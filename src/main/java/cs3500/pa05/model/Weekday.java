package cs3500.pa05.model;

import cs3500.pa05.model.jsons.JsonEvent;
import cs3500.pa05.model.jsons.JsonTask;
import cs3500.pa05.model.jsons.JsonWeekday;
import java.util.ArrayList;

/**
 * represents a weekday in a bullet journal spread and the associated information
 */
public class Weekday {
  private final DayName day;
  private final ArrayList<Task> tasks;
  private final ArrayList<Event> events;

  /**
   * constructor for a default weekday - no tasks or events
   *
   * @param day the day of the week
   */
  public Weekday(DayName day) {
    this.day = day;
    this.tasks = new ArrayList<>();
    this.events = new ArrayList<>();
  }

  /**
   * constructor for a weekday with tasks and events
   *
   * @param day    the day of the week
   * @param tasks  the tasks for this day
   * @param events the events for this day
   */
  public Weekday(DayName day, ArrayList<Task> tasks, ArrayList<Event> events) {
    this.day = day;
    this.tasks = tasks;
    this.events = events;
  }

  /**
   * returns the tasks for this day
   *
   * @return the tasks for this day
   */
  public ArrayList<Task> getTasks() {
    return this.tasks;
  }

  /**
   * returns the events for this day
   *
   * @return the events for this day
   */
  public ArrayList<Event> getEvents() {
    return this.events;
  }

  /**
   * adds the given task to this weekday's tasks
   *
   * @param task the task to be added
   */
  public void addTask(Task task) {
    this.tasks.add(task);
  }

  /**
   * adds the given event to this weekday's events
   *
   * @param event the event to be added
   */
  public void addEvent(Event event) {
    this.events.add(event);
  }

  /**
   * encodes this Weekday into it's JSON equivalent
   *
   * @return the JSON representation of this weekday
   */
  public JsonWeekday encode() {
    ArrayList<JsonTask> jsonTasks = new ArrayList<>();
    for (Task task : tasks) {
      jsonTasks.add(task.encode());
    }

    ArrayList<JsonEvent> jsonEvents = new ArrayList<>();
    for (Event event : events) {
      jsonEvents.add(event.encode());
    }

    return new JsonWeekday(day, jsonTasks, jsonEvents);
  }

  /**
   * decodes a Weekday from it's JSON equivalent
   *
   * @param jsonWeekday the JSON equivalent of a weekday
   * @return the Weekday represented by the given JSON
   */
  public static Weekday decode(JsonWeekday jsonWeekday) {
    ArrayList<Task> newTasks = new ArrayList<>();
    for (JsonTask jsonTask : jsonWeekday.tasks()) {
      newTasks.add(Task.decode(jsonTask));
    }

    ArrayList<Event> newEvents = new ArrayList<>();
    for (JsonEvent jsonEvent : jsonWeekday.events()) {
      newEvents.add(Event.decode(jsonEvent));
    }

    return new Weekday(jsonWeekday.day(), newTasks, newEvents);
  }
}
