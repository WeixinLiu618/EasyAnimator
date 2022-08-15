import static junit.framework.TestCase.assertEquals;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import org.junit.Test;

/**
 * Test the static inner class Builder of the class AnimationModelImpl.
 *
 * @author Weixin Liu
 */
public class AnimationBuilderTest {

  AnimationModel model;

  /**
   * Test the method build().
   */
  @Test
  public void testBuild() {
    model = new AnimationModelImpl.Builder().build();
    assertEquals(0, model.getShapes().size());
    assertEquals(0, model.getAnimations().size());
    assertEquals(0, model.getDeclaredShapes().size());
    assertEquals(0, model.getCanvas().getCanvasX());
    assertEquals(0, model.getCanvas().getCanvasY());
    assertEquals(1000, model.getCanvas().getCanvasWidth());
    assertEquals(1000, model.getCanvas().getCanvasHeight());
  }

  /**
   * Tests the method setBounds(int x, int y, int width, int height).
   */
  @Test
  public void TestSetBound() {
    model = new AnimationModelImpl.Builder().setBounds(-20, 10, 800, 600).build();
    assertEquals(0, model.getShapes().size());
    assertEquals(0, model.getAnimations().size());
    assertEquals(0, model.getDeclaredShapes().size());
    assertEquals(-20, model.getCanvas().getCanvasX());
    assertEquals(10, model.getCanvas().getCanvasY());
    assertEquals(800, model.getCanvas().getCanvasWidth());
    assertEquals(600, model.getCanvas().getCanvasHeight());
  }

  /**
   * Tests the method declareShape(String name, String type).
   */
  @Test
  public void TestDeclareShape() {
    model = new AnimationModelImpl.Builder()
        .declareShape("R", "rectangle").build();
    assertEquals(0, model.getShapes().size());
    assertEquals(0, model.getAnimations().size());
    assertEquals(1, model.getDeclaredShapes().size());
    assertEquals(0, model.getCanvas().getCanvasX());
    assertEquals(0, model.getCanvas().getCanvasY());
    assertEquals(1000, model.getCanvas().getCanvasWidth());
    assertEquals(1000, model.getCanvas().getCanvasHeight());

    model = new AnimationModelImpl.Builder()
        .declareShape("R", "rectangle")
        .declareShape("O", "ellipse").build();
    assertEquals(0, model.getShapes().size());
    assertEquals(0, model.getAnimations().size());
    assertEquals(2, model.getDeclaredShapes().size());
    assertEquals(0, model.getCanvas().getCanvasX());
    assertEquals(0, model.getCanvas().getCanvasY());
    assertEquals(1000, model.getCanvas().getCanvasWidth());
    assertEquals(1000, model.getCanvas().getCanvasHeight());

  }

  /**
   * Tests the method addMotion(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
   * int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2).
   */
  @Test
  public void TestAddMotion() {

    model = new AnimationModelImpl.Builder()
        .declareShape("R", "rectangle")
        .addMotion("R", 1, 200, 200, 50, 100, 255, 0, 0,
            10, 200, 200, 50, 100, 255, 0, 0)
        .addMotion("R", 10, 200, 200, 50, 100, 255, 0, 0,
            50, 300, 300, 50, 100, 255, 0, 0)
        .addMotion("R", 50, 300, 300, 50, 100, 255, 0, 0,
            51, 300, 300, 50, 100, 255, 0, 0)
        .addMotion("R", 51, 300, 300, 50, 100, 255, 0, 0,
            70, 300, 300, 25, 100, 255, 0, 0)
        .addMotion("R", 70, 300, 300, 25, 100, 255, 0, 0,
            100, 200, 200, 25, 100, 255, 0, 0)
        .declareShape("C", "ellipse")
        .addMotion("C", 6, 440, 70, 120, 60, 0, 0, 255,
            20, 440, 70, 120, 60, 0, 0, 255)
        .addMotion("C", 20, 440, 70, 120, 60, 0, 0, 255,
            50, 440, 250, 120, 60, 0, 0, 255)
        .addMotion("C", 50, 440, 250, 120, 60, 0, 0, 255,
            70, 440, 370, 120, 60, 0, 170, 85)
        .addMotion("C", 70, 440, 370, 120, 60, 0, 170, 85,
            80, 440, 370, 120, 60, 0, 255, 0)
        .addMotion("C", 80, 440, 370, 120, 60, 0, 255, 0,
            100, 440, 370, 120, 60, 0, 255, 0)
        .build();

    assertEquals(100, model.getEndTick());

    assertEquals(2, model.getShapes().size());
    assertEquals(3, model.getShapes().get(0).getShapeAnimations().size());
    assertEquals(4, model.getShapes().get(1).getShapeAnimations().size());
    assertEquals(7, model.getAnimations().size());
    assertEquals(2, model.getDeclaredShapes().size());

    assertEquals(0, model.getCanvas().getCanvasX());
    assertEquals(0, model.getCanvas().getCanvasY());
    assertEquals(1000, model.getCanvas().getCanvasWidth());
    assertEquals(1000, model.getCanvas().getCanvasHeight());
  }

}
