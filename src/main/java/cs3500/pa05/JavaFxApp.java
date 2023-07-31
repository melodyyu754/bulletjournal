package cs3500.pa05;

import cs3500.pa05.controller.BujoControllerImpl;
import cs3500.pa05.model.Spread;
import cs3500.pa05.view.BujoView;
import cs3500.pa05.view.BujoViewImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The class that extends Application to run our bullet journal app.
 */
public class JavaFxApp extends Application {
  @Override
  public void start(Stage primaryStage) {
    Spread spread = new Spread();
    BujoControllerImpl controller = new BujoControllerImpl(spread, primaryStage);
    BujoView view = new BujoViewImpl(controller);

    try {
      primaryStage.setScene(view.load());
      primaryStage.show();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }

    controller.run();
    controller.updateDisplay(spread.getThemeAsInt());
  }
}