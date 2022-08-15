package cs5004.animator.view;

import cs5004.animator.model.attributes.Canvas;
import cs5004.animator.model.shapes.IReadOnlyShape;
import java.util.List;

/**
 * This interface represents the display for the animation model, and offers several operations for
 * the text view, SVG view, and visual view.
 *
 * @author Weixin Liu
 */
public interface IView {

  /**
   * Generates the output to users.
   *
   * @param shapes the given read only shapes list from the animation model
   */
  void render(List<IReadOnlyShape> shapes);


  /**
   * Sets the output view canvas according to the given canvas.
   *
   * @param c the given canvas with its size and position
   */
  void setViewCanvas(Canvas c);

  /**
   * Sets the tempo of the view of the animation. Tempo is the number of ticks in one second.
   *
   * @param tempo the given tempo
   */
  void setTempo(int tempo);

  /**
   * Returns the tempo of the view of the animation. Tempo is the number of ticks in one second.
   *
   * @return the tempo of the view of the animation
   */
  int getTempo();


  /**
   * Sets the loop flag of this animation view.
   *
   * @param loopFlag the given loop flag, true if loopback, otherwise false
   */
  void setLoopFlag(boolean loopFlag);

  /**
   * Returns the loop flag of this animation view.
   *
   * @return true if loopback, otherwise false
   */
  boolean isLoopFlag();

  /**
   * Adds all the EditorFeatures to the listeners to execute.
   *
   * @param editorFeatures the given editor features
   * @throws UnsupportedOperationException if this view does not support the editor features
   */
  void addFeatures(EditorFeatures editorFeatures) throws UnsupportedOperationException;

  /**
   * Determines is this animation view automatically displaying at beginning.
   *
   * @return true if this animation view automatically display, otherwise false
   */
  boolean isAutoplay();
}
