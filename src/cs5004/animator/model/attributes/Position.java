package cs5004.animator.model.attributes;

/**
 * The class Position represents a 2D coordinate position. For rectangles, the position represents
 * the min-corner of them. For ovals, the position represents the center of them.
 *
 * @author Weixin Liu
 */
public class Position {

  private double x;
  private double y;

  /**
   * Creates a Position object with the given values of x coordinate and y coordinate.
   *
   * @param x the given x coordinate value
   * @param y the given y coordinate value
   */
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns x coordinate of this position.
   *
   * @return x coordinate of this position
   */
  public double getX() {
    return x;
  }

  /**
   * Returns y coordinate of this position.
   *
   * @return y coordinate of this position
   */
  public double getY() {
    return y;
  }

  /**
   * Returns a string that describes this position.
   *
   * @return a string that describes this position.
   */
  @Override
  public String toString() {
    return String.format("(%.1f,%.1f)", this.x, this.y);
  }

}
