package cs5004.animator;

import cs5004.animator.controller.ControllerImpl;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.util.AnimationReader;
import cs5004.animator.view.IView;
import cs5004.animator.view.ViewFactory;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.JOptionPane;

/**
 * This is the main class that enables the animator to run in different ways based on the user's
 * input.
 *
 * @author Weixin Liu
 */
public final class EasyAnimator {


  /**
   * The main method to run the Easy Animator program based on the command lines the user inputs.
   *
   * @param args command lines that the user inputs in, to decide the animator run in which way.
   */
  public static void main(String[] args) throws IOException {

    String viewType = null;
    String inputFilePath = null;
    String outputFilePath = null;
    int tempo = 1;

    if (Arrays.stream(args).noneMatch("-in"::equals)) {
      popupError("Error: -in was not specified");
    }
    if (Arrays.stream(args).noneMatch("-view"::equals)) {
      popupError("Error: -view was not specified");
    }
    HashSet<String> argsSet = new HashSet<>();

    for (int i = 0; i < args.length; i++) {

      // read -in
      if ("-in".equals(args[i])) {
        if (argsSet.contains("-in")) {
          popupError("Error: Arguments repeated");
        }
        argsSet.add("-in");
        try {
          inputFilePath = args[i + 1];
        } catch (IndexOutOfBoundsException e1) {
          popupError("Error: file not found.");
        }
      }

      // read -view
      if ("-view".equals(args[i])) {
        if (argsSet.contains("-view")) {
          popupError("Error: Arguments repeated");
        }
        argsSet.add("-view");
        try {
          viewType = args[i + 1];
        } catch (IndexOutOfBoundsException e) {
          popupError("Error: view type not found.");
        }
      }

      // read -out
      if ("-out".equals(args[i])) {
        if (argsSet.contains("-out")) {
          popupError("Error: Arguments repeated");
        }
        argsSet.add("-out");
        try {
          outputFilePath = args[i + 1];
        } catch (IndexOutOfBoundsException e) {
          popupError("Error: file not found or could not be created.");
        }
      }

      // read speed
      if ("-speed".equals(args[i])) {
        if (argsSet.contains("-speed")) {
          popupError("Error: Arguments repeated");
        }
        argsSet.add("-speed");
        try {
          tempo = Integer.parseInt(args[i + 1]);
        } catch (IndexOutOfBoundsException | NumberFormatException e1) {
          popupError("Error: invalid speed specified");
        }
        if (tempo <= 0) {
          popupError("Error: invalid speed specified");
        }
      }

    }

    // read input file and build the animation model
    AnimationModel model = null;
    if (inputFilePath != null) {
      try {
        FileReader file = new FileReader(inputFilePath);
        model = AnimationReader.parseFile(file, new AnimationModelImpl.Builder());
      } catch (FileNotFoundException e1) {
        popupError("Error: file not found");
      } catch (RuntimeException e2) {
        popupError("Error: Invalid input file.");
      }
    } else {
      popupError("Error: file not found.");
    }

    // declare and initiate the output
    Appendable output = System.out;
    if (outputFilePath != null) {
      output = new FileWriter(outputFilePath);
    }

    // create view
    IView view = null;
    if (viewType != null) {
      try {
        view = ViewFactory.createView(viewType, tempo, output);
      } catch (IllegalArgumentException e) {
        popupError("Error: view type not found.");
      }
    }

    // control to run the animation model
    if (model != null && view != null) {
      ControllerImpl controller = new ControllerImpl(model, view);
      controller.run();
    }
  }

  /**
   * Pops up error message using the JOptionPane when the main cannot go on well, and exit the
   * program.
   *
   * @param errorMessage the error message to show on the pane
   */
  private static void popupError(String errorMessage) {
    JOptionPane.showMessageDialog(null, errorMessage, "Error running the Animation",
        JOptionPane.ERROR_MESSAGE);
    System.exit(1);
  }
}



