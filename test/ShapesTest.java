import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import org.junit.Test;

/**
 * Tests constructors and all methods of shape classes including Oval and Rectangle.
 *
 * @author Weixin Liu
 */
public class ShapesTest {

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
   * Tests invalid constructors and should throw IllegalArgumentException.
   */
  @Test
  public void testInvalidConstructor() {
    try {
      Rectangle r3 = new Rectangle("R", new Position(0, 0), new Size(20, 30),
          new RGBColor(255, 0, 0), -1, 10);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Appear tick or disappear tick cannot be negative.", e.getMessage());
    }

    try {
      Rectangle r4 = new Rectangle("R", new Position(0, 0), new Size(20, 30),
          new RGBColor(255, 0, 0), -34, -3);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Appear tick or disappear tick cannot be negative.", e.getMessage());
    }

    try {
      Oval r3 = new Oval("O", new Position(0, 0), new Size(20, 30),
          new RGBColor(255, 0, 0), 32, 20);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Disappear tick cannot be less than appear tick.", e.getMessage());
    }

  }

  /**
   * Tests getter methods, getName(), getShapeType(), getPosition(), getSize(), getColor(),
   * getAppearTick(), getDisappearTick().
   */
  @Test
  public void testGetters() {
    // test getName()
    assertEquals("R1", r1.getName());
    assertEquals("R2", r2.getName());
    assertEquals("O1", o1.getName());
    assertEquals("O2", o2.getName());

    // test getShapeType()
    assertEquals(ShapeType.RECTANGLE, r1.getShapeType());
    assertEquals(ShapeType.RECTANGLE, r2.getShapeType());
    assertEquals(ShapeType.OVAL, o1.getShapeType());
    assertEquals(ShapeType.OVAL, o2.getShapeType());

    // test getPosition()
    assertEquals("(0.0,0.0)", r1.getPosition().toString());
    assertEquals("(-20.0,30.0)", r2.getPosition().toString());
    assertEquals("(200.0,300.0)", o1.getPosition().toString());
    assertEquals("(25.0,40.0)", o2.getPosition().toString());

    // test getSize()
    assertEquals(20, r1.getSize().getXLength(), 0.001);
    assertEquals(30, r1.getSize().getYLength(), 0.001);
    assertEquals(45, r2.getSize().getXLength(), 0.001);
    assertEquals(65, r2.getSize().getYLength(), 0.001);
    assertEquals(120, o1.getSize().getXLength(), 0.001);
    assertEquals(60, o1.getSize().getYLength(), 0.001);
    assertEquals(20, o2.getSize().getXLength(), 0.001);
    assertEquals(30, o2.getSize().getYLength(), 0.001);

    // test getColor()
    assertEquals("(255,0,0)", r1.getColor().toString());
    assertEquals("(30,30,30)", r2.getColor().toString());
    assertEquals("(0,255,255)", o1.getColor().toString());
    assertEquals("(100,0,200)", o2.getColor().toString());

    // test getAppearTick()
    assertEquals(1, r1.getAppearTick());
    assertEquals(6, r2.getAppearTick());
    assertEquals(10, o1.getAppearTick());
    assertEquals(15, o2.getAppearTick());

    // test getDisappearTick()
    assertEquals(10, r1.getDisappearTick());
    assertEquals(20, r2.getDisappearTick());
    assertEquals(50, o1.getDisappearTick());
    assertEquals(30, o2.getDisappearTick());

    // at first, the size of shape animation list should be 0
    assertTrue(r1.getShapeAnimations().isEmpty());
    assertTrue(r2.getShapeAnimations().isEmpty());
    assertTrue(o1.getShapeAnimations().isEmpty());
    assertTrue(o1.getShapeAnimations().isEmpty());

  }

  /**
   * Tests setter methods,setPosition(Position position), setColor(RGBColor color), setSize(Size
   * size), setDisappearTick(int disappearTick).
   */
  @Test
  public void testSetters() {

    // test setPosition
    r1.setPosition(new Position(10, 20));
    assertEquals("(10.0,20.0)", r1.getPosition().toString());
    r1.setPosition(new Position(30, 50));
    assertEquals("(30.0,50.0)", r1.getPosition().toString());
    o1.setPosition(new Position(-200, -300));
    assertEquals("(-200.0,-300.0)", o1.getPosition().toString());

    // test setColor
    r1.setColor(new RGBColor(255, 255, 255));
    assertEquals("(255,255,255)", r1.getColor().toString());
    r1.setColor(new RGBColor(0, 0, 0));
    assertEquals("(0,0,0)", r1.getColor().toString());
    o1.setColor(new RGBColor(100, 100, 100));
    assertEquals("(100,100,100)", o1.getColor().toString());

    // test setSize
    r1.setSize(new Size(10, 30));
    assertEquals(10, r1.getSize().getXLength(), 0.001);
    assertEquals(30, r1.getSize().getYLength(), 0.001);
    r1.setSize(new Size(30, 30));
    assertEquals(30, r1.getSize().getXLength(), 0.001);
    assertEquals(30, r1.getSize().getYLength(), 0.001);
    o1.setSize(new Size(50, 100));
    assertEquals(50, o1.getSize().getXLength(), .001);
    assertEquals(100, o1.getSize().getYLength(), .001);

    // test setDisappearTick
    r1.setDisappearTick(12);
    assertEquals(12, r1.getDisappearTick());
    r1.setDisappearTick(20);
    assertEquals(20, r1.getDisappearTick());
    o1.setDisappearTick(59);
    assertEquals(59, o1.getDisappearTick());

  }

  /**
   * Tests the method addShapeAnimation(IAnimation animation).
   */
  @Test
  public void testAddShapeAnimation() {

    r1.addShapeAnimation(m1);
    assertEquals(1, r1.getShapeAnimations().size());
    assertTrue(r1.getShapeAnimations().contains(m1));
    assertEquals(10, r1.getDisappearTick());
    r1.addShapeAnimation(m4);
    assertEquals(2, r1.getShapeAnimations().size());
    assertTrue(r1.getShapeAnimations().contains(m4));
    assertEquals(12, r1.getDisappearTick());
    r1.addShapeAnimation(s1);
    assertEquals(3, r1.getShapeAnimations().size());
    assertTrue(r1.getShapeAnimations().contains(s1));
    assertEquals(12, r1.getDisappearTick());
    r1.addShapeAnimation(c1);
    assertEquals(4, r1.getShapeAnimations().size());
    assertTrue(r1.getShapeAnimations().contains(c1));
    assertEquals(15, r1.getDisappearTick());

    for (int i = 1; i < r1.getShapeAnimations().size(); i++) {
      assertTrue(r1.getShapeAnimations().get(i).compareTo(r1.getShapeAnimations().get(i - 1)) >= 0);
    }

    try {
      // overlap
      r1.addShapeAnimation(m2);
      fail("Should not add this animation.");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid new animation during overlapping tick "
          + "intervals.", e.getMessage());
    }
    try {
      // different shape name
      r1.addShapeAnimation(m3);
      fail("Should not add this animation.");
    } catch (IllegalArgumentException e) {
      assertEquals("The given animation and this shape do not match.", e.getMessage());
    }

    assertEquals(4, r1.getShapeAnimations().size());
    assertFalse(r1.getShapeAnimations().contains(m2));
    assertFalse(r1.getShapeAnimations().contains(m3));

  }

  /**
   * Test the method getShapeAtTick(int t).
   */
  @Test
  public void testGetShapeAtTick() {

    // test rectangle
    r1.addShapeAnimation(m1);
    r1.addShapeAnimation(s1);
    r1.addShapeAnimation(c1);

    IReadOnlyShape actual = r1.getShapeAtTick(0);
    assertEquals("(0.0,0.0)", actual.getPosition().toString());
    assertEquals(20, actual.getSize().getXLength(), 0.001);
    assertEquals(30, actual.getSize().getYLength(), 0.001);
    assertEquals("(255,0,0)", actual.getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.getShapeType());
    assertNotEquals(r1, actual);

    actual = r1.getShapeAtTick(4);
    assertEquals("(25.0,25.0)", actual.getPosition().toString());
    assertEquals(30, actual.getSize().getXLength(), 0.001);
    assertEquals(40, actual.getSize().getYLength(), 0.001);
    assertEquals("(255,0,0)", actual.getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.getShapeType());
    assertNotEquals(r1, actual);

    actual = r1.getShapeAtTick(13);
    assertEquals("(50.0,50.0)", actual.getPosition().toString());
    assertEquals(40, actual.getSize().getXLength(), 0.001);
    assertEquals(50, actual.getSize().getYLength(), 0.001);
    assertEquals("(128,0,128)", actual.getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.getShapeType());
    assertNotEquals(r1, actual);

    actual = r1.getShapeAtTick(16);
    assertEquals("(50.0,50.0)", actual.getPosition().toString());
    assertEquals(40, actual.getSize().getXLength(), 0.001);
    assertEquals(50, actual.getSize().getYLength(), 0.001);
    assertEquals("(0,0,255)", actual.getColor().toString());
    assertEquals(ShapeType.RECTANGLE, actual.getShapeType());
    assertNotEquals(r1, actual);

    // position, size and color of r1 are not mutated
    assertEquals("(0.0,0.0)", r1.getPosition().toString());
    assertEquals(20, r1.getSize().getXLength(), 0.001);
    assertEquals(30, r1.getSize().getYLength(), 0.001);
    assertEquals(15, r1.getDisappearTick());

    // test oval
    o1.addShapeAnimation(s2);
    o1.addShapeAnimation(c2);

    actual = o1.getShapeAtTick(10);
    assertEquals("(200.0,300.0)", actual.getPosition().toString());
    assertEquals(120, actual.getSize().getXLength(), 0.001);
    assertEquals(60, actual.getSize().getYLength(), 0.001);
    assertEquals("(0,255,255)", actual.getColor().toString());
    assertEquals(ShapeType.OVAL, actual.getShapeType());
    assertNotEquals(o1, actual);

    actual = o1.getShapeAtTick(35);
    assertEquals("(200.0,300.0)", actual.getPosition().toString());
    assertEquals(120, actual.getSize().getXLength(), 0.001);
    assertEquals(60, actual.getSize().getYLength(), 0.001);
    assertEquals("(0,128,255)", actual.getColor().toString());
    assertEquals(ShapeType.OVAL, actual.getShapeType());
    assertNotEquals(o1, actual);

    actual = o1.getShapeAtTick(55);
    assertEquals("(200.0,300.0)", actual.getPosition().toString());
    assertEquals(90, actual.getSize().getXLength(), 0.001);
    assertEquals(60, actual.getSize().getYLength(), 0.001);
    assertEquals("(0,0,255)", actual.getColor().toString());
    assertEquals(ShapeType.OVAL, actual.getShapeType());
    assertNotEquals(o1, actual);

    assertEquals("(200.0,300.0)", o1.getPosition().toString());
    assertEquals(120, o1.getSize().getXLength(), 0.001);
    assertEquals(60, o1.getSize().getYLength(), 0.001);
    assertEquals(60, o1.getDisappearTick());

  }


}
