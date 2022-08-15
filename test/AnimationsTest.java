import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs5004.animator.model.animations.AnimationType;
import cs5004.animator.model.animations.ChangingColor;
import cs5004.animator.model.animations.IAnimation;
import cs5004.animator.model.animations.Moving;
import cs5004.animator.model.animations.Scaling;
import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;
import org.junit.Test;

/**
 * These are the tests for testing constructors and methods of animations classes including Moving,
 * Scaling and ChangingColor.
 *
 * @author Weixin Liu
 */
public class AnimationsTest {

  IAnimation moving1 = new Moving("R", 1, 10,
      new Position(200, 200), new Position(300, 300));
  IAnimation moving2 = new Moving("C", 40, 60,
      new Position(-20, 30), new Position(30, -200));

  IAnimation scaling1 = new Scaling("R", 3, 23,
      new Size(21, 34), new Size(30, 40));
  IAnimation scaling2 = new Scaling("C", 2, 4,
      new Size(2, 2), new Size(3, 3));

  IAnimation changingColor1 = new ChangingColor("R", 1, 12,
      new RGBColor(255, 0, 0), new RGBColor(0, 255, 0));
  IAnimation changingColor2 = new ChangingColor("C", 5, 10,
      new RGBColor(30, 30, 30), new RGBColor(80, 80, 80));


  /**
   * Tests invalid constructors and should throw IllegalArgumentException.
   */
  @Test
  public void testInvalidConstructors() {
    try {
      IAnimation m = new Moving("R", -1, 10,
          new Position(200, 200), new Position(300, 300));
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Input start tick or end tick is invalid.", e.getMessage());
    }

    try {
      IAnimation s = new Scaling("R", 23, 3,
          new Size(21, 34), new Size(30, 40));
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Input start tick or end tick is invalid.", e.getMessage());
    }

    try {
      IAnimation c = new ChangingColor("C", -15, -1,
          new RGBColor(30, 30, 30), new RGBColor(80, 80, 80));
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Input start tick or end tick is invalid.", e.getMessage());
    }
  }

  /**
   * Tests get Methods.
   */
  @Test
  public void testGetters() {
    // test getShapeName()
    assertEquals("R", moving1.getShapeName());
    assertEquals("C", moving2.getShapeName());
    assertEquals("R", scaling1.getShapeName());
    assertEquals("C", scaling2.getShapeName());
    assertEquals("R", changingColor1.getShapeName());
    assertEquals("C", changingColor2.getShapeName());

    // test getStartTick()
    assertEquals(1, moving1.getStartTick());
    assertEquals(40, moving2.getStartTick());
    assertEquals(3, scaling1.getStartTick());
    assertEquals(2, scaling2.getStartTick());
    assertEquals(1, changingColor1.getStartTick());
    assertEquals(5, changingColor2.getStartTick());

    // test getEndTick()
    assertEquals(10, moving1.getEndTick());
    assertEquals(60, moving2.getEndTick());
    assertEquals(23, scaling1.getEndTick());
    assertEquals(4, scaling2.getEndTick());
    assertEquals(12, changingColor1.getEndTick());
    assertEquals(10, changingColor2.getEndTick());

    // test getAnimationType()
    assertEquals(AnimationType.MOVE, moving1.getAnimationType());
    assertEquals(AnimationType.MOVE, moving2.getAnimationType());
    assertEquals(AnimationType.SCALE, scaling1.getAnimationType());
    assertEquals(AnimationType.SCALE, scaling2.getAnimationType());
    assertEquals(AnimationType.COLOR, changingColor1.getAnimationType());
    assertEquals(AnimationType.COLOR, changingColor2.getAnimationType());

    // test Moving - getFromPosition(), getToPosition()
    assertEquals("(200.0,200.0)", ((Moving) moving1).getFromPosition().toString());
    assertEquals("(300.0,300.0)", ((Moving) moving1).getToPosition().toString());
    assertEquals("(-20.0,30.0)", ((Moving) moving2).getFromPosition().toString());
    assertEquals("(30.0,-200.0)", ((Moving) moving2).getToPosition().toString());

    // test Scaling - getOldSize(), getNewSize()
    assertEquals(21, ((Scaling) scaling1).getOldSize().getXLength(), 0.001);
    assertEquals(34, ((Scaling) scaling1).getOldSize().getYLength(), 0.001);
    assertEquals(30, ((Scaling) scaling1).getNewSize().getXLength(), 0.001);
    assertEquals(40, ((Scaling) scaling1).getNewSize().getYLength(), 0.001);

    assertEquals(2, ((Scaling) scaling2).getOldSize().getXLength(), 0.001);
    assertEquals(2, ((Scaling) scaling2).getOldSize().getYLength(), 0.001);
    assertEquals(3, ((Scaling) scaling2).getNewSize().getXLength(), 0.001);
    assertEquals(3, ((Scaling) scaling2).getNewSize().getYLength(), 0.001);

    // test ChangingColor - getOldColor(), getNewColor()

    assertEquals("(255,0,0)", ((ChangingColor) changingColor1).getOldColor().toString());
    assertEquals("(0,255,0)", ((ChangingColor) changingColor1).getNewColor().toString());

    assertEquals("(30,30,30)", ((ChangingColor) changingColor2).getOldColor().toString());
    assertEquals("(80,80,80)", ((ChangingColor) changingColor2).getNewColor().toString());
  }

  /**
   * Tests method compareTo(IAnimation o).
   */
  @Test
  public void testCompareTo() {
    assertTrue(moving1.compareTo(moving2) < 0);
    assertTrue(moving1.compareTo(scaling1) < 0);
    assertTrue(moving1.compareTo(scaling2) < 0);
    assertTrue(moving1.compareTo(changingColor1) == 0);
    assertTrue(moving1.compareTo(changingColor2) < 0);
    assertTrue(changingColor2.compareTo(moving2) < 0);
    assertTrue(scaling2.compareTo(scaling1) < 0);
    assertTrue(scaling1.compareTo(changingColor2) < 0);
  }

  /**
   * Tests whether two animations are overlapping.
   */
  @Test
  public void testIsOverlap() {
    IAnimation moving3 = new Moving("R", 2, 12,
        new Position(200, 200), new Position(100, 300));
    IAnimation moving4 = new Moving("C", 1, 10,
        new Position(200, 200), new Position(300, 300));
    IAnimation scaling3 = new Scaling("C", 3, 5,
        new Size(2, 2), new Size(3, 3));
    IAnimation changingColor3 = new ChangingColor("R", 5, 8,
        new RGBColor(255, 255, 0), new RGBColor(0, 255, 0));

    assertTrue(moving1.isOverlap(moving1));
    assertTrue(moving1.isOverlap(moving3));
    assertTrue(moving3.isOverlap(moving1));
    assertTrue(scaling2.isOverlap(scaling3));
    assertTrue(changingColor1.isOverlap(changingColor3));

    assertFalse(moving1.isOverlap(moving2));
    assertFalse(moving2.isOverlap(moving1));
    assertFalse(moving2.isOverlap(moving3));
    assertFalse(moving3.isOverlap(moving2));

    assertFalse(moving1.isOverlap(moving4));
    assertFalse(moving1.isOverlap(scaling1));
    assertFalse(moving1.isOverlap(scaling2));

    assertFalse(moving1.isOverlap(changingColor1));
    assertFalse(scaling1.isOverlap(scaling3));
    assertFalse(changingColor3.isOverlap(changingColor2));
    assertFalse(scaling1.isOverlap(scaling2));
    assertFalse(changingColor2.isOverlap(moving2));
    assertFalse(changingColor1.isOverlap(scaling3));
  }

  /**
   * Tests the methods getTweeningAnimatedPosition(int t), getTweeningAnimatedSize(int t) and
   * getTweeningAnimatedColor(int t).
   */
  @Test
  public void testGetTweeningAttributes() {

    Position actual1 = ((Moving) moving1).getTweeningAnimatedPosition(0);
    assertEquals("(200.0,200.0)", actual1.toString());
    actual1 = ((Moving) moving1).getTweeningAnimatedPosition(11);
    assertEquals("(300.0,300.0)", actual1.toString());
    actual1 = ((Moving) moving1).getTweeningAnimatedPosition(5);
    assertEquals("(244.4,244.4)", actual1.toString());

    Position actual2 = ((Moving) moving2).getTweeningAnimatedPosition(40);
    assertEquals("(-20.0,30.0)", actual2.toString());
    actual2 = ((Moving) moving2).getTweeningAnimatedPosition(60);
    assertEquals("(30.0,-200.0)", actual2.toString());
    actual2 = ((Moving) moving2).getTweeningAnimatedPosition(50);
    assertEquals("(5.0,-85.0)", actual2.toString());

    Size actual3 = ((Scaling) scaling1).getTweeningAnimatedSize(2);
    assertEquals(21, actual3.getXLength(), 0.001);
    assertEquals(34, actual3.getYLength(), 0.001);
    actual3 = ((Scaling) scaling1).getTweeningAnimatedSize(25);
    assertEquals(30, actual3.getXLength(), 0.001);
    assertEquals(40, actual3.getYLength(), 0.001);
    actual3 = ((Scaling) scaling1).getTweeningAnimatedSize(5);
    assertEquals(21.9, actual3.getXLength(), 0.001);
    assertEquals(34.6, actual3.getYLength(), 0.001);

    Size actual4 = ((Scaling) scaling2).getTweeningAnimatedSize(1);
    assertEquals(2, actual4.getXLength(), 0.001);
    assertEquals(2, actual4.getYLength(), 0.001);
    actual4 = ((Scaling) scaling2).getTweeningAnimatedSize(6);
    assertEquals(3, actual4.getXLength(), 0.001);
    assertEquals(3, actual4.getYLength(), 0.001);
    actual4 = ((Scaling) scaling2).getTweeningAnimatedSize(3);
    assertEquals(2.5, actual4.getXLength(), 0.001);
    assertEquals(2.5, actual4.getYLength(), 0.001);

    RGBColor actual5 = ((ChangingColor) changingColor1).getTweeningAnimatedColor(1);
    assertEquals("(255,0,0)", actual5.toString());
    actual5 = ((ChangingColor) changingColor1).getTweeningAnimatedColor(12);
    assertEquals("(0,255,0)", actual5.toString());
    actual5 = ((ChangingColor) changingColor1).getTweeningAnimatedColor(10);
    assertEquals("(46,209,0)", actual5.toString());

    RGBColor actual6 = ((ChangingColor) changingColor2).getTweeningAnimatedColor(1);
    assertEquals("(30,30,30)", actual6.toString());
    actual6 = ((ChangingColor) changingColor2).getTweeningAnimatedColor(10);
    assertEquals("(80,80,80)", actual6.toString());
    actual6 = ((ChangingColor) changingColor2).getTweeningAnimatedColor(6);
    assertEquals("(40,40,40)", actual6.toString());
  }


}
