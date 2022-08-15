package cs5004.animator.model.shapes;

import cs5004.animator.model.animations.IAnimation;
import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;
import java.util.List;

/**
 * This IReadOnlyShape interface is to extract shape data of the animation model. It provides it to
 * the client, avoiding direct contact between the client and the model and preventing mutation of
 * the model and the original shapes.
 *
 * @author Weixin Liu
 */
public interface IReadOnlyShape {

  /**
   * Returns the name of this shape.
   *
   * @return the name of this shape
   */
  String getName();

  /**
   * Returns the type of this shape.
   *
   * @return the type of this shape
   */
  ShapeType getShapeType();

  /**
   * Returns the coordinate position (x, y) of this shape.
   *
   * @return the coordinate position (x, y) of this shape
   */
  Position getPosition();

  /**
   * Returns the size with two dimensions of this shape.
   *
   * @return the size of this shape
   */
  Size getSize();

  /**
   * Returns the RGB color of this shape.
   *
   * @return the RGB color of this shape
   */
  RGBColor getColor();

  /**
   * Returns the appear tick of this shape.
   *
   * @return the appear tick of this shape
   */
  int getAppearTick();

  /**
   * Returns the disappear tick of this shape.
   *
   * @return the disappear tick of this shape
   */
  int getDisappearTick();


  /**
   * Returns the list of animations on this shape.
   *
   * @return the list of animations on this shape
   */
  List<IAnimation> getShapeAnimations();


  /**
   * Evaluates the status of animation on a shape object at a particular tick value t and return the
   * new animated shape object at that tick. If the t is smaller than the appear tick of this shape,
   * returns a shape with the same initial attributes of this shape. If the t is larger than the
   * disappear tick of this shape, returns the last state of this shape.
   *
   * @param t a given tweening tick
   * @return a shape with mutated attributes at the that tick
   */
  IReadOnlyShape getShapeAtTick(int t);


}
