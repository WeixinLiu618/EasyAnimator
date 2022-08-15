package cs5004.animator.model.shapes;

import cs5004.animator.model.animations.AnimationType;
import cs5004.animator.model.animations.ChangingColor;
import cs5004.animator.model.animations.IAnimation;
import cs5004.animator.model.animations.Moving;
import cs5004.animator.model.animations.Scaling;
import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is an abstract class representing the shape in the animation model. It implements the Ishape
 * interface and offers most of the operations mandated by the Ishape interface.
 *
 * @author Weixin Liu
 */
public abstract class AbstractShape implements IShape {

  protected final String name;
  protected ShapeType shapeType;
  protected Position position;
  protected RGBColor color;
  protected Size size;

  protected int appearTick;
  protected int disappearTick;
  protected List<IAnimation> animationList;

  /**
   * Defines a constructor of the shape with the given shape name, position, size, color, appear
   * tick and disappear tick. In the same animation, any two shapes cannot have the same name.
   *
   * @param name          the given name of the shape
   * @param position      the given position of the shape
   * @param size          the given size of the shape
   * @param color         the given color of the shape
   * @param appearTick    the given appear tick of the shape
   * @param disappearTick the given disappear tick of the shape
   * @throws IllegalArgumentException when the given appear tick or disappear tick is negative, or
   *                                  the given appear tick is larger than the given disappear tick
   */
  public AbstractShape(String name, Position position, Size size, RGBColor color,
      int appearTick, int disappearTick) throws IllegalArgumentException {

    if (appearTick < 0 || disappearTick < 0) {
      throw new IllegalArgumentException("Appear tick or disappear tick cannot be negative.");
    }
    if (disappearTick < appearTick) {
      throw new IllegalArgumentException("Disappear tick cannot be less than appear tick.");
    }
    this.name = name;
    this.position = position;
    this.size = size;
    this.color = color;
    this.appearTick = appearTick;
    this.disappearTick = disappearTick;
    this.animationList = new ArrayList<>();

  }


  @Override
  public void setPosition(Position position) {
    this.position = position;
  }

  @Override
  public void setColor(RGBColor color) {
    this.color = color;
  }

  @Override
  public void setSize(Size size) {
    this.size = size;
  }


  @Override
  public void setDisappearTick(int disappearTick) {
    this.disappearTick = disappearTick;

  }

  @Override
  public String getName() {
    return this.name;
  }


  @Override
  public ShapeType getShapeType() {
    return this.shapeType;
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public Size getSize() {
    return this.size;
  }

  @Override
  public RGBColor getColor() {
    return this.color;
  }

  @Override
  public int getAppearTick() {
    return this.appearTick;
  }

  @Override
  public int getDisappearTick() {
    return this.disappearTick;
  }

  @Override
  public List<IAnimation> getShapeAnimations() {
    return this.animationList;
  }


  @Override
  public void addShapeAnimation(IAnimation newAnimation) throws IllegalArgumentException {
    for (IAnimation a : this.animationList) {
      if (!newAnimation.getShapeName().equals(this.name)) {
        throw new IllegalArgumentException("The given animation and this shape do not match.");
      }
      if (newAnimation.isOverlap(a)) {
        throw new IllegalArgumentException("Invalid new animation during overlapping tick "
            + "intervals.");
      }
    }
    this.animationList.add(newAnimation);
    Collections.sort(this.animationList);
    if (newAnimation.getEndTick() > this.disappearTick) {
      this.setDisappearTick(newAnimation.getEndTick());
    }
  }


  @Override
  public IReadOnlyShape getShapeAtTick(int t) {

    IShape animatedShape = null;

    if (this.getShapeType() == ShapeType.RECTANGLE) {
      animatedShape = new Rectangle(this.name, this.position, this.size, this.color,
          this.appearTick,
          this.disappearTick);
    } else if (this.getShapeType() == ShapeType.OVAL) {
      animatedShape = new Oval(this.name, this.position, this.size, this.color,
          this.appearTick,
          this.disappearTick);
    }

    if (animatedShape != null) {
      for (IAnimation a : this.getShapeAnimations()) {
        if (t >= a.getStartTick()) {
          if (a.getAnimationType() == AnimationType.MOVE) {
            Moving m = (Moving) a;
            animatedShape.setPosition(m.getTweeningAnimatedPosition(t));
          } else if (a.getAnimationType() == AnimationType.SCALE) {
            Scaling s = (Scaling) a;
            animatedShape.setSize(s.getTweeningAnimatedSize(t));
          } else if (a.getAnimationType() == AnimationType.COLOR) {
            ChangingColor c = (ChangingColor) a;
            animatedShape.setColor(c.getTweeningAnimatedColor(t));
          }
        }
      }
    }
    return animatedShape;
  }


}
