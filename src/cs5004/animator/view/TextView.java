package cs5004.animator.view;

import cs5004.animator.model.animations.AnimationType;
import cs5004.animator.model.animations.ChangingColor;
import cs5004.animator.model.animations.IAnimation;
import cs5004.animator.model.animations.Moving;
import cs5004.animator.model.animations.Scaling;
import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.model.shapes.ShapeType;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

/**
 * This TextView class extends the AbstractView. It represents a text description of the animation
 * and renders the animation model into the string output with some format.
 *
 * @author Weixin Liu
 */
public class TextView extends AbstractView {

  private Appendable output;

  /**
   * Return the output of this view.
   *
   * @return the output of this view.
   */
  public Appendable getOutput() {
    return this.output;
  }

  /**
   * Creates a TextView object with a given tempo.
   *
   * @param tempo  the given tempo of this view
   * @param output the given appendable output of this view
   */
  public TextView(int tempo, Appendable output) {
    super();
    this.tempo = tempo;
    this.output = output;
  }

  /**
   * Renders the all shapes and animation of the model and generates the textual output with some
   * format.
   *
   * @param shapes the given read only shapes list from the animation model
   */
  @Override
  public void render(List<IReadOnlyShape> shapes) {

    StringBuilder textualOutput = new StringBuilder();
    if (shapes == null || shapes.size() < 1) {
      textualOutput.append("There is no shape!");
    } else {

      // Uses a sorted map to put all animations keys in order.
      // The mapped values are the ShapeType.
      TreeMap<IAnimation, ShapeType> animationTreeMap = new TreeMap<>();

      for (IReadOnlyShape s : shapes) {

        // Put all animations of the shape as key and the shape type as value to the treemap.
        for (IAnimation animation : s.getShapeAnimations()) {
          animationTreeMap.put(animation, s.getShapeType());
        }

        if (s.getShapeType() == ShapeType.RECTANGLE) {
          textualOutput.append(
              String.format(("Create rectangle %s with corner at %s, width %.1f "
                      + "and height %.1f, color rgb%s\n"),
                  s.getName(),
                  s.getPosition().toString(),
                  s.getSize().getXLength(),
                  s.getSize().getYLength(),
                  s.getColor().toString()));
        } else if (s.getShapeType() == ShapeType.OVAL) {
          textualOutput.append(
              String.format(
                  ("Create oval %s with center at %s, radius %.1f and %.1f, color rgb%s\n"),
                  s.getName(),
                  s.getPosition().toString(),
                  s.getSize().getXLength(),
                  s.getSize().getYLength(),
                  s.getColor().toString()));
        }
      }

      textualOutput.append("\n");

      // time = tick / tempo, which is in second.
      // And multiply 1000 to get the time in millisecond.
      for (IReadOnlyShape shape : shapes) {
        textualOutput.append(
            String.format("%s appears at time t=%dms and disappears at time t=%dms\n",
                shape.getName(),
                shape.getAppearTick() * 1000 / tempo,
                shape.getDisappearTick() * 1000 / tempo));
      }

      textualOutput.append("\n");

      // generate animation output text
      for (IAnimation a : animationTreeMap.keySet()) {
        // moving
        if (a.getAnimationType() == AnimationType.MOVE) {
          Moving m = (Moving) a;
          textualOutput.append(
              String.format("Shape %s moves from %s to %s from t=%dms to t=%dms\n",
                  m.getShapeName(),
                  m.getFromPosition().toString(),
                  m.getToPosition().toString(),
                  m.getStartTick() * 1000 / tempo,
                  m.getEndTick() * 1000 / tempo));

          // changing color
        } else if (a.getAnimationType() == AnimationType.COLOR) {
          ChangingColor c = (ChangingColor) a;
          textualOutput.append(
              String.format("Shape %s changes from rgb%s to rgb%s from t=%dms to t=%dms\n",
                  c.getShapeName(),
                  c.getOldColor().toString(),
                  c.getNewColor().toString(),
                  c.getStartTick() * 1000 / tempo,
                  c.getEndTick() * 1000 / tempo));

          // scaling
        } else if (a.getAnimationType() == AnimationType.SCALE) {
          Scaling s = (Scaling) a;

          // width or x radius changes
          if (s.getOldSize().getXLength() != s.getNewSize().getXLength()) {
            if (animationTreeMap.get(a) == ShapeType.RECTANGLE) {
              textualOutput.append(
                  String.format("Shape %s changes width from %.1f to %.1f from time t=%dms to "
                          + "t=%dms\n",
                      s.getShapeName(),
                      s.getOldSize().getXLength(),
                      s.getNewSize().getXLength(),
                      s.getStartTick() * 1000 / tempo,
                      s.getEndTick() * 1000 / tempo));
            } else if (animationTreeMap.get(a) == ShapeType.OVAL) {
              textualOutput.append(
                  String.format("Shape %s changes X radius from %.1f to %.1f from time t=%dms to "
                          + "t=%dms\n",
                      s.getShapeName(),
                      s.getOldSize().getXLength(),
                      s.getNewSize().getXLength(),
                      s.getStartTick() * 1000 / tempo,
                      s.getEndTick() * 1000 / tempo));
            }
          }
          // height or y radius changes
          if (s.getOldSize().getYLength() != s.getNewSize().getYLength()) {
            if (animationTreeMap.get(a) == ShapeType.RECTANGLE) {
              textualOutput.append(
                  String.format("Shape %s changes height from %.1f to %.1f from time t=%dms to "
                          + "t=%dms\n",
                      s.getShapeName(),
                      s.getOldSize().getYLength(),
                      s.getNewSize().getYLength(),
                      s.getStartTick() * 1000 / tempo,
                      s.getEndTick() * 1000 / tempo));
            } else if (animationTreeMap.get(a) == ShapeType.OVAL) {
              textualOutput.append(
                  String.format("Shape %s changes Y radius from %.1f to %.1f from time t=%dms to "
                          + "t=%dms\n",
                      s.getShapeName(),
                      s.getOldSize().getYLength(),
                      s.getNewSize().getYLength(),
                      s.getStartTick() * 1000 / tempo,
                      s.getEndTick() * 1000 / tempo));
            }
          }
        }
      }
    }

    try {
      this.output.append(textualOutput);
      if (this.output instanceof Closeable) {
        ((Closeable) this.output).close();
      }
    } catch (IOException e) {
      throw new IllegalStateException("cannot output the animation");
    }
  }


  @Override
  public void addFeatures(EditorFeatures editorFeatures) throws UnsupportedOperationException {
    // TextView does not support the editor features.
    throw new UnsupportedOperationException("The given view doesn't support the editor features.");
  }
}
