import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs5004.animator.model.attributes.Canvas;
import cs5004.animator.model.attributes.Position;
import cs5004.animator.model.attributes.RGBColor;
import cs5004.animator.model.attributes.Size;
import java.util.Random;

import org.junit.Test;

/**
 * This is the test for testing classes in the model.attributes package.
 *
 * @author Weixin Liu
 */
public class AttributesTest {

  Random random = new Random();

  /**
   * Tests constructor, getter, and setter methods of class Canvas.
   */
  @Test
  public void testCanvas() {
    Canvas c;
    int x;
    int y;
    int width;
    int height;

    // test valid constructor and getter method
    for (int test = 0; test < 1000; test++) {
      x = random.nextInt();
      y = random.nextInt();
      width = random.nextInt(1000) + 1;
      height = random.nextInt(1000) + 1;
      c = new Canvas(x, y, width, height);
      assertEquals(x, c.getCanvasX());
      assertEquals(y, c.getCanvasY());
      assertEquals(width, c.getCanvasWidth());
      assertEquals(height, c.getCanvasHeight());
    }

    // test the constructor with non-positive number and throws an exception
    for (int test = 0; test < 1000; test++) {
      x = random.nextInt();
      y = random.nextInt();
      width = -random.nextInt(1000);
      height = -random.nextInt(1000);
      try {
        c = new Canvas(x, y, width, height);
        fail("invalid constructor");
      } catch (IllegalArgumentException e) {
        assertEquals("The input width and height values must be positive.", e.getMessage());
      }
    }

    // test the setter method with valid input
    c = new Canvas(0, 0, 100, 100);
    for (int test = 0; test < 1000; test++) {
      x = random.nextInt();
      y = random.nextInt();
      width = random.nextInt(1000) + 1;
      height = random.nextInt(1000) + 1;
      c.setCanvas(x, y, width, height);
      assertEquals(x, c.getCanvasX());
      assertEquals(y, c.getCanvasY());
      assertEquals(width, c.getCanvasWidth());
      assertEquals(height, c.getCanvasHeight());
    }

    // test the setter method with non-positive width or height and throws an exception
    for (int test = 0; test < 1000; test++) {
      x = random.nextInt();
      y = random.nextInt();
      width = -random.nextInt(1000);
      height = -random.nextInt(1000);
      try {
        c.setCanvas(x, y, width, height);
        fail("invalid constructor");
      } catch (IllegalArgumentException e) {
        assertEquals("The input width and height values must be positive.", e.getMessage());
      }
    }
  }

  /**
   * Tests constructor, getter, and toString methods of class Position.
   */
  @Test
  public void testPosition() {
    Position p1 = new Position(2.35, -3);
    Position p2 = new Position(0, 0);
    Position p3 = new Position(-12.01, -1.344);
    Position p4 = new Position(23, 32);

    assertEquals(2.35, p1.getX(), 0.001);
    assertEquals(-3, p1.getY(), 0.001);
    assertEquals(0, p2.getX(), 0.001);
    assertEquals(0, p2.getY(), 0.001);
    assertEquals(-12.01, p3.getX(), 0.001);
    assertEquals(-1.344, p3.getY(), 0.001);
    assertEquals(23, p4.getX(), 0.001);
    assertEquals(32, p4.getY(), 0.001);

    assertEquals("(2.4,-3.0)", p1.toString());
    assertEquals("(0.0,0.0)", p2.toString());
    assertEquals("(-12.0,-1.3)", p3.toString());
    assertEquals("(23.0,32.0)", p4.toString());
  }

  /**
   * Tests constructor, getter method of class Size.
   */
  @Test
  public void testSize() {
    Size s1 = new Size(2, 2);
    Size s2 = new Size(4.5, 6.5);
    Size s3 = new Size(10.22, 7.46);

    assertEquals(2, s1.getXLength(), 0.001);
    assertEquals(2, s1.getYLength(), 0.001);
    assertEquals(4.5, s2.getXLength(), 0.001);
    assertEquals(6.5, s2.getYLength(), 0.001);
    assertEquals(10.22, s3.getXLength(), 0.001);
    assertEquals(7.46, s3.getYLength(), 0.001);

    try {
      Size s4 = new Size(0, 1);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("The input size values must be positive.", e.getMessage());
    }
    try {
      Size s5 = new Size(4, 0);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("The input size values must be positive.", e.getMessage());
    }
    try {
      Size s6 = new Size(-4, -3);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("The input size values must be positive.", e.getMessage());
    }
  }


  /**
   * Tests constructor, getter and toString method of class RGBColor.
   */
  @Test
  public void testRGBColor() {
    RGBColor rgb1 = new RGBColor(0, 255, 255);
    RGBColor rgb2 = new RGBColor(30, 65, 40);
    RGBColor rgb3 = new RGBColor(182, 20, 49);
    RGBColor rgb4 = new RGBColor(84.4, 20.5, 100.1);

    assertEquals(0, rgb1.getRed(), 0.001);
    assertEquals(255, rgb1.getGreen(), 0.001);
    assertEquals(255, rgb1.getBlue(), 0.001);

    assertEquals(30, rgb2.getRed(), 0.001);
    assertEquals(65, rgb2.getGreen(), 0.001);
    assertEquals(40, rgb2.getBlue(), 0.001);

    assertEquals(182, rgb3.getRed(), 0.001);
    assertEquals(20, rgb3.getGreen(), 0.001);
    assertEquals(49, rgb3.getBlue(), 0.001);

    assertEquals(84.4, rgb4.getRed(), 0.001);
    assertEquals(20.5, rgb4.getGreen(), 0.001);
    assertEquals(100.1, rgb4.getBlue(), 0.001);

    assertEquals("(0,255,255)", rgb1.toString());
    assertEquals("(30,65,40)", rgb2.toString());
    assertEquals("(182,20,49)", rgb3.toString());
    assertEquals("(84,21,100)", rgb4.toString());

    try {
      RGBColor rgb5 = new RGBColor(256, 200, 0);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid input of rgb.", e.getMessage());
    }
    try {
      RGBColor rgb6 = new RGBColor(0, 300, 0);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid input of rgb.", e.getMessage());
    }
    try {
      RGBColor rgb7 = new RGBColor(3, 200, 2366);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid input of rgb.", e.getMessage());
    }
    try {
      RGBColor rgb8 = new RGBColor(-23, 200, 0);
      fail("invalid constructor");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid input of rgb.", e.getMessage());
    }

  }


}
