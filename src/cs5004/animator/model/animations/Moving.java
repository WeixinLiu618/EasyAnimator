package cs5004.animator.model.animations;

import cs5004.animator.model.attributes.Position;


/**
 * This class represents the moving animation that inherits the class AbstractAnimation.
 *
 * @author Weixin Liu
 */
public class Moving extends AbstractAnimation {


  private Position fromPosition;
  private Position toPosition;

  /**
   * Creates a Moving animation object with the given shape name, start tick, end tick, fromPosition
   * and toPosition.
   *
   * @param shapeName    the given shape name this moving animation is added on
   * @param startTick    the given start tick of this moving animation
   * @param endTick      the given end tick of this moving animation
   * @param fromPosition the given start position of this moving animation
   * @param toPosition   the given destination position of this moving animation
   * @throws IllegalArgumentException when the given start tick or end tick is negative, or the
   *                                  given start tick is larger than the given end tick
   */
  public Moving(String shapeName, int startTick, int endTick, Position fromPosition,
      Position toPosition) throws IllegalArgumentException {

    super(shapeName, startTick, endTick);
    this.fromPosition = fromPosition;
    this.toPosition = toPosition;

    this.animationType = AnimationType.MOVE;
  }


  @Override
  public boolean isOverlap(IAnimation a) {
    return a.isOverlap(this);
  }

  @Override
  public boolean isOverlap(Moving m) {
    return this.getShapeName().equals(m.getShapeName())
        && this.getStartTick() < m.getEndTick() && this.getEndTick() > m.getStartTick();
  }


  /**
   * Returns the position from which starting moving.
   *
   * @return the position from which starting moving
   */
  public Position getFromPosition() {
    return fromPosition;
  }

  /**
   * Returns the position which to move to.
   *
   * @return the position which to move to
   */
  public Position getToPosition() {
    return toPosition;
  }


  /**
   * Returns the position in the intermediate tick t, where t is between the start tick and the end
   * tick of this moving animation. If the intermediate tick is smaller than the start tick, the
   * tweening Poisiton will be the same values of the fromPosition, and if the intermediate tick is
   * larger than the end tick, the tweening Poisiton will be the same values of the toPosition.
   *
   * @param t the intermediate tick between the start tick and the end tick of this animation
   * @return the position at the intermediate tick t
   */
  public Position getTweeningAnimatedPosition(int t) {

    double tCoordinateX = calculateTweeningValue(this.startTick, this.endTick, t,
        this.fromPosition.getX(), this.toPosition.getX());
    double tCoordinateY = calculateTweeningValue(this.startTick, this.endTick, t,
        this.fromPosition.getY(), this.toPosition.getY());

    return new Position(tCoordinateX, tCoordinateY);
  }


}
