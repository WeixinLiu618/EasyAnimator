package cs5004.animator.model.animations;

import cs5004.animator.model.attributes.RGBColor;


/**
 * This class represents the changing color animation that inherits the class AbstractAnimation.
 *
 * @author Weixin Liu
 */
public class ChangingColor extends AbstractAnimation {

  private RGBColor oldColor;
  private RGBColor newColor;


  /**
   * Creates a ChangingColor animation object with the given shape name, start tick, end tick, old
   * color and new color.
   *
   * @param shapeName the given shape name this changing color animation is added on
   * @param startTick the given start tick of this changing color animation
   * @param endTick   the given end tick of this changing color animation
   * @param oldColor  the given old color of this changing color animation
   * @param newColor  the given new color of this changing color animation
   * @throws IllegalArgumentException when the given start tick or end tick is negative, or the
   *                                  given start tick is larger than the given end tick
   */
  public ChangingColor(String shapeName, int startTick, int endTick, RGBColor oldColor,
      RGBColor newColor)
      throws IllegalArgumentException {
    super(shapeName, startTick, endTick);
    this.oldColor = oldColor;
    this.newColor = newColor;
    this.animationType = AnimationType.COLOR;
  }

  @Override
  public boolean isOverlap(IAnimation a) {
    return a.isOverlap(this);
  }

  @Override
  public boolean isOverlap(ChangingColor c) {
    return this.getShapeName().equals(c.getShapeName())
        && this.getStartTick() < c.getEndTick() && this.getEndTick() > c.getStartTick();
  }

  /**
   * Returns the oldColor.
   *
   * @return the oldColor
   */
  public RGBColor getOldColor() {
    return oldColor;
  }

  /**
   * Returns the newColor.
   *
   * @return the newColor
   */
  public RGBColor getNewColor() {
    return newColor;
  }

  /**
   * Returns the RGB color in the intermediate tick t, where t is between the start tick and the end
   * tick of this changing color animation. If the intermediate tick is smaller than the start tick,
   * the tweening color will be the same values of the old color, and if the intermediate tick is
   * larger than the end tick, the tweening color will be the same values of the new color.
   *
   * @param t the intermediate tick between the start tick and the end tick of this animation
   * @return the RGB color at the intermediate tick t
   */
  public RGBColor getTweeningAnimatedColor(int t) {

    double tRed = calculateTweeningValue(this.startTick, this.endTick, t,
        this.oldColor.getRed(), this.newColor.getRed());
    double tGreen = calculateTweeningValue(this.startTick, this.endTick, t,
        this.oldColor.getGreen(), this.newColor.getGreen());
    double tBlue = calculateTweeningValue(this.startTick, this.endTick, t,
        this.oldColor.getBlue(), this.newColor.getBlue());

    return new RGBColor(tRed, tGreen, tBlue);
  }

}
