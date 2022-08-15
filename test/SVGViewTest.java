import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.view.IView;
import cs5004.animator.view.SVGView;
import java.util.List;
import org.junit.Test;

/**
 * Tests the class SVGView.
 *
 * @author Weixin Liu
 */
public class SVGViewTest {

  IView svgView;

  /**
   * Tests the constructor.
   */
  @Test
  public void testConstructor() {
    svgView = new SVGView(50, new StringBuilder());
    assertEquals(50, svgView.getTempo());
    assertEquals(0, ((StringBuilder) ((SVGView) svgView).getOutput()).length());
    assertFalse(svgView.isLoopFlag());
    // Timer will not start
    assertFalse(svgView.isAutoplay());
  }

  /**
   * Tests the method render().
   */
  @Test
  public void testRender() {

    // loopFlag is false
    AnimationModel model = new AnimationModelImpl.Builder()
        .setBounds(200, 70, 360, 360)
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
    svgView = new SVGView(50, new StringBuilder());
    svgView.setViewCanvas(model.getCanvas());
    svgView.render(readOnlyShapes);
    assertEquals("<svg width=\"560\" height=\"430\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n\n"
        + "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"200ms\" dur=\"800ms\" "
        + "attributeName=\"x\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"200ms\" dur=\"800ms\" "
        + "attributeName=\"y\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"1020ms\" dur=\"380ms\" "
        + "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"1400ms\" dur=\"600ms\" "
        + "attributeName=\"x\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"1400ms\" dur=\"600ms\" "
        + "attributeName=\"y\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n"
        + "</rect>\n\n</svg>\n", ((StringBuilder) ((SVGView) svgView).getOutput()).toString());

    // loopFlag is true
    model = new AnimationModelImpl.Builder()
        .setBounds(200, 70, 360, 360)
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
    svgView = new SVGView(50, new StringBuilder());
    svgView.setViewCanvas(model.getCanvas());
    svgView.setLoopFlag(true);
    svgView.render(readOnlyShapes);
    assertEquals("<svg width=\"560\" height=\"430\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n\n"
        + "<rect>\n"
        + "\t<animate id=\"base\" begin=\"0;base.end\" dur=\"4000ms\" "
        + "attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n"
        + "</rect>\n\n"
        + "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" "
        + "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+200ms\" dur=\"800ms\" "
        + "attributeName=\"x\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+200ms\" dur=\"800ms\" "
        + "attributeName=\"y\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+1020ms\" dur=\"380ms\" "
        + "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+1400ms\" dur=\"600ms\" "
        + "attributeName=\"x\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+1400ms\" dur=\"600ms\" "
        + "attributeName=\"y\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"x\" to=\"200.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"y\" to=\"200.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"width\" to=\"50.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"height\" to=\"100.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"fill\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n"
        + "</rect>\n\n"
        + "<ellipse id=\"C\" cx=\"440.0\" cy=\"70.0\" rx=\"120.0\" ry=\"60.0\" "
        + "fill=\"rgb(0,0,255)\" visibility=\"visible\" >\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+400ms\" dur=\"600ms\" "
        + "attributeName=\"cy\" from=\"70.0\" to=\"250.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" dur=\"400ms\" "
        + "attributeName=\"cy\" from=\"250.0\" to=\"370.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" dur=\"400ms\" "
        + "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.begin+1400ms\" dur=\"200ms\" "
        + "attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"cx\" to=\"440.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"cy\" to=\"70.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"rx\" to=\"120.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"ry\" to=\"60.0\" fill=\"freeze\" />\n"
        + "\t<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
        + "attributeName=\"fill\" to=\"rgb(0,0,255)\" fill=\"freeze\" />\n"
        + "</ellipse>\n\n"
        + "</svg>\n", ((StringBuilder) ((SVGView) svgView).getOutput()).toString());
  }


}
