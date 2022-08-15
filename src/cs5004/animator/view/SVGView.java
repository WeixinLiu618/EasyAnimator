package cs5004.animator.view;

import cs5004.animator.model.animations.ChangingColor;
import cs5004.animator.model.animations.IAnimation;
import cs5004.animator.model.animations.Moving;
import cs5004.animator.model.animations.Scaling;
import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.model.shapes.ShapeType;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * This SVGView class extends the AbstractView. It represents an SVG description of the animation
 * and renders the animation model into the string output with the SVG format. If the user chooses
 * to set the loop flag of this view as true, the output SVG will be with loopback. Otherwise, there
 * will not be with loopback.
 *
 * @author Weixin Liu
 */
public class SVGView extends AbstractView {

  Appendable output;


  /**
   * Return the output of this view.
   *
   * @return the output of this view.
   */
  public Appendable getOutput() {
    return this.output;
  }

  /**
   * Creates a SVGView object with a given tempo.
   *
   * @param tempo  the given tempo of this view
   * @param output the given appendable output of this view
   */
  public SVGView(int tempo, Appendable output) {
    super();
    this.tempo = tempo;
    this.output = output;
  }

  /**
   * Renders the all shapes and animation of the model and generates the string output with the SVG
   * format. If the loopFlag of this view is true, then the SVG output will be with loopback,
   * otherwise, there will be no loopback.
   *
   * @param shapes the given list of shapes in ReadOnly format from the animation model
   */
  @Override
  public void render(List<IReadOnlyShape> shapes) {

    StringBuilder svgText = new StringBuilder();

    int lastTick = 0;
    for (IReadOnlyShape s : shapes) {
      lastTick = Math.max(lastTick, s.getDisappearTick());
    }
    // set the duration time if loopback which is the end time of the model in ms, and plus 2000ms
    int loopBackTime = lastTick * 1000 / this.tempo + 2000;

    svgText.append(String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">\n\n",
        this.canvas.getCanvasX() + this.canvas.getCanvasWidth(),
        this.canvas.getCanvasY() + this.canvas.getCanvasHeight()));

    // if with loopback, append the loopback section at beginning
    if (loopFlag) {
      svgText.append(generateLoopBackSection(loopBackTime));
    }

    for (IReadOnlyShape shape : shapes) {
      // append the shape tag
      svgText.append(generateShape(shape));
      ShapeType shapeType = shape.getShapeType();
      // generates all animations in this shape tag
      svgText.append(
          generateShapeAnimations(shapeType, shape.getShapeAnimations(), tempo, loopFlag));
      // if with loopback, restore all attributes of this shape to initial values
      if (loopFlag) {
        svgText.append(restoreAttributes(shape));
      }
      // end of this shape tag
      switch (shapeType) {
        case RECTANGLE:
          svgText.append("</rect>\n\n");
          break;
        case OVAL:
          svgText.append("</ellipse>\n\n");
          break;
        default:
          break;
      }
    }
    // end of this svg tag
    svgText.append("</svg>\n");

    try {
      this.output.append(svgText);
      if (this.output instanceof Closeable) {
        ((Closeable) this.output).close();
      }
    } catch (IOException e) {
      throw new IllegalStateException("cannot output the animation");
    }
  }

  /**
   * Returns the loopback section strings with the given duration time.
   *
   * @param loopBackTime the given time after which the animation loops back, in millisecond
   * @return the StringBuilder of the loopback section
   */
  private StringBuilder generateLoopBackSection(int loopBackTime) {
    StringBuilder loopBackSection = new StringBuilder();
    loopBackSection.append("<rect>\n");
    loopBackSection.append("\t<animate id=\"base\" begin=\"0;base.end\" dur=\""
        + loopBackTime + "ms\" " + "attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n");
    loopBackSection.append("</rect>\n\n");
    return loopBackSection;
  }

  /**
   * Returns the StringBuilder of one line output of an attribute changing(or an animation).
   *
   * @param attributeName the given attributeName
   * @param startTime     the given startTime of the animation
   * @param duringTime    the given duringTime of the animation
   * @param oldValue      the given old value of the attribute
   * @param newValue      the given new value of the attribute
   * @param loopFlag      the given loopFlag of this svg view
   * @return the StringBuilder of one line output of an attribute changing
   */
  private StringBuilder generateAttributeChange(String attributeName, int startTime,
      int duringTime, String oldValue, String newValue, boolean loopFlag) {

    StringBuilder outputLine = new StringBuilder();
    if (!oldValue.equals(newValue)) {
      if (loopFlag) {
        outputLine.append(String.format(("\t<animate attributeType=\"xml\" begin=\"base"
                + ".begin+%dms\" dur=\"%dms\" attributeName=\"%s\" from=\"%s\" to=\"%s\" "
                + "fill=\"freeze\" />\n"),
            startTime, duringTime, attributeName, oldValue, newValue));
      } else {
        outputLine.append(String.format(("\t<animate attributeType=\"xml\" begin=\"%dms\" "
                + "dur=\"%dms\" attributeName=\"%s\" from=\"%s\" to=\"%s\" fill=\"freeze\" />\n"),
            startTime, duringTime, attributeName, oldValue, newValue));
      }
    }
    return outputLine;
  }

  /**
   * Returns the StringBuilder of one line output of an attribute restoring with the animate tag.
   *
   * @param attributeName the given attributeName
   * @param initialValue  the given initial value to restore to
   * @return the StringBuilder of one line output of an attribute restoring
   */
  private StringBuilder generateAttributeRestore(String attributeName, String initialValue) {
    StringBuilder outputLine = new StringBuilder();
    outputLine.append(String.format((
        "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"%s\" "
            + "to=\"%s\" fill=\"freeze\" />\n"), attributeName, initialValue));
    return outputLine;
  }


  /**
   * Generates the svg strings of the restoring all the attributes of the given ReadOnly shape to
   * the original value when loopback.
   *
   * @param shape the given ReadOnly shape
   * @return a StringBuilder of restoring attributes of a shape in svg format
   */
  private StringBuilder restoreAttributes(IReadOnlyShape shape) {
    StringBuilder outputLines = new StringBuilder();

    String positionX = String.format("%.1f", shape.getPosition().getX());
    String positionY = String.format("%.1f", shape.getPosition().getY());
    String sizeX = String.format("%.1f", shape.getSize().getXLength());
    String sizeY = String.format("%.1f", shape.getSize().getYLength());
    String color = "rgb" + shape.getColor().toString();

    switch (shape.getShapeType()) {
      case RECTANGLE:
        outputLines.append(generateAttributeRestore("x", positionX));
        outputLines.append(generateAttributeRestore("y", positionY));
        outputLines.append(generateAttributeRestore("width", sizeX));
        outputLines.append(generateAttributeRestore("height", sizeY));
        outputLines.append(generateAttributeRestore("fill", color));
        break;
      case OVAL:
        outputLines.append(generateAttributeRestore("cx", positionX));
        outputLines.append(generateAttributeRestore("cy", positionY));
        outputLines.append(generateAttributeRestore("rx", sizeX));
        outputLines.append(generateAttributeRestore("ry", sizeY));
        outputLines.append(generateAttributeRestore("fill", color));
        break;
      default:
        return outputLines;
    }
    return outputLines;
  }

  /**
   * Generates all the animations output in one shape tag. All the output strings are with animate
   * tag line by line.
   *
   * @param shapeType  the given shape type with the animations
   * @param animations the given animation list of this shape
   * @param tempo      the given tempo of this view
   * @param loopFlag   the given loopFlag of this view
   * @return the StringBuilder of all animations of a shape in svg format
   */
  private StringBuilder generateShapeAnimations(ShapeType shapeType, List<IAnimation> animations,
      int tempo, boolean loopFlag) {

    StringBuilder animationsOutput = new StringBuilder();

    for (IAnimation animation : animations) {

      int startTime = animation.getStartTick() * 1000 / tempo;
      int during = animation.getEndTick() * 1000 / tempo - startTime;

      switch (animation.getAnimationType()) {
        case MOVE:
          Moving moving = (Moving) animation;
          String oldX = String.format("%.1f", moving.getFromPosition().getX());
          String newX = String.format("%.1f", moving.getToPosition().getX());

          if (shapeType == ShapeType.RECTANGLE) {
            animationsOutput.append(
                generateAttributeChange(
                    "x", startTime, during, oldX, newX, loopFlag));
          } else if (shapeType == ShapeType.OVAL) {
            animationsOutput.append(
                generateAttributeChange(
                    "cx", startTime, during, oldX, newX, loopFlag));

          }

          String oldY = String.format("%.1f", moving.getFromPosition().getY());
          String newY = String.format("%.1f", moving.getToPosition().getY());

          if (shapeType == ShapeType.RECTANGLE) {
            animationsOutput.append(
                generateAttributeChange(
                    "y", startTime, during, oldY, newY, loopFlag));
          } else if (shapeType == ShapeType.OVAL) {
            animationsOutput.append(
                generateAttributeChange(
                    "cy", startTime, during, oldY, newY, loopFlag));
          }
          break;

        case SCALE:
          Scaling scaling = (Scaling) animation;

          String oldWidth = String.format("%.1f", scaling.getOldSize().getXLength());
          String newWidth = String.format("%.1f", scaling.getNewSize().getXLength());

          if (shapeType == ShapeType.RECTANGLE) {
            animationsOutput.append(
                generateAttributeChange("width", startTime, during,
                    oldWidth, newWidth, loopFlag));
          } else if (shapeType == ShapeType.OVAL) {
            animationsOutput.append(
                generateAttributeChange("rx", startTime, during,
                    oldWidth, newWidth, loopFlag));
          }

          String oldHeight = String.format("%.1f", scaling.getOldSize().getYLength());
          String newHeight = String.format("%.1f", scaling.getNewSize().getYLength());

          if (shapeType == ShapeType.RECTANGLE) {
            animationsOutput.append(
                generateAttributeChange("height", startTime, during,
                    oldHeight, newHeight, loopFlag));
          } else if (shapeType == ShapeType.OVAL) {
            animationsOutput.append(
                generateAttributeChange("ry", startTime, during,
                    oldHeight, newHeight, loopFlag));
          }
          break;

        case COLOR:
          ChangingColor changingColor = (ChangingColor) animation;
          String oldColor = "rgb" + changingColor.getOldColor().toString();
          String newColor = "rgb" + changingColor.getNewColor().toString();
          animationsOutput.append(
              generateAttributeChange("fill", startTime, during,
                  oldColor, newColor, loopFlag));
          break;
        default:
          return animationsOutput;
      }

    }

    return animationsOutput;
  }


  /**
   * Generates the shape tag string of the creating the given ReadOnly shape.
   *
   * @param shape the given ReadOnly shape
   * @return a StringBuilder of a shape in svg format
   */
  private StringBuilder generateShape(IReadOnlyShape shape) {
    StringBuilder shapeOutput = new StringBuilder();
    String id = shape.getName();
    String positionX = String.format("%.1f", shape.getPosition().getX());
    String positionY = String.format("%.1f", shape.getPosition().getY());
    String sizeX = String.format("%.1f", shape.getSize().getXLength());
    String sizeY = String.format("%.1f", shape.getSize().getYLength());
    String color = "rgb" + shape.getColor().toString();

    switch (shape.getShapeType()) {
      case RECTANGLE:
        shapeOutput.append(String.format(
            ("<rect id=\"%s\" x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" "
                + "fill=\"%s\" visibility=\"visible\" >\n"),
            id, positionX, positionY, sizeX, sizeY, color));
        break;
      case OVAL:
        shapeOutput.append(String.format(
            ("<ellipse id=\"%s\" cx=\"%s\" cy=\"%s\" rx=\"%s\" ry=\"%s\" "
                + "fill=\"%s\" visibility=\"visible\" >\n"),
            id, positionX, positionY, sizeX, sizeY, color));
        break;
      default:
        return shapeOutput;
    }
    return shapeOutput;
  }


  @Override
  public void addFeatures(EditorFeatures editorFeatures) throws UnsupportedOperationException {
    // SVGView does not support the editor features.
    throw new UnsupportedOperationException("The given view doesn't support the editor features.");
  }

}
