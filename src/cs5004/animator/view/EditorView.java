package cs5004.animator.view;


import cs5004.animator.model.attributes.Canvas;
import cs5004.animator.model.shapes.IReadOnlyShape;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * This EditorView extends the AbstractView. It is a GUI view, and shows and plays the animation
 * model inside a window. Users can interact with this view by choosing to start, pause, resume,
 * restart, change speed and enable/disable looping. This class takes a visualView as a delegate to
 * render the animation.
 *
 * @author Weixin Liu
 */
public class EditorView extends AbstractView {

  private VisualView visualView;
  private JButton startButton;
  private JButton pauseButton;
  private JButton resumeButton;
  private JButton restartButton;
  private JSpinner tempoSpinner;
  private JCheckBox loopCheckBox;


  /**
   * Creates an EditorView object with extra components to a given visual view.
   *
   * @param visualView the given visual view
   */
  public EditorView(VisualView visualView) {
    super();
    this.visualView = visualView;

    this.tempo = this.visualView.getTempo();

    // bottomPanel contains control buttons
    JPanel bottomPanel;
    bottomPanel = new JPanel(new FlowLayout());

    // start button
    startButton = new JButton("Start");
    startButton.setActionCommand("start");
    bottomPanel.add(startButton);

    // pause button
    pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("pause");
    bottomPanel.add(pauseButton);

    // resume button
    resumeButton = new JButton("Resume");
    resumeButton.setActionCommand("resume");
    bottomPanel.add(resumeButton);

    // restart button
    restartButton = new JButton("Restart");
    restartButton.setActionCommand("restart");
    bottomPanel.add(restartButton);

    // tempo label
    JLabel tempoLabel;
    tempoLabel = new JLabel("Speed:");
    bottomPanel.add(tempoLabel);
    SpinnerModel speedModel = new SpinnerNumberModel(this.getTempo(), 1,
        Integer.MAX_VALUE, 1);

    // tempo spinner
    tempoSpinner = new JSpinner(speedModel);
    bottomPanel.add(tempoSpinner);
    ((JSpinner.DefaultEditor) tempoSpinner.getEditor()).getTextField().setColumns(3);

    // loop check box
    loopCheckBox = new JCheckBox("Loop");
    loopCheckBox.setActionCommand("loop");
    bottomPanel.add(loopCheckBox);

    visualView.add(bottomPanel, BorderLayout.SOUTH);
    pack();

    this.setVisible(false);
  }


  /**
   * Renders all shapes and the corresponding animations of the model and generates the output view
   * by calling the render method of visualView.
   *
   * @param shapes the given read only shapes list from the animation model
   */
  @Override
  public void render(List<IReadOnlyShape> shapes) {
    this.visualView.setVisible(true);
    this.visualView.render(shapes);
  }

  @Override
  public void setViewCanvas(Canvas c) {
    this.visualView.setViewCanvas(c);
  }


  @Override
  public void addFeatures(EditorFeatures editorFeatures) {

    startButton.addActionListener(e -> editorFeatures.start());
    pauseButton.addActionListener(e -> editorFeatures.pause());
    resumeButton.addActionListener(e -> editorFeatures.resume());
    restartButton.addActionListener(e -> editorFeatures.restart());
    tempoSpinner.addChangeListener(
        e -> editorFeatures.changeTempo((Integer) tempoSpinner.getValue()));
    loopCheckBox.addActionListener(e -> editorFeatures.generateLoop(loopCheckBox.isSelected()));

  }
}
