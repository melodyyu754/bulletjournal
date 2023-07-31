package cs3500.pa05.controller;

/**
 * Represents a control that responds to different user interactions.
 */
public interface ActionResponder {
  /**
   * Responds to the given action by delegating to a helper method.
   *
   * @param a the action that must be responded to
   */
  void respond(Action a);
}
