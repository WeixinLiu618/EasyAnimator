package cs5004.animator.model.shapes;

import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;


/**
 * This class represents a rectangle shape and inherits the class AbstractShape.
 *
 * @author Weixin Liu
 */
public class Rectangle extends AbstractShape {


  /**
   * Creates a rectangle shape object with the given shape name, min-corner position, size, color,
   * appear tick and disappear tick.
   *
   * @param name          the given name of the rectangle
   * @param position      the given min-corner position of the rectangle
   * @param size          the given size of the rectangle
   * @param color         the given color of the rectangle
   * @param appearTick    the given appear tick of the rectangle
   * @param disappearTick the given disappear tick of the rectangle
   * @throws IllegalArgumentException when the given appear tick or disappear tick is negative, or
   *                                  the given appear tick is larger than the given disappear tick
   */
  public Rectangle(String name, Position position, Size size,
      RGBColor color, int appearTick, int disappearTick)
      throws IllegalArgumentException {
    super(name, position, size, color, appearTick, disappearTick);
    this.shapeType = ShapeType.RECTANGLE;
  }


}
