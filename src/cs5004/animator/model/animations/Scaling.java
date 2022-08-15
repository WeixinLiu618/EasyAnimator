package cs5004.animator.model.animations;

import cs5004.animator.model.attributes.Size;


/**
 * This class represents the scaling animation that inherits the class AbstractAnimation.
 *
 * @author Weixin Liu
 */
public class Scaling extends AbstractAnimation {

  private Size oldSize;
  private Size newSize;


  /**
   * Creates a Scaling animation object with the given shape name, start tick, end tick, old size
   * and new size.
   *
   * @param shapeName the given shape name this scaling animation is added on
   * @param startTick the given start tick of this scaling animation
   * @param endTick   the given end tick of this scaling animation
   * @param oldSize   the given old size of this scaling animation
   * @param newSize   the given new size of this scaling animation
   * @throws IllegalArgumentException when the given start tick or end tick is negative, or the
   *                                  given start tick is larger than the given end tick
   */
  public Scaling(String shapeName, int startTick, int endTick, Size oldSize, Size newSize)
      throws IllegalArgumentException {

    super(shapeName, startTick, endTick);
    this.oldSize = oldSize;
    this.newSize = newSize;
    this.animationType = AnimationType.SCALE;
  }

  /**
   * Returns the old size of this scaling animation.
   *
   * @return the old size of this scaling animation
   */
  public Size getOldSize() {
    return oldSize;
  }

  /**
   * Returns the new size of this scaling animation.
   *
   * @return the new size of this scaling animation
   */
  public Size getNewSize() {
    return newSize;
  }


  @Override
  public boolean isOverlap(IAnimation a) {
    return a.isOverlap(this);
  }

  @Override
  public boolean isOverlap(Scaling s) {
    return this.getShapeName().equals(s.getShapeName())
        && this.getStartTick() < s.getEndTick() && this.getEndTick() > s.getStartTick();
  }

  /**
   * Returns the size in the intermediate tick t, where t is between the start tick and the end tick
   * of this scaling animation. If the intermediate tick is smaller than the start tick, the
   * tweening size will be the same values of the old size, and if the intermediate tick is larger
   * than the end tick, the tweening size will be the same values of the new size.
   *
   * @param t the intermediate tick between the start tick and the end tick of this animation
   * @return the size at the intermediate tick t
   */
  public Size getTweeningAnimatedSize(int t) {

    double tXLength = calculateTweeningValue(this.startTick, this.endTick, t,
        this.oldSize.getXLength(), this.newSize.getXLength());
    double tYLength = calculateTweeningValue(this.startTick, this.endTick, t,
        this.oldSize.getYLength(), this.newSize.getYLength());

    return new Size(tXLength, tYLength);
  }


}
