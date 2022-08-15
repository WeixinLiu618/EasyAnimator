import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.view.IView;
import cs5004.animator.view.TextView;
import java.util.List;
import org.junit.Test;

/**
 * Tests the class TextView.
 *
 * @author Weixin Liu
 */
public class TextViewTest {

  IView textView;


  /**
   * Tests the constructor.
   */
  @Test
  public void testConstructor() {
    textView = new TextView(20, new StringBuilder());
    assertEquals(20, textView.getTempo());
    assertEquals(0, ((StringBuilder) ((TextView) textView).getOutput()).length());
    assertFalse(textView.isLoopFlag());
  }


  /**
   * Tests the method render().
   */
  @Test
  public void testRender() {

    AnimationModel model = new AnimationModelImpl.Builder()
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
        .build();

    List<IReadOnlyShape> readOnlyShapes = model.getReadOnlyShapes();
    textView = new TextView(20, new StringBuilder());
    textView.render(readOnlyShapes);
    assertEquals("Create rectangle R with corner at (200.0,200.0), "
            + "width 50.0 and height 100.0, color rgb(255,0,0)\n\n"
            + "R appears at time t=50ms and disappears at time t=5000ms\n\n"
            + "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=500ms to t=2500ms\n"
            + "Shape R changes width from 50.0 to 25.0 from time t=2550ms to t=3500ms\n"
            + "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=3500ms to t=5000ms\n",
        ((StringBuilder) ((TextView) textView).getOutput()).toString());

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

    readOnlyShapes = model.getReadOnlyShapes();
    textView = new TextView(10, new StringBuilder());
    textView.render(readOnlyShapes);
    assertEquals(
        "Create rectangle R with corner at (200.0,200.0), "
            + "width 50.0 and height 100.0, color rgb(255,0,0)\n"
            + "Create oval C with center at (440.0,70.0), "
            + "radius 120.0 and 60.0, color rgb(0,0,255)\n\n"
            + "R appears at time t=100ms and disappears at time t=10000ms\n"
            + "C appears at time t=600ms and disappears at time t=10000ms\n\n"
            + "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=1000ms to t=5000ms\n"
            + "Shape C moves from (440.0,70.0) to (440.0,250.0) from t=2000ms to t=5000ms\n"
            + "Shape C moves from (440.0,250.0) to (440.0,370.0) from t=5000ms to t=7000ms\n"
            + "Shape R changes width from 50.0 to 25.0 from time t=5100ms to t=7000ms\n"
            + "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=7000ms to t=10000ms\n",
        ((StringBuilder) ((TextView) textView).getOutput()).toString());
  }
}
