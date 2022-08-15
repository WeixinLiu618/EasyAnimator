package cs5004.animator.model.shapes;

import cs5004.animator.model.animations.IAnimation;

import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;


/**
 * This IShape interface contains several operations of all types of shapes and extends the
 * IReadOnlyShape interface.
 *
 * @author Weixin Liu
 */
public interface IShape extends IReadOnlyShape {

  /**
   * Sets the new position of this shape.
   *
   * @param position the given position to be set to this shape
   */
  void setPosition(Position position);


  /**
   * Sets the new color of this shape.
   *
   * @param color the given color to be set to this shape
   */
  void setColor(RGBColor color);

  /**
   * Sets the new size of this shape.
   *
   * @param size the given size to be set to this shape
   */
  void setSize(Size size);


  /**
   * Sets the new disappear tick of this shape.
   *
   * @param disappearTick the given disappear tick to be set to this shape
   */
  void setDisappearTick(int disappearTick);


  /**
   * Adds an animation to this shape. If the end tick of the animation is larger than the
   * disappear tick of this shape, set the disappear tick as the end tick of the animation.
   *
   * @param animation the animation to be added to the shape
   * @throws IllegalArgumentException if the shape name of the given animation and this shape are
   *                                  different, or if that animation is overlapped with some
   *                                  existing animation of this shape
   */
  void addShapeAnimation(IAnimation animation) throws IllegalArgumentException;

}
