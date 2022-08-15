import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.animations.ChangingColor;
import cs5004.animator.model.animations.IAnimation;
import cs5004.animator.model.animations.Moving;
import cs5004.animator.model.animations.Scaling;
import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;
import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.model.shapes.Oval;
import cs5004.animator.model.shapes.Rectangle;
import cs5004.animator.model.shapes.ShapeType;
import java.util.List;
import org.junit.Test;

/**
 * Tests constructors and all methods of the AnimationModel.
 *
 * @author Weixin Liu
 */
public class AnimationModelTest {

  AnimationModel model;

  Rectangle r1 = new Rectangle("R1", new Position(0, 0), new Size(20, 30),
      new RGBColor(255, 0, 0), 1, 10);
  Rectangle r2 = new Rectangle("R2", new Position(-20, 30), new Size(45, 65),
      new RGBColor(30, 30, 30), 6, 20);

  Oval o1 = new Oval("O1", new Position(200, 300), new Size(120, 60),
      new RGBColor(0, 255, 255), 10, 50);
  Oval o2 = new Oval("O2", new Position(25, 40), new Size(20, 30),
      new RGBColor(100, 0, 200), 15, 30);

  IAnimation m1 = new Moving("R1", 3, 5,
      new Position(0, 0), new Position(50, 50));
  IAnimation m2 = new Moving("R1", 2, 7,
      new Position(10, 0), new Position(50, 50));
  IAnimation m3 = new Moving("R2", 24, 70,
      new Position(-20, 30), new Position(200, 300));
  IAnimation m4 = new Moving("R1", 5, 12,
      new Position(50, 50), new Position(60, 60));

  IAnimation s1 = new Scaling("R1", 3, 5, new Size(20, 30),
      new Size(40, 50));
  IAnimation s2 = new Scaling("O1", 50, 60,
      new Size(120, 60), new Size(60, 60));

  IAnimation c1 = new ChangingColor("R1", 11, 15,
      new RGBColor(255, 0, 0), new RGBColor(0, 0, 255));
  IAnimation c2 = new ChangingColor("O1", 30, 40,
      new RGBColor(0, 255, 255), new RGBColor(0, 0, 255));

  /**
   * Tests the constructor.
   */
  @Test
  public void testConstructor() {
    model = new AnimationModelImpl();
    assertTrue(model.getShapes().isEmpty());
    assertTrue(model.getAnimations().isEmpty());
    assertTrue(model.getDeclaredShapes().isEmpty());
    assertEquals(0, model.getCanvas().getCanvasX());
    assertEquals(0, model.getCanvas().getCanvasY());
    assertEquals(1000, model.getCanvas().getCanvasWidth());
    assertEquals(1000, model.getCanvas().getCanvasHeight());
  }

  /**
   * Tests the method addShape(Ishape shape).
   */
  @Test
  public void testAddShape() {
    model = new AnimationModelImpl();
    assertTrue(model.getShapes().isEmpty());
    model.addShape(r1);
    assertEquals(1, model.getShapes().size());
    assertTrue(model.getShapes().contains(r1));
    model.addShape(o1);
    assertEquals(2, model.getShapes().size());
    assertTrue(model.getShapes().contains(o1));
    model.addShape(r2);
    assertEquals(3, model.getShapes().size());
    assertTrue(model.getShapes().contains(r2));
    model.addShape(o2);
    assertEquals(4, model.getShapes().size());
    assertTrue(model.getShapes().contains(o2));

    Rectangle r3 = new Rectangle("R1", new Position(-20, 30), new Size(45, 65),
        new RGBColor(30, 30, 30), 6, 20);
    try {
      model.addShape(r3);
      fail("Should not add this shape, but added");
    } catch (IllegalArgumentException e) {
      assertEquals("The shape with this name has already existed.", e.getMessage());
    }
    assertFalse(model.getShapes().contains(r3));

  }

  /**
   * Tests the method removeShape(String shapeName).
   */
  @Test
  public void testRemoveShape() {
    model = new AnimationModelImpl();
    model.addShape(r1);
    model.addShape(o1);
    model.addShape(r2);
    model.addShape(o2);

    model.removeShape("R1");
    assertEquals(3, model.getShapes().size());
    assertFalse(model.getShapes().contains(r1));
    model.removeShape("R2");
    assertEquals(2, model.getShapes().size());
    assertFalse(model.getShapes().contains(r2));

    model.removeShape("R3");
    assertEquals(2, model.getShapes().size());
    assertTrue(model.getShapes().contains(o1));
    assertTrue(model.getShapes().contains(o2));
  }

  /**
   * Tests the method findShape(String name).
   */
  @Test
  public void testFindShape() {
    model = new AnimationModelImpl();
    model.addShape(r1);
    model.addShape(o1);
    model.addShape(r2);
    model.addShape(o2);

    assertSame(r1, model.findShape("R1"));
    assertSame(o1, model.findShape("O1"));
    assertNull(model.findShape("R3"));
    assertNull(model.findShape("O3"));
  }

  /**
   * Tests the method addAnimation(IAnimation animation).
   */
  @Test
  public void testAddAnimation() {
    model = new AnimationModelImpl();
    model.addShape(r1);
    model.addShape(o1);

    model.addAnimation(m1);
    assertEquals(1, model.getAnimations().size());
    assertTrue(model.getAnimations().contains(m1));
    assertEquals(1, r1.getShapeAnimations().size());
    assertTrue(r1.getShapeAnimations().contains(m1));

    model.addAnimation(s1);
    assertEquals(2, model.getAnimations().size());
    assertTrue(model.getAnimations().contains(s1));
    assertEquals(2, r1.getShapeAnimations().size());
    assertTrue(r1.getShapeAnimations().contains(s1));

    model.addAnimation(c1);
    assertEquals(3, model.getAnimations().size());
    assertTrue(model.getAnimations().contains(c1));
    assertEquals(3, r1.getShapeAnimations().size());
    assertTrue(r1.getShapeAnimations().contains(c1));

    model.addAnimation(s2);
    assertEquals(4, model.getAnimations().size());
    assertTrue(model.getAnimations().contains(s2));
    assertEquals(1, o1.getShapeAnimations().size());
    assertTrue(o1.getShapeAnimations().contains(s2));
    assertFalse(r1.getShapeAnimations().contains(s2));

    model.addAnimation(c2);
    assertEquals(5, model.getAnimations().size());
    assertTrue(model.getAnimations().contains(c2));
    assertEquals(2, o1.getShapeAnimations().size());
    assertTrue(o1.getShapeAnimations().contains(c2));
    assertFalse(r1.getShapeAnimations().contains(c2));

    try {
      // overlap
      model.addAnimation(m2);
      fail("Should not add this animation, but added.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid new animation during overlapping tick "
          + "intervals.", e.getMessage());
    }

    // the shape name of m3 is not in the model, it will not be added
    model.addAnimation(m3);
    assertEquals(5, model.getAnimations().size());
    assertFalse(model.getAnimations().contains(m2));
    assertFalse(model.getAnimations().contains(m3));

    // the animation list should be sorted
    for (int i = 1; i < model.getAnimations().size(); i++) {
      assertTrue(model.getAnimations().get(i).compareTo(model.getAnimations().get(i - 1)) >= 0);
    }

  }

  /**
   * Tests the method getEndTick().
   */
  @Test
  public void testGetEndTick() {

    model = new AnimationModelImpl();
    model.addShape(r1);
    assertEquals(10, model.getEndTick());
    model.addAnimation(m1);
    assertEquals(10, model.getEndTick());
    model.addAnimation(s1);
    assertEquals(10, model.getEndTick());
    model.addAnimation(c1);
    assertEquals(15, model.getEndTick());
    model.addShape(o1);
    assertEquals(50, model.getEndTick());
    model.addAnimation(s2);
    assertEquals(60, model.getEndTick());
    model.addAnimation(c2);
    assertEquals(60, model.getEndTick());
  }

  /**
   * Tests the method getReadOnlyShapes().
   */
  @Test
  public void testGetReadOnlyShapes() {
    model = new AnimationModelImpl();
    model.addShape(r1);
    model.addShape(o1);
    model.addShape(r2);
    model.addShape(o2);
    model.addAnimation(m1);
    model.addAnimation(s1);
    model.addAnimation(c1);
    model.addAnimation(s2);
    model.addAnimation(c2);

    List<IReadOnlyShape> actual = model.getReadOnlyShapes();

    assertEquals(model.getShapes().size(), actual.size());
    for (int i = 0; i < actual.size(); i++) {
      assertEquals(model.getShapes().get(i).getShapeType(),
          actual.get(i).getShapeType());
      assertEquals(model.getShapes().get(i).getPosition().getX(),
          actual.get(i).getPosition().getX(), 0.001);
      assertEquals(model.getShapes().get(i).getPosition().getY(),
          actual.get(i).getPosition().getY(), 0.001);
      assertEquals(model.getShapes().get(i).getSize().getXLength(),
          actual.get(i).getSize().getXLength(), 0.001);
      assertEquals(model.getShapes().get(i).getSize().getYLength(),
          actual.get(i).getSize().getYLength(), 0.001);
      assertEquals(model.getShapes().get(i).getColor().getRed(),
          actual.get(i).getColor().getRed(), 0.001);
      assertEquals(model.getShapes().get(i).getColor().getGreen(),
          actual.get(i).getColor().getGreen(), 0.001);
      assertEquals(model.getShapes().get(i).getColor().getBlue(),
          actual.get(i).getColor().getBlue(), 0.001);
      assertEquals(model.getShapes().get(i).getAppearTick(),
          actual.get(i).getAppearTick());
      assertEquals(model.getShapes().get(i).getDisappearTick(),
          actual.get(i).getDisappearTick());
      assertEquals(model.getShapes().get(i).getShapeAnimations().size(),
          actual.get(i).getShapeAnimations().size());
    }


  }

  /**
   * Tests the method getTweeningShapes(int t).
   */
  @Test
  public void testGetTweeningShapes() {
    model = new AnimationModelImpl();
    model.addShape(r1);
    model.addShape(o1);

    model.addAnimation(m1);
    model.addAnimation(s1);
    model.addAnimation(c1);
    model.addAnimation(s2);
    model.addAnimation(c2);

    // t = 0
    List<IReadOnlyShape> actual = model.getTweeningShapes(0);
    assertEquals(2, actual.size());
    assertEquals("(0.0,0.0)", actual.get(0).getPosition().toString());
    assertEquals(20, actual.get(0).getSize().getXLength(), 0.001);
    assertEquals(30, actual.get(0).getSize().getYLength(), 0.001);
    assertEquals("(255,0,0)", actual.get(0).getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.get(0).getShapeType());
    assertNotEquals(r1, actual.get(0));
    assertEquals("(200.0,300.0)", actual.get(1).getPosition().toString());
    assertEquals(120, actual.get(1).getSize().getXLength(), 0.001);
    assertEquals(60, actual.get(1).getSize().getYLength(), 0.001);
    assertEquals("(0,255,255)", actual.get(1).getColor().toString());
    assertEquals(ShapeType.OVAL, actual.get(1).getShapeType());
    assertNotEquals(o1, actual.get(1));

    // t = 4
    actual = model.getTweeningShapes(4);
    assertEquals(2, actual.size());
    assertEquals("(25.0,25.0)", actual.get(0).getPosition().toString());
    assertEquals(30, actual.get(0).getSize().getXLength(), 0.001);
    assertEquals(40, actual.get(0).getSize().getYLength(), 0.001);
    assertEquals("(255,0,0)", actual.get(0).getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.get(0).getShapeType());
    assertNotEquals(r1, actual.get(0));
    assertEquals("(200.0,300.0)", actual.get(1).getPosition().toString());
    assertEquals(120, actual.get(1).getSize().getXLength(), 0.001);
    assertEquals(60, actual.get(1).getSize().getYLength(), 0.001);
    assertEquals("(0,255,255)", actual.get(1).getColor().toString());
    assertEquals(ShapeType.OVAL, actual.get(1).getShapeType());
    assertNotEquals(o1, actual.get(1));

    // t = 13
    actual = model.getTweeningShapes(13);
    assertEquals(2, actual.size());
    assertEquals("(50.0,50.0)", actual.get(0).getPosition().toString());
    assertEquals(40, actual.get(0).getSize().getXLength(), 0.001);
    assertEquals(50, actual.get(0).getSize().getYLength(), 0.001);
    assertEquals("(128,0,128)", actual.get(0).getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.get(0).getShapeType());
    assertNotEquals(r1, actual.get(0));
    assertEquals("(200.0,300.0)", actual.get(1).getPosition().toString());
    assertEquals(120, actual.get(1).getSize().getXLength(), 0.001);
    assertEquals(60, actual.get(1).getSize().getYLength(), 0.001);
    assertEquals("(0,255,255)", actual.get(1).getColor().toString());
    assertEquals(ShapeType.OVAL, actual.get(1).getShapeType());
    assertNotEquals(o1, actual.get(1));

    // t = 35
    actual = model.getTweeningShapes(35);
    assertEquals(2, actual.size());
    assertEquals("(50.0,50.0)", actual.get(0).getPosition().toString());
    assertEquals(40, actual.get(0).getSize().getXLength(), 0.001);
    assertEquals(50, actual.get(0).getSize().getYLength(), 0.001);
    assertEquals("(0,0,255)", actual.get(0).getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.get(0).getShapeType());
    assertNotEquals(r1, actual.get(0));
    assertEquals("(200.0,300.0)", actual.get(1).getPosition().toString());
    assertEquals(120, actual.get(1).getSize().getXLength(), 0.001);
    assertEquals(60, actual.get(1).getSize().getYLength(), 0.001);
    assertEquals("(0,128,255)", actual.get(1).getColor().toString());
    assertEquals(ShapeType.OVAL, actual.get(1).getShapeType());
    assertNotEquals(o1, actual.get(1));

    // t = 55
    actual = model.getTweeningShapes(55);
    assertEquals(2, actual.size());
    assertEquals("(50.0,50.0)", actual.get(0).getPosition().toString());
    assertEquals(40, actual.get(0).getSize().getXLength(), 0.001);
    assertEquals(50, actual.get(0).getSize().getYLength(), 0.001);
    assertEquals("(0,0,255)", actual.get(0).getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.get(0).getShapeType());
    assertNotEquals(r1, actual.get(0));
    assertEquals("(200.0,300.0)", actual.get(1).getPosition().toString());
    assertEquals(90, actual.get(1).getSize().getXLength(), 0.001);
    assertEquals(60, actual.get(1).getSize().getYLength(), 0.001);
    assertEquals("(0,0,255)", actual.get(1).getColor().toString());
    assertEquals(ShapeType.OVAL, actual.get(1).getShapeType());
    assertNotEquals(o1, actual.get(1));

    // t = 60
    actual = model.getTweeningShapes(60);
    assertEquals(2, actual.size());
    assertEquals("(50.0,50.0)", actual.get(0).getPosition().toString());
    assertEquals(40, actual.get(0).getSize().getXLength(), 0.001);
    assertEquals(50, actual.get(0).getSize().getYLength(), 0.001);
    assertEquals("(0,0,255)", actual.get(0).getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.get(0).getShapeType());
    assertNotEquals(r1, actual.get(0));
    assertEquals("(200.0,300.0)", actual.get(1).getPosition().toString());
    assertEquals(60, actual.get(1).getSize().getXLength(), 0.001);
    assertEquals(60, actual.get(1).getSize().getYLength(), 0.001);
    assertEquals("(0,0,255)", actual.get(1).getColor().toString());
    assertEquals(ShapeType.OVAL, actual.get(1).getShapeType());
    assertNotEquals(o1, actual.get(1));

  }


}
