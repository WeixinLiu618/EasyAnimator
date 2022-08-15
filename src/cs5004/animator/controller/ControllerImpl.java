package cs5004.animator.controller;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.view.EditorFeatures;
import cs5004.animator.view.IView;
import java.util.List;
import javax.swing.Timer;

/**
 * This class represents a controller that links with the animation model and tells the view what to
 * display according to the user's choice. It implements the interface IController and
 * EditorFeatures.
 *
 * @author Weixin Liu
 */
public class ControllerImpl implements IController, EditorFeatures {

  private AnimationModel model;
  private IView view;
  private int tick;
  private Timer timer;

  /**
   * Creates a controller with the given animation model and the view, and initializes the tick from
   * 0, and the Timer.
   *
   * @param model the given model linked with this controller
   * @param view  the given view linked with this controller
   */
  public ControllerImpl(AnimationModel model, IView view) {
    this.model = model;
    this.view = view;
    // tick starts from 0
    this.tick = 0;

    this.timer = new Timer(1000 / this.view.getTempo(), e -> {
      List<IReadOnlyShape> shapesToRender = model.getTweeningShapes(tick++);
      view.render(shapesToRender);
      if (view.isLoopFlag() && tick > model.getEndTick()) {
        tick = 0;
      }
    });

  }


  @Override
  public void run() {

    this.view.setViewCanvas(this.model.getCanvas());

    try {
      this.view.addFeatures(this);
    } catch (UnsupportedOperationException e) {
      // do nothing
    }

    this.view.render(this.model.getReadOnlyShapes());

    if (view.isAutoplay()) {
      this.timer.start();
    }
  }

  @Override
  public void start() {
    if (tick == 0) {
      timer.start();
    }
    if (tick > model.getEndTick()) {
      tick = 0;
    }
  }

  @Override
  public void pause() {
    this.timer.stop();
  }

  @Override
  public void resume() {
    if (this.tick > 0) {
      timer.start();
    }

  }

  @Override
  public void restart() {
    tick = 0;
    timer.start();
  }

  @Override
  public void changeTempo(int t) {
    this.view.setTempo(t);
    this.timer.setDelay(1000 / this.view.getTempo());
  }

  @Override
  public void generateLoop(boolean loopFlag) {
    this.view.setLoopFlag(loopFlag);
    if (this.view.isLoopFlag() && tick > model.getEndTick()) {
      timer.stop();
      tick = 0;
    }
  }
}
