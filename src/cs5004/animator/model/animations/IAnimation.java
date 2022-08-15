package cs5004.animator.model.animations;


/**
 * The IAnimation interface represents several operations for all types of animations.
 *
 * @author Weixin Liu
 */
public interface IAnimation extends Comparable<IAnimation> {


  /**
   * Returns the start tick of this animation.
   *
   * @return the start tick of this animation
   */
  int getStartTick();

  /**
   * Returns the end tick of this animation.
   *
   * @return the end tick of this animation
   */
  int getEndTick();

  /**
   * Returns the type of the animation.
   *
   * @return the type of the animation.
   */
  AnimationType getAnimationType();

  /**
   * Returns this animation's shape name.
   *
   * @return the shape name this animation is added on
   */
  String getShapeName();


  /**
   * Checks if this animation overlaps with the given animation. Overlap means two animations have
   * the same shape name, the same animation type, as well as their durations are overlapping.
   *
   * @param a the given animation to be checked with
   * @return true if this animation is overlapping with the given animation, otherwise false
   */
  boolean isOverlap(IAnimation a);

  /**
   * Checks if this animation overlaps with the given changing color animation. Overlap means two
   * animations have the same shape name, the same animation type, as well as their durations are
   * overlapping.
   *
   * @param c the given changing color animation
   * @return true if this animation overlaps with the given change color animation, otherwise false
   */
  boolean isOverlap(ChangingColor c);

  /**
   * Checks if this animation overlaps with the given scaling animation. Overlap means two
   * animations have the same shape name, the same animation type, as well as their durations are
   * overlapping.
   *
   * @param s the given scaling animation
   * @return true if this animation overlaps with the given scaling animation, otherwise false
   */
  boolean isOverlap(Scaling s);

  /**
   * Checks if this animation overlaps with the given moving animation. Overlap means two animations
   * have the same shape name, the same animation type, as well as their durations are overlapping.
   *
   * @param m the given moving animation
   * @return true if this animation overlaps with the given moving animation, otherwise false
   */
  boolean isOverlap(Moving m);


  /**
   * Compares this animation with the given one based on their start ticks.
   *
   * @param o the given animation to be compared with this animation
   */
  @Override
  int compareTo(IAnimation o);


}















