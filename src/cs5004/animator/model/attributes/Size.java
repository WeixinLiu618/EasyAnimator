package cs5004.animator.model.attributes;

/**
 * A class that represents size in terms of two dimensions. For rectangles, the two dimensions
 * represent width and height respectively. For ovals, the two dimensions denote radius x and radius
 * y respectively.
 *
 * @author Weixin Liu
 */
public class Size {

  private double xLength;
  private double yLength;

  /**
   * Creates a Size object with the given values of the length in x direction and the length in y
   * direction. For rectangles, those are separately width and height, and for ovals, those are
   * separately x radius and y radius.
   *
   * @param xLength the given length in x direction
   * @param yLength the given length in y direction
   * @throws IllegalArgumentException when the given xLength or yLength is non-positive
   */
  public Size(double xLength, double yLength) throws IllegalArgumentException {
    if (xLength <= 0 || yLength <= 0) {
      throw new IllegalArgumentException("The input size values must be positive.");
    }
    this.xLength = xLength;
    this.yLength = yLength;
  }


  /**
   * Returns the length in x direction of this size.
   *
   * @return the length in x direction of this size
   */
  public double getXLength() {
    return this.xLength;
  }

  /**
   * Returns the length in y direction of this size.
   *
   * @return the length in y direction of this size
   */
  public double getYLength() {
    return this.yLength;
  }


}
