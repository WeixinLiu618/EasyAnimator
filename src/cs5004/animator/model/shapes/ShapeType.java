package cs5004.animator.model.shapes;

/**
 * This enum represents different types of 2D shapes.
 *
 * @author Weixin Liu
 */
public enum ShapeType {

  RECTANGLE("rectangle"), OVAL("oval");

  private final String shapeType;

  /**
   * A constructor of the enum ShapeType with the given shape type string.
   *
   * @param shapeType the shape type string of this ShapeType
   */
  ShapeType(String shapeType) {
    this.shapeType = shapeType;
  }

  /**
   * Returns a string to describe this ShapeType.
   *
   * @return a string to describe this ShapeType
   */
  public String toString() {
    return this.shapeType;
  }
}
