package cs3500.pa05.model;

import cs3500.pa05.model.jsons.JsonTask;

/**
 * Represents a Task in a Week
 */
public class Task extends WeekItem {
  private boolean completed;

  /**
   * Constructor for a Task with a description
   *
   * @param dayName the day that this task occurs
   * @param name the name of the task
   * @param description the description of the task
   * @param completed whether the task is completed
   */
  public Task(DayName dayName, String name, String description, boolean completed) {
    super(dayName, name, description);
    this.completed = completed;
  }

  /**
   * Constructor for a Task with no description
   *
   * @param dayName the day that this task occurs
   * @param name the name of the Task
   * @param completed whether the task is completed
   */
  public Task(DayName dayName, String name, boolean completed) {
    super(dayName, name);
    this.completed = completed;
  }

  /**
   * returns the completed status of this task
   *
   * @return whether this task has been completed
   */
  public boolean getCompleted() {
    return this.completed;
  }

  /**
   * Changes the completedValue of the event to the opposite boolean
   *
   */
  public void setCompleted() {
    this.completed = !completed;
  }

  /**
   * encodes this task into it's JSON equivalent
   *
   * @return the JSON representation of this task
   */
  public JsonTask encode() {
    return new JsonTask(dayName, name, description, completed);
  }

  /**
   * decodes a task from it's JSON equivalent
   *
   * @param jsonTask the JSON equivalent of a task
   * @return the Task represented by the given JSON
   */
  public static Task decode(JsonTask jsonTask) {
    return new Task(jsonTask.dayName(), jsonTask.name(), jsonTask.description(),
        jsonTask.completed());
  }
}