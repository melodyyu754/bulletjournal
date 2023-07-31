package cs3500.pa05.model;

import cs3500.pa05.model.jsons.JsonWeek;
import cs3500.pa05.model.jsons.JsonWeekday;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * represents the information contained by a week in a bullet journal
 */
public class Week {
  private final ArrayList<Weekday> weekdays;
  private int maxTasks;
  private int maxEvents;
  private int totalEvents;
  private int totalTasks;
  private double taskPercentage;

  /**
   * default constructor for a Week - i.e. with no user customization
   * initializes max # of tasks and events to 5
   */
  public Week() {
    this.maxTasks = 3;
    this.maxEvents = 3;
    this.weekdays = new ArrayList<>(Arrays.asList(
        new Weekday(DayName.MONDAY), new Weekday(DayName.TUESDAY), new Weekday(DayName.WEDNESDAY),
        new Weekday(DayName.THURSDAY), new Weekday(DayName.FRIDAY), new Weekday(DayName.SATURDAY),
        new Weekday(DayName.SUNDAY)));
    this.totalTasks = 0;
    this.totalEvents = 0;
    this.taskPercentage = 100;
  }

  /**
   * constructor for a week with known # of tasks and events - i.e.
   * when loading a .bujo file.
   *
   * @param maxTasks  the max number of tasks per day in this week
   * @param maxEvents the max number of events per day in this week
   * @param weekdays  the weekdays of this spread
   */
  public Week(int maxTasks, int maxEvents, ArrayList<Weekday> weekdays) {
    this.maxTasks = maxTasks;
    this.maxEvents = maxEvents;
    this.weekdays = weekdays;
    this.totalTasks = this.getTotalTasks();
    this.totalEvents = this.getTotalEvents();
    this.taskPercentage = 100; //change this later
  }

  /**
   * adds the given task to the given day
   *
   * @param day  the day to add to
   * @param task the task to be added
   */
  public void addTaskToDay(DayName day, Task task) {
    Weekday weekday = weekdays.get(day.ordinal());

    if (weekday.getTasks().size() >= maxTasks) {
      throw new IllegalStateException("Max tasks reached");
    } else {
      weekday.addTask(task);
      System.out.println("Task added.");
    }
  }

  /**
   * adds the given event to the given day
   *
   * @param day   the day to add to
   * @param event the event to be added
   */
  public void addEventToDay(DayName day, Event event) {
    Weekday weekday = weekdays.get(day.ordinal());

    if (weekday.getEvents().size() >= maxEvents) {
      throw new IllegalStateException("Max events reached.");
    } else {
      weekday.addEvent(event);
    }
  }

  /**
   * returns the events of a particular day in this week
   *
   * @param day the day of the week
   * @return the events of the given day
   */
  public ArrayList<Event> getEventsForDay(DayName day) {
    Weekday weekday = weekdays.get(day.ordinal());
    return weekday.getEvents();
  }

  /**
   * returns the tasks of a particular day in this week
   *
   * @param day the day of the week
   * @return the tasks of the given day
   */
  public ArrayList<Task> getTasksForDay(DayName day) {
    Weekday weekday = weekdays.get(day.ordinal());
    return weekday.getTasks();
  }

  /**
   * returns a lists of incomplete tasks for the given day
   *
   * @param day the day in question
   * @return the tasks that are incomplete for the given day
   */
  public ArrayList<Task> getIncompleteTasksForDay(DayName day) {
    Weekday weekday = weekdays.get(day.ordinal());
    ArrayList<Task> incompleteTasks = new ArrayList<>();

    for (Task task : weekday.getTasks()) {
      if (!task.getCompleted()) {
        incompleteTasks.add(task);
      }
    }

    return incompleteTasks;
  }

  /**
   * returns the total events in this week
   *
   * @return the total number of events in this week
   */
  public int getTotalEvents() {
    int totalEvents = 0;
    for (Weekday weekday : weekdays) {
      totalEvents += weekday.getEvents().size();
    }
    return totalEvents;
  }

  /**
   * returns the total tasks in this week
   *
   * @return the total number of tasks in this week
   */
  public int getTotalTasks() {
    int totalTasks = 0;
    for (Weekday weekday : weekdays) {
      totalTasks += weekday.getTasks().size();
    }
    return totalTasks;
  }

  /**
   * returns the number of tasks that have been completed
   *
   * @return the percentage of tasks completed in this week
   */
  public double getTaskPercentage() {
    int tasksCompleted = 0;
    for (Weekday weekday : weekdays) {
      for (Task task : weekday.getTasks()) {
        if (task.getCompleted()) {
          tasksCompleted++;
        }
      }
    }
    return (double) tasksCompleted * 100.0 / (double) this.getTotalTasks();
  }

  /**
   * changes the maximum number of tasks allowed per day for this week
   *
   * @param maxTasks the new maximum number of tasks allowed per day
   */
  public void changeMaxTasks(int maxTasks) {
    this.maxTasks = maxTasks;
  }

  /**
   * changes the maximum number of events allowed per day for this week
   *
   * @param maxEvents the new maximum number of events allowed per day
   */
  public void changeMaxEvents(int maxEvents) {
    this.maxEvents = maxEvents;
  }

  /**
   * encodes this Week into it's JSON equivalent
   *
   * @return the JSON representation of this week
   */
  public JsonWeek encode() {
    ArrayList<JsonWeekday> jsonWeekdays = new ArrayList<>();
    for (Weekday weekday : weekdays) {
      jsonWeekdays.add(weekday.encode());
    }

    return new JsonWeek(jsonWeekdays, maxTasks, maxEvents);
  }

  /**
   * decodes a Week from it's JSON equivalent
   *
   * @param jsonWeek the JSON equivalent of a week
   * @return the Week represented by the given JSON
   */
  public static Week decode(JsonWeek jsonWeek) {
    ArrayList<Weekday> newWeekdays = new ArrayList<>();
    for (JsonWeekday jsonWeekday : jsonWeek.weekdays()) {
      newWeekdays.add(Weekday.decode(jsonWeekday));
    }

    return new Week(jsonWeek.maxTasks(), jsonWeek.maxEvents(), newWeekdays);
  }

  /**
   * adds the given event to the given day
   *
   * @param day      the day to add to
   * @param weekItem the weekItem to be added
   */
  public void addWeekItemToDay(DayName day, WeekItem weekItem) {
    Weekday weekday = weekdays.get(day.ordinal());

    if (weekday.getEvents().size() >= maxEvents) {
      throw new IllegalStateException("Max events reached.");
    } else {
      if (weekItem instanceof Event) {
        weekday.addEvent((Event) weekItem);
      } else {
        weekday.addTask((Task) weekItem);
      }
    }
  }
}
