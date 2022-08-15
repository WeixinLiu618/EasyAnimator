package cs5004.animator.view;

import cs5004.animator.model.attributes.Canvas;
import javax.swing.JFrame;

/**
 * This class represents an abstract view which implements the IView interface and extends JFrame.
 *
 * @author Weixin Liu
 */
public abstract class AbstractView extends JFrame implements IView {


  protected Canvas canvas;
  protected int tempo;
  protected boolean loopFlag;


  /**
   * Defines a constructor of the AbstractView, and initializes the title, the tempo and the loop
   * flag.
   */
  public AbstractView() {
    super("Easy Animator (CS5004__WeixinLiu)");
    this.tempo = 1;
    this.loopFlag = false;
  }


  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public void setLoopFlag(boolean loopFlag) {
    this.loopFlag = loopFlag;
  }

  @Override
  public boolean isLoopFlag() {
    return this.loopFlag;
  }

  @Override
  public boolean isAutoplay() {
    return false;
  }

  @Override
  public void setViewCanvas(Canvas c) {
    this.canvas = c;
  }
}
