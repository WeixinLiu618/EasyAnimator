package cs5004.animator.model.shapes;

import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;


/**
 * This class represents an oval shape and inherits the class AbstractShape.
 *
 * @author Weixin Liu
 */
public class Oval extends AbstractShape {

  /**
   * Creates an oval shape object with the given shape name, center position, size, color, appear
   * tick and disappear tick.
   *
   * @param name          the given name of the oval
   * @param center        the given center position of the oval
   * @param size          the given size of the oval
   * @param color         the given color of the oval
   * @param appearTick    the given appear tick of the oval
   * @param disappearTick the given disappear tick of the oval
   * @throws IllegalArgumentException when the given appear tick or disappear tick is negative, or
   *                                  the given appear tick is larger than the given disappear tick
   */
  public Oval(String name, Position center, Size size, RGBColor color, int appearTick,
      int disappearTick) throws IllegalArgumentException {
    super(name, center, size, color, appearTick, disappearTick);
    this.shapeType = ShapeType.OVAL;
  }


}
