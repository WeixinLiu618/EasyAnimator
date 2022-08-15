package cs5004.animator.model.attributes;

/**
 * The class RGBColor represents the rgb values of a color.
 *
 * @author Weixin Liu
 */
public class RGBColor {

  private double red;
  private double green;
  private double blue;

  /**
   * Creates a RGBColor object with the given values of red, green, and blue of a color. The three
   * dimensions of a color should be all in the range of 0 to 255 (both included).
   *
   * @param r the red dimension of the color
   * @param g the green dimension of the color
   * @param b the blue dimension of the color
   * @throws IllegalArgumentException when the input r or g or b is out of bound
   */
  public RGBColor(double r, double g, double b) throws IllegalArgumentException {
    if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0) {
      throw new IllegalArgumentException("Invalid input of rgb.");
    }
    this.red = r;
    this.green = g;
    this.blue = b;
  }


  /**
   * Returns the red component of this color.
   *
   * @return the red component of this color
   */
  public double getRed() {
    return this.red;
  }

  /**
   * Returns the green component of this color.
   *
   * @return the green component of this color
   */
  public double getGreen() {
    return this.green;
  }

  /**
   * Returns the blue component of this color.
   *
   * @return the blue component of this color
   */
  public double getBlue() {
    return this.blue;
  }

  /**
   * Returns a string that describes this RGBColor.
   *
   * @return a string that describes this RGBColor.
   */
  @Override
  public String toString() {
    return String.format("(%.0f,%.0f,%.0f)", this.red, this.green, this.blue);
  }

}
