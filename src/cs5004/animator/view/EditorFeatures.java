package cs5004.animator.view;

/**
 * This interface defines several operations for the animator controller to control the editor view,
 * including start, pause, resume, restart, change tempo, and generate loop.
 *
 * @author Weixin Liu
 */
public interface EditorFeatures {

  /**
   * Starts to play the animation.
   */
  void start();

  /**
   * Pauses the animation.
   */
  void pause();

  /**
   * Resumes to play the animation.
   */
  void resume();

  /**
   * Restarts to play the animations from the very beginning.
   */
  void restart();

  /**
   * Changes the tempo of the animation.
   *
   * @param t the given tempo to set to the animation
   */
  void changeTempo(int t);


  /**
   * Generates looping if the given loopFlag is true. Looping causes the animation to automatically
   * restart once it reaches the end.
   *
   * @param loopFlag the loopFlag to enable/disable looping. True to generate loop, otherwise,not to
   *                 generate loop
   */
  void generateLoop(boolean loopFlag);
}
