## The Easy Animator Application

This project provides an easy animator which converts the input text into an animation and allow users to view this animation in several ways. For example, users can choose to display the animation directly on his/her screen, interactively play it, changing the speed. Or they can export the animation as pure descriptions on a time line, or even export the animation as a SVG file.

This application is built using the Model-View-Controller architecture. Each part has been structured into separate packages and sub-packages.  In this document, each package’s class and the implemented methods are explained. The packages and sub-packages are:
* model
  * animations
  * attributes
  * shapes
  * util
* view
* controller

---
### The Model part
All the classes to build the animation model are included in the model package. In the model package, there are four sub-packages.

Package attributes contains 3 classes as compositions for a shape: `Position`, `Size` and `RGBColor`. And a class as attribute for a model: `Canvas`.

Package shape including `IShape` interface which has some methods supported by the concrete shape in the animation model.  An abstract class `AbstractShape` implements it and offers most of the operations mandated by the `Ishape` interface. Utilized an enum `ShapeType` to represent different types of 2D shapes. And the concrete shape classes includes `Oval` and `Rectangle` and extend the `AbstractShape`.
Optimization: to prevent mutation of the model’s data, made the `IShape` interface extends the `IReadOnlyShape` interface, and moved all the get methods to the `IReadOnlyShape` interface. And used the `IReadOnlyShape` interface to extract shape data of the animation model.
A shape would be constructed with the following attributes:
* `String name`, as a unique identifier to distinguish each shape;
* `ShapeType shapeType`, the shape type of the shape, RECTANGLE, OVAL;
* `Position position`, the postion of the shape;
* `RGBColor color`, the RGB color of the shape;
* `Size size`, the two dimension size of the shape;
* `int appearTick`, the appear tick of the shape;
* `int disappearTick`, the disappear tick of the shape;
* `List<IAnimation> animationList`, the list of animations the shape owns;
  

  A shape has the following methods:
* `String getName()`, return the name of this shape;
* `ShapeType getShapeType()`, returns the type of this shape;
* `Position getPosition()`, returns the coordinate position (x, y) of this shape;
* `Size getSize()`, returns the size with two dimensions of this shape;
* `RGBColor getColor()`, returns the RGB color of this shape;
* `int getAppearTick()`, returns the appear tick of this shape;
* `int getDisappearTick()`, returns the disappear tick of this shape;
* `List<IAnimation> getShapeAnimations()`, returns the list of animations on this shape;
* `void setPosition(Position position)`, sets the new position of this shape;
* `void setColor(RGBColor color)`, sets a new color of this shape;
* `void setSize(Size size)`, sets the new size of this shape;
* `void setDisappearTick(int disappearTick)`, sets the disappear tick of this shape;
* `void addShapeAnimation(IAnimation animation)`, adds an animation to this shape;
* `IReadOnlyShape getShapeAtTick(int t)`, return a new animated shape object at a given tick;
 
Package animations consists of `IAnimation` interface that represents the methods supported by the animation model. `IAnimation` interface extends the `Comparable` interface for the convenience of sorting the animations by their start ticks.
An abstract class `AbstractAnimation` implements it and offers most of the operations mandated by the `IAnimation` interface. Utilized an enum `AnimationType` to represent different types of animations. And the concrete shape classes `Moving`, `Scaling` and `ChangingColor` extend the `AbstractAnimation`. An animation and a shape are connected by the unique shape name. And an animation can only add on the shape with the same shape name.
 
An animation has these general fields:
* `String shapeName`, the shape name this animation is added on;
* `AnimationType animationType`, the type of this animation, MOVE, SCALE or COLOR;
* `int startTick`, the start tick of this animation;
* `int endTick`, the end tick of this animation;
 
The concrete classes have their own specific field:
* `Moving`:
  * `Position fromPosition`, the start position of this moving animation;
  *	`Position toPosition`, the destination position of this moving animation;
* `Scaling`:
  *	`Size oldSize`, the old size of this scaling animation;
  *	`Size newSize`, the new size of this scaling animation;
* `ChangingColor`:
  * `RGBColor oldColor`, the old color of this changing color animation;
  * `RGBColor newColor`, the new color of this changing color animation;
 
An animation has the following methods:
* `int getStartTick()`, returns the start tick of this animation;
* `int getEndTick()`, returns the end tick of this animation;
* `AnimationType getAnimationType()`, returns the type of the animation;
* `String getShapeName()`, returns the shape name this animation is added on;
* `boolean isOverlap(IAnimation a)`, checks if this animation overlaps with the given animation;
* `int compareTo(IAnimation o)`, compares this animation with the given one based on their start 
     ticks;
 
Each concrete classes also have their own getters and get in-between state method,
* `Moving`:
  * `Position getFromPosition()`, returns the position from which starting moving; 
  * `Position getToPosition()`, returns the position to move to;
  * `Position getTweeningAnimatedPosition(int t)`, returns the position at the intermediate tick;
* `Scaling`:
  * `Size getOldSize()`, returns the old size of this scaling animation;
  *	`Size getNewSize()`, returns the new size of this scaling animation;
  *	`Size getTweeningAnimatedSize(int t)`, returns the size at the intermediate tick;
* `ChangingColor`:
  *	`RGBColor getOldColor()`, returns the old color of this changing color animation;
  *	`RGBColor getNewColor()`, returns the new color of this changing color animation;
  *	`RGBColor getTweeningAnimatedColor(int t)`, returns the color at the intermediate tick;
 

Package util consists of utility class `AnimationReader` and interface `AnimationBuilder` to support the project. `AnimationReader` is responsible for reading the input animation files and construct animation data from it through the main method. `AnimationBuilder` offers some methods to build the animation. 

 
The model package includes the `AnimationModel` interface which contains several operations for the animation model. And the `AnimationModelImpl` will implement all the functionalities of this interface. The Impl class has four fields:
* `List<IShape> shapes`, the list of shapes of the animation model;
* `List<IAnimation> animations`, the list of animations of this animation model;
* `Canvas canvas`, the canvas of this animation model;
* `HashMap<String, String> declaredShapes`, the declared shape name and its mapping type string;
 
The animation model has the getter methods and some other functionalities including:
* `List<IShape> getShapes()`, returns the list of shapes of the animation model;
* `List<IAnimation> getAnimations()`, returns the list of animations of this animation model;
* `Canvas getCanvas()`, returns the canvas of this animation model;
* `HashMap<String, String> getDeclaredShapes()`, returns the HashMap of the declared shapes 
     where the key is string of the shape name, and value is the string of the declared shape type;
* `void addShape(IShape shape)`, adds a shape to this animation model;
* `void removeShape(String shapeName)`, removes the shape from the animation model. If the given 
     shape name does not exist in the model, the model remains unchanged;
* `void addAnimation(IAnimation animation)`, adds an animation to this animation model. The 
     animation will be added to the animation list of this model, it will also be added to the corresponding shape;
* `int getEndTick()`, returns the end tick of the animation model, by checking for the last 
     disappear tick of the shapes in the model;
* `List<IReadOnlyShape> getReadOnlyShapes()`, returns the list of shapes in the model in a form 
     of IReadOnlyShape.;
* `List<IReadOnlyShape> getTweeningShapes(int t)`, returns a new list of shapes with animated 
     in-between attributes at tick t of this model;
* `IShape findShape(String name)`, returns the shape with the given name in the model, if there 
     is not a shape with that name, returns null;
 
The `AnimationModelImpl `class includes an inner static class Builder which implements the `AnimationBuilder` interface and is compatible with the animation model. It is used to build an animation model object.
 
#####Some declarations on the concept of time:

In the animation model, in order to prevent confusion of the concept of time, I treated all the t as tick, somewhat like the distance in time dimension. And define the tempo of the animation as the number of ticks per second. It would be more straightforward to use mathematical formulas as following:

>tempo * time = tick, we can calculate the time in second,  
> time = tick / tempo,

which is in second, and multiply 1000, we can get the time in millisecond.

----
### The View part
There are 4 view classes for the Easy Animator application.
* `TextView`, represents a text description of the animation, and renders the animation model 
     into the string output with some format.
* `SVGView`, represents an SVG description of the animation and renders the animation model into 
     the string output with the SVG format.
* `VisualView`, a GUI view, and directly and automatically shows and plays the animation to the 
     users inside a window.
* `EditorView`, based on the VisualView, users can interact with this view by choosing to start, 
     pause, resume, restart, change speed and enable/disable looping.

Since the four types of the views have similar functions like rendering the data in a model, set and get tempo, set canvas, etc. The interface `IView` was created to offer these functions.  And all have some similar fields like canvas, tempo, and loopFlag, I used an abstract class `AbstractView` implements the `IView` interface. Since some views are GUIs, I made the `AbstractView` extends `JFrame`. The `AbstractView` is more like an adapter, all four view classes extend the `AbstractView`, but individual views can suppress some of the methods.
The main method of `IView`, `render(List<IReadOnlyShape> shapes)`, takes in a list of `IReadOnlyShapes` because the shapes in the list have all the necessary data from the model. And the `IReadOnlyShapes` only have the get methods, views will not have the access to mutate the model.

`TextView` and `SVGView` are similar, they both have an extra field `Appendable output`. The method `render(List<IReadOnlyShape> shapes)` will take in a list of `IReadOnlyShapes` and use a StringBuilder to get the text string in any format we want. And finally, the output would append this StringBuilder. Since output is an `Appendable`, in theory it should support various classes that implement the `Appendable` interface. And if the class of the output also implements `Closeable`, like `FileWriter`, it needs to close after appending the whole StringBuilder.

For `VisualView`, I used another class `ViewPanel` that extends `JPanel` as a field. The `ViewPanel` did all the graphical work and the draw method of it is to draw the shapes according to the list of the `IReadOnlyShape`.
Therefore, in the render method of `VisualView`, it just needed to set visible as true and call the draw method from `ViewPanel` to draw all the shapes. In addition, the bounds of the viewPanel of VisualView are reset according to the canvas.

For the `EditorView`, instead of extending the visual view class, I took the `VisualView` as a delegation as a field of the `EditorView`. In this way, I could directly add another `JPanel bottomPanel` with all buttons we need to that `VisualView`. And in render method it just needed to set visible as true and call the visualView’s render method. Also, the setting bounds can also call the visualView’ `setViewCanvas` method. `EditorView` has some special features like start, pause and so on. The buttons or other components need to add ActionListener to listen to the change on them and make specific response to the changes. 

The program encapsulated each of the above features as a callback function, in a common interface `EditorFeatures`. I used a `addFeatures(EditorFeatures editorFeatures)` method to implement all the addListeners(..) and connect corresponding method callback to the event of the button.

The `ViewFactory` class is designed to create the specific type of view that should be generated by choosing the corresponding view based on the configuration from main.

----
### The Controller part

In `IController` interface, there is only one method `run()`. It gives control to the controller, runs the animation model, and tells the view what and how to display according to the view the user chooses. The `ControllerImpl` class implemented the `IController`. The `ControllerImpl` has four fields:
`AnimationModel model`, `IView view`, `int tick`, `Timer timer`.
The `timer` is created with the delay of 1000 / tempo (milliseconds for the initial and between-event delay) and a listener of this timer.

Since the controller should get control in response to any event, the `ControllerImpl` implemented the `EditorFeatures` as well.  The view of the controller is provided with all the callbacks by `this.view.addFeatures(this)`.

----

### Run Configuration

In order to run the program, the user need to download the resource folder and unzip it ,then 
open the terminal and cd to that folder with the `EasyAnimator.jar` file.
Type out the following commands and then add the arguments to read files, choose view type, 
set speeds and specify the output file. 
 

For example, if a user wants to use the buildings.txt as the input file for the animation, and 
want to get a SVG output, with the speed of 30, and finally get an output file with name buildingsOut.svg, he/she can use the following command:
>java -jar EasyAnimator.jar -in inputs/buildings.txt -view svg -speed 30 -out buildingsOut.svg

the configurations should be in pairs, the four pairs are:
* -in inputfileName
* -view viewtype
* -speed positiveInteger
* -out outputfileName

Among them, the -in with input file name/path and the -view with view type are necessary, otherwise, an error message dialog will pop up.
View type can only be specified as one of “text”, “svg”, “visual”, “playback”, double quotes are not needed.

If -speed pair is not specified, the speed will be the default value 1.

If -out pair is not specified when the view type is text or svg, the user will get the `System.out` by default.

Configuration pair cannot be repeated. If type the repeated or other invalid configurations, an error message dialog will also pop up. 


