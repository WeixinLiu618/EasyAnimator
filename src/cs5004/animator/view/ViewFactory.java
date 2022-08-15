package cs5004.animator.view;

/**
 * This class represents a factory for making different types of the animator view.
 *
 * @author Weixin Liu
 */
public class ViewFactory {

  /**
   * The static method takes in the given string of the view type and an integer value tempo.
   * According to the different view type, it returns responding kind of view object with the given
   * tempo.
   *
   * @param viewType the given string of the view type
   * @param tempo    the given tempo of a view
   * @param output   the given appendable output of this view
   * @return the IView matches the string of the view type
   * @throws IllegalArgumentException if the input string of view type does not match any type of
   *                                  view
   */
  public static IView createView(String viewType, int tempo, Appendable output)
      throws IllegalArgumentException {
    switch (viewType) {
      case "text":
        return new TextView(tempo, output);
      case "svg":
        return new SVGView(tempo, output);
      case "visual":
        return new VisualView(tempo);
      case "playback":
        return new EditorView(new VisualView(tempo));
      default:
        throw new IllegalArgumentException("Invalid input of view type.");

    }
  }

}
