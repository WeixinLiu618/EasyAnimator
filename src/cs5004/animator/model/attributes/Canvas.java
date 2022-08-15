package cs5004.animator.model.attributes;

/**
 * The class represents a canvas with the leftmost x coordinate, the topmost y coordinate, the
 * width, and the height.
 *
 * @author Weixin Liu
 */
public class Canvas {

  private int canvasX;
  private int canvasY;
  private int canvasWidth;
  private int canvasHeight;

  /**
   * Creates a Canvas object with the given values of the leftmost x coordinate, the topmost y
   * coordinate, the width, and the height.
   *
   * @param x      the given leftmost x coordinate
   * @param y      the given topmost y coordinate
   * @param width  the given width of this canvas
   * @param height the given height of this canvas
   * @throws IllegalArgumentException when the input width or height is non-positive
   */
  public Canvas(int x, int y, int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("The input width and height values must be positive.");
    }
    this.canvasX = x;
    this.canvasY = y;
    this.canvasWidth = width;
    this.canvasHeight = height;
  }

  /**
   * Returns the leftmost x coordinate of this canvas.
   *
   * @return the leftmost x coordinate of this canvas
   */
  public int getCanvasX() {
    return canvasX;
  }

  /**
   * Returns the topmost y coordinate of this canvas.
   *
   * @return the topmost y coordinate of this canvas
   */
  public int getCanvasY() {
    return canvasY;
  }


  /**
   * Returns the width of this canvas.
   *
   * @return the width of this canvas
   */
  public int getCanvasWidth() {
    return canvasWidth;
  }

  /**
   * Returns the height of this canvas.
   *
   * @return the height of this canvas
   */
  public int getCanvasHeight() {
    return canvasHeight;
  }

  /**
   * Set the leftmost x coordinate, the topmost y coordinate, the width, and the height parameters
   * of this canvas.
   *
   * @param x      the given leftmost x coordinate to be set
   * @param y      the given topmost y coordinate to be set
   * @param width  the given width of this canvas to be set
   * @param height height the given height of this canvas to be set
   * @throws IllegalArgumentException when the input width or height is non-positive
   */
  public void setCanvas(int x, int y, int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("The input width and height values must be positive.");
    }
    this.canvasX = x;
    this.canvasY = y;
    this.canvasWidth = width;
    this.canvasHeight = height;
  }

}
