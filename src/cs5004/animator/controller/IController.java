package cs5004.animator.controller;

/**
 * This is the interface of animation controller for the animation model.
 *
 * @author Weixin Liu
 */
public interface IController {

  /**
   * Give control to the controller, runs the animation model, and tells the view what and how to
   * display according to adaptation.
   */
  void run();

}
