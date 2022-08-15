package cs5004.animator.view;

import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.model.attributes.Canvas;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * This VisualView class extends the AbstractView. It is a GUI view, and directly and automatically
 * shows and plays the animation model inside a window.
 *
 * @author Weixin Liu
 */
public class VisualView extends AbstractView {

  private ViewPanel viewPanel;
  private JScrollPane mainScrollPane;


  /**
   * Creates a VisualView object with the given tempo of the animation. And Sets this visual view
   * with an initial bound, view panel and main scroll pane.
   *
   * @param tempo the given tempo of the animation
   */
  public VisualView(int tempo) {
    super();
    this.tempo = tempo;
    this.setBounds(0, 0, 1000, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.viewPanel = new ViewPanel();
    this.mainScrollPane = new JScrollPane(this.viewPanel);
    this.add(mainScrollPane);

  }

  @Override
  public void setViewCanvas(Canvas c) {
    super.setViewCanvas(c);
    this.viewPanel.setBounds(c.getCanvasX(), c.getCanvasY(), c.getCanvasWidth(),
        c.getCanvasHeight());
    this.viewPanel.setPreferredSize(new Dimension(c.getCanvasWidth(), c.getCanvasHeight()));
    this.mainScrollPane.setPreferredSize(new Dimension(c.getCanvasWidth(), c.getCanvasHeight()));
  }

  /**
   * Renders all shapes and the corresponding animations of the model and generates the visual view
   * by calling the draw method of the view panel.
   *
   * @param shapes the given read only shapes list from the animation model
   */
  @Override
  public void render(List<IReadOnlyShape> shapes) {
    this.setVisible(true);
    this.viewPanel.draw(shapes);
  }

  /**
   * Set Autoplay as true.
   *
   * @return true
   */
  @Override
  public boolean isAutoplay() {
    return true;
  }

  @Override
  public void addFeatures(EditorFeatures editorFeatures) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("The given view doesn't support the editor features");
  }
}
