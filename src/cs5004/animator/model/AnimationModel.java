package cs5004.animator.model;

import cs5004.animator.model.animations.IAnimation;
import cs5004.animator.model.attributes.Canvas;
import cs5004.animator.model.shapes.IReadOnlyShape;
import cs5004.animator.model.shapes.IShape;
import java.util.HashMap;
import java.util.List;


/**
 * This interface contains several operations for the animation model. And the model will implement
 * all the functionality of this interface, including get shapes, get animations, get canvas, add
 * shape, remove shape, add animation, get end tick, get ReadOnly shapes, get tweening Shapes at
 * some ticks, find shape with the give name, and get the map of the declared shapes.
 *
 * @author Weixin Liu
 */
public interface AnimationModel {


  /**
   * Returns the list of shapes of the animation model.
   *
   * @return the list of shapes in the animation
   */
  List<IShape> getShapes();

  /**
   * Returns the list of animations of this animation model.
   *
   * @return the list of animations of this animation model
   */
  List<IAnimation> getAnimations();

  /**
   * Returns the canvas of this animation model.
   *
   * @return the canvas of this animation model
   */
  Canvas getCanvas();


  /**
   * Returns the HashMap of the declared shapes where the key is string of the shape name, and value
   * is the string of the declared shape type.
   *
   * @return the HashMap of the declared shapes
   */
  HashMap<String, String> getDeclaredShapes();

  /**
   * Adds a 2D shape to the animation model.
   *
   * @param shape the shape that is to be added
   * @throws IllegalArgumentException if the shape with the same name has already existed in the
   *                                  shape list
   */
  void addShape(IShape shape) throws IllegalArgumentException;

  /**
   * Removes the shape from the animation model. If the given shape name does not exist in the
   * model, the model remains unchanged.
   *
   * @param shapeName the shape of that name to be removed from the model
   */
  void removeShape(String shapeName);

  /**
   * Adds an animation to this animation model.
   *
   * @param animation the animation to be added
   * @throws IllegalArgumentException if the given animation is overlapped with some existing
   *                                  animation of the shape with the same shapeName
   */
  void addAnimation(IAnimation animation) throws IllegalArgumentException;


  /**
   * Returns the end tick of the animation model, by checking for the last disappear tick of the
   * shapes in the model.
   *
   * @return the end tick of this animation model
   */
  int getEndTick();

  /**
   * Returns the list of shapes in the model in a form of IReadOnlyShape.
   *
   * @return the list of shapes in the model in a form of IReadOnlyShape.
   */
  List<IReadOnlyShape> getReadOnlyShapes();


  /**
   * Returns a new list of shapes with animated in-between attributes at tick t of this model.
   *
   * @param t a given in-between tick
   * @return a list of shapes with animated attributes at that given tick of this model.
   */
  List<IReadOnlyShape> getTweeningShapes(int t);


  /**
   * Returns the shape with the given name in the model, if there is not a shape with that name,
   * returns null.
   *
   * @param name find the shape with that given name
   * @return the shape if existing, otherwise null
   */
  IShape findShape(String name);

}
