package cs5004.animator.view;

import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.model.shapes.ShapeType;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

/**
 * This ViewPanel class extends JPanel, and is responsible for doing the graphical work. It draws
 * the shapes at each tick from the animation model.
 *
 * @author Weixin Liu
 */
public class ViewPanel extends JPanel {

  private List<IReadOnlyShape> shapes = null;

  /**
   * Creates a ViewPanel object, and set the background to white.
   */
  public ViewPanel() {
    super();
    this.setBackground(Color.WHITE);
  }

  /**
   * According to the lists of shapes from the animation model, draws corresponding same-attributes
   * 2D graphics on the panel.
   *
   * @param graphics the Graphics object to protect
   */
  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    if (shapes != null) {
      Graphics2D graphics2D = (Graphics2D) graphics;
      for (IReadOnlyShape shape : this.shapes) {

        int r = (int) Math.round(shape.getColor().getRed());
        int g = (int) Math.round(shape.getColor().getGreen());
        int b = (int) Math.round(shape.getColor().getBlue());
        int positionX = (int) Math.round(shape.getPosition().getX());
        int positionY = (int) Math.round(shape.getPosition().getY());
        int xLength = (int) Math.round(shape.getSize().getXLength());
        int yLength = (int) Math.round(shape.getSize().getYLength());
        graphics2D.setColor(new Color(r, g, b));
        if (shape.getShapeType() == ShapeType.RECTANGLE) {
          graphics2D.fillRect(positionX, positionY, xLength, yLength);
        } else if (shape.getShapeType() == ShapeType.OVAL) {
          graphics2D.fillOval(positionX, positionY, xLength, yLength);
        }
      }
    }
  }

  /**
   * Draws the given shapes on the panel.
   *
   * @param shapes the list of the ReadOnly shapes from the animation model
   */
  public void draw(List<IReadOnlyShape> shapes) {
    this.shapes = shapes;
    repaint();
  }
}
