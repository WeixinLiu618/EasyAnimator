package cs5004.animator.model;

import cs5004.animator.model.animations.IAnimation;
import cs5004.animator.model.animations.ChangingColor;
import cs5004.animator.model.animations.Moving;
import cs5004.animator.model.animations.Scaling;
import cs5004.animator.model.attributes.Canvas;
import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;
import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.model.shapes.Oval;
import cs5004.animator.model.shapes.Rectangle;
import cs5004.animator.model.shapes.IShape;
import cs5004.animator.model.util.AnimationBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * This class implements the AnimationModel interface. It represents an animation with a canvas, a
 * list of shapes and a list of animations happen on those shapes. It supports adding and removing
 * shapes in the animation, as well as adding animations to the shapes in the model. It can also get
 * a list of new shapes at a specific tick during the animation. The class uses an inner builder
 * class to construct the model.
 *
 * @author Weixin Liu
 */
public class AnimationModelImpl implements AnimationModel {

  private List<IShape> shapes;
  private List<IAnimation> animations;
  private HashMap<String, String> declaredShapes;
  private Canvas canvas;


  /**
   * Creates an animation model object, and initializes an empty shape list and an empty animation
   * list at beginning. Initializes a hashmap to mapping of store shape name string and type string
   * that helps to build the model. Initializes a new canvas of this model.
   */
  public AnimationModelImpl() {
    this.shapes = new ArrayList<>();
    this.animations = new ArrayList<>();
    this.declaredShapes = new HashMap<>();
    this.canvas = new Canvas(0, 0, 1000, 1000);
  }

  @Override
  public List<IShape> getShapes() {
    return this.shapes;
  }

  @Override
  public List<IAnimation> getAnimations() {
    return this.animations;
  }

  @Override
  public HashMap<String, String> getDeclaredShapes() {
    return this.declaredShapes;
  }

  @Override
  public Canvas getCanvas() {
    return canvas;
  }

  @Override
  public IShape findShape(String name) {

    for (IShape s : this.shapes) {
      if (s.getName().equals(name)) {
        return s;
      }
    }
    return null;
  }


  @Override
  public void addShape(IShape shape) throws IllegalArgumentException {

    if (findShape(shape.getName()) != null) {
      throw new IllegalArgumentException("The shape with this name has already existed.");
    } else {
      this.shapes.add(shape);
    }

  }

  @Override
  public void removeShape(String shapeName) {

    this.shapes.removeIf(s -> s.getName().equals(shapeName));
    this.animations.removeIf(a -> a.getShapeName().equals(shapeName));
  }

  @Override
  public void addAnimation(IAnimation animation) throws IllegalArgumentException {
    for (IShape s : this.shapes) {
      if (s.getName().equals(animation.getShapeName())) {
        // add the animation to the corresponding shape
        s.addShapeAnimation(animation);
        // add the animation to the animation list of the model and sort by start tick
        this.animations.add(animation);
        Collections.sort(animations);
        break;
      }
    }

  }


  @Override
  public int getEndTick() {
    int lastShapeDisappearTick = 0;
    if (!shapes.isEmpty()) {
      lastShapeDisappearTick = shapes.stream()
          .map(IReadOnlyShape::getDisappearTick)
          .max(Integer::compare)
          .get();
    }
    return lastShapeDisappearTick;
  }

  @Override
  public List<IReadOnlyShape> getReadOnlyShapes() {
    return new ArrayList<IReadOnlyShape>(this.shapes);
  }

  @Override
  public List<IReadOnlyShape> getTweeningShapes(int t) {
    List<IReadOnlyShape> tweeningShapesAtTick = new ArrayList<>();
    for (IShape s : this.shapes) {
      IReadOnlyShape animatedShape = s.getShapeAtTick(t);
      if (animatedShape != null) {
        tweeningShapesAtTick.add(animatedShape);
      }
    }

    return tweeningShapesAtTick;
  }

  //------------------------------------------------------------------------------------

  /**
   * This class implements the AnimationBuilder interface and is compatible with the animation
   * model. It is used to build the animation model.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {

    AnimationModel model = new AnimationModelImpl();


    /**
     * Constructs a final animation model.
     *
     * @return the newly constructed animation model
     */
    @Override
    public AnimationModel build() {
      return this.model;
    }


    /**
     * Sets the bounds of the canvas for the animation.
     *
     * @param x      The leftmost x value
     * @param y      The topmost y value
     * @param width  The width of the canvas
     * @param height The height of the canvas
     * @return this AnimationBuilder
     */
    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      this.model.getCanvas().setCanvas(x, y, width, height);
      return this;
    }

    /**
     * Adds a name string as key and the type string as value to the declared HashMap.
     *
     * @param name The unique name of the shape to be added. No shape with this name should already
     *             exist.
     * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
     *             shapes is unspecified, but should include "ellipse" and "rectangle" as a
     *             minimum.
     * @return this AnimationBuilder
     */
    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
      if (this.model.findShape(name) != null) {
        return this;
      }
      this.model.getDeclaredShapes().put(name, type);
      return this;
    }

    /**
     * Adds a transformation to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start tick of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param t2   The end tick of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @return this AnimationBuilder
     * @throws IllegalArgumentException when the new motion overlaps with the existing motion of a
     *                                  shape
     */
    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) throws IllegalArgumentException {
      if (this.model.findShape(name) == null) {
        if ("rectangle".equals(this.model.getDeclaredShapes().get(name))) {
          this.model.addShape(new Rectangle(name, new Position(x1, y1), new Size(w1, h1),
              new RGBColor(r1, g1, b1), t1, t2));
        } else if ("ellipse".equals(this.model.getDeclaredShapes().get(name))) {
          this.model.addShape(new Oval(name, new Position(x1, y1), new Size(w1, h1),
              new RGBColor(r1, g1, b1), t1, t2));
        }
      } else {
        IShape existedShape = this.model.findShape(name);
        int currentDisappearTick = existedShape.getDisappearTick();
        existedShape.setDisappearTick(Math.max(currentDisappearTick, t2));
      }

      // if any changes in position, size or color, add the corresponding animation to the model
      if (x1 != x2 || y1 != y2) {
        this.model.addAnimation(
            new Moving(name, t1, t2, new Position(x1, y1), new Position(x2, y2)));
      }
      if (w1 != w2 || h1 != h2) {
        this.model.addAnimation(new Scaling(name, t1, t2, new Size(w1, h1), new Size(w2, h2)));
      }
      if (r1 != r2 || g1 != g2 || b1 != b2) {
        this.model.addAnimation(
            new ChangingColor(name, t1, t2, new RGBColor(r1, g1, b1), new RGBColor(r2, g2, b2)));
      }

      return this;
    }
  }


}
