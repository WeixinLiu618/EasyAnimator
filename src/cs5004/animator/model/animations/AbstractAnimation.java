package cs5004.animator.model.animations;


/**
 * This is an abstract class representing the animation in the animation model. It implements the
 * IAnimation interface and offers most of the operations mandated by the IAnimation interface.
 *
 * @author Weixin Liu
 */
public abstract class AbstractAnimation implements IAnimation {

  protected String shapeName;
  protected int startTick;
  protected int endTick;
  protected AnimationType animationType;

  /**
   * Defines a constructor of an animation with the given shape name, start tick and end tick.
   *
   * @param shapeName the given shape name this animation is added on
   * @param startTick the given start tick of this animation
   * @param endTick   the given end tick of this animation
   * @throws IllegalArgumentException when the given start tick or end tick is negative, or the
   *                                  given start tick is larger than the given end tick
   */
  public AbstractAnimation(String shapeName, int startTick, int endTick)
      throws IllegalArgumentException {
    if (startTick < 0 || endTick < 0 || startTick > endTick) {
      throw new IllegalArgumentException("Input start tick or end tick is invalid.");
    }
    this.shapeName = shapeName;
    this.startTick = startTick;
    this.endTick = endTick;
  }

  @Override
  public int getStartTick() {
    return this.startTick;
  }

  @Override
  public int getEndTick() {
    return this.endTick;
  }

  @Override
  public AnimationType getAnimationType() {
    return this.animationType;
  }

  @Override
  public String getShapeName() {
    return this.shapeName;
  }

  @Override
  public abstract boolean isOverlap(IAnimation a);

  @Override
  public boolean isOverlap(ChangingColor c) {
    return false;
  }

  @Override
  public boolean isOverlap(Scaling s) {
    return false;
  }

  @Override
  public boolean isOverlap(Moving m) {
    return false;
  }


  /**
   * Compares this animation with the given one based on their start ticks.
   *
   * @param o the given animation to be compared with this animation
   */
  @Override
  public int compareTo(IAnimation o) {
    return this.getStartTick() - o.getStartTick();
  }

  /**
   * Returns the in-betweening value from value a to value b at tick t, where t is between start
   * tick and end tick using linear interpolation. If the intermediate tick is smaller than the
   * start tick, the tweening value will be the start value, and if the intermediate tick is larger
   * than the end tick, the tweening value will be the end value.
   *
   * @param start the start tick
   * @param end   the end tick
   * @param t     the intermediate tick between start and end
   * @param a     the start value
   * @param b     the end value
   * @return the tweening value of a and b at tick t
   */
  protected double calculateTweeningValue(int start, int end, int t, double a, double b) {

    if (start <= t && t <= end) {
      return a * (end - t) / (end - start) + b * (t - start) / (end - start);
    } else if (t > end) {
      return b;
    } else {
      return a;
    }

  }

}
