package cs5004.animator.model.animations;


/**
 * This enum represents different types of animations that can be applied to a shape.
 *
 * @author Weixin Liu
 */
public enum AnimationType {

  MOVE("move"), COLOR("change color"), SCALE("scale");

  private final String animationType;

  /**
   * A constructor of the enum AnimationType with the given animation type string.
   *
   * @param animationType the animation type string of this AnimationType
   */
  AnimationType(String animationType) {
    this.animationType = animationType;
  }

  /**
   * Returns a string to describe this AnimationType.
   *
   * @return a string to describe this AnimationType
   */
  @Override
  public String toString() {
    return this.animationType;
  }

}
