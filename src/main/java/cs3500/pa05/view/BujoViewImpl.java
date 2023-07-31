package cs3500.pa05.view;

import cs3500.pa05.controller.ActionResponder;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * Represents a concrete view for a bullet journal application.
 */
public class BujoViewImpl implements BujoView {
  private final FXMLLoader loader;

  /**
   * Constructor for a view
   *
   * @param responder a specific instance of the control that deals with action response
   */
  public BujoViewImpl(ActionResponder responder) {
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("final-draft.fxml"));
    this.loader.setController(responder);
  }

  /**
   * Loads this view.
   */
  @Override
  public Scene load() {
    try {
      return this.loader.load();
    } catch (IOException e) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}