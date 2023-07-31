package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.jsons.JsonEvent;
import cs3500.pa05.model.jsons.JsonTask;
import cs3500.pa05.model.jsons.JsonTime;
import cs3500.pa05.model.jsons.JsonWeek;
import cs3500.pa05.model.jsons.JsonWeekday;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeekTest {

  Week week;
  Week week2;
  ArrayList<Weekday> dayList;
  ArrayList<Task> tasks;
  ArrayList<Event> events;

  @BeforeEach
  void setUp() {
    this.week = new Week();
    this.dayList = new ArrayList<>(Arrays.asList(
        new Weekday(DayName.MONDAY), new Weekday(DayName.TUESDAY), new Weekday(DayName.WEDNESDAY),
        new Weekday(DayName.THURSDAY), new Weekday(DayName.FRIDAY), new Weekday(DayName.SATURDAY),
        new Weekday(DayName.SUNDAY)));
    this.tasks = new ArrayList<>();
    this.events = new ArrayList<>();
    this.week2 = new Week(1, 1, this.dayList);
  }

  @Test
  void addTaskToDay() {
    Task one = new Task(DayName.MONDAY, "Hello", true);
    this.week.addTaskToDay(DayName.MONDAY, one);
    //Confirming that the task has been added successfully
    assertEquals(one, week.getTasksForDay(DayName.MONDAY).get(0));
    //Same for the other constructor
    Task two = new Task(DayName.TUESDAY, "Two", true);
    this.week2.addTaskToDay(DayName.TUESDAY, two);
    assertEquals(two, this.week2.getTasksForDay(DayName.TUESDAY).get(0));

    //But lets add another task, this should error for week2 since we specified we only
    //want one maximum task
    assertThrows(IllegalStateException.class, () -> {
      this.week2.addTaskToDay(DayName.TUESDAY, two);
    });
  }

  @Test
  void addEventToDay() {
    Event one = new Event(DayName.TUESDAY, "hello", new Time(1, Period.AM), 2);
    this.week.addEventToDay(DayName.TUESDAY, one);
    //Confirming that the event has been added successfully
    assertEquals(one, this.week.getEventsForDay(DayName.TUESDAY).get(0));
    //Same for the other constructor
    Event two = new Event(DayName.TUESDAY, "Two", new Time(2, Period.PM), 3);
    this.week2.addEventToDay(DayName.TUESDAY, two);
    assertEquals(two, this.week2.getEventsForDay(DayName.TUESDAY).get(0));

    //If we want to add another event, this should throw an exception
    //But lets add another task, this should error for week2 since we specified we only
    //want one maximum task
    assertThrows(IllegalStateException.class, () -> {
      this.week2.addEventToDay(DayName.TUESDAY, two);
    });
  }

  @Test
  void getEventsForDay() {
    Event exampleEvent = new Event(DayName.THURSDAY, "Stone", new Time(3, Period.AM), 2);
    this.week.addEventToDay(DayName.THURSDAY, exampleEvent);
    //Checking if the added event is contained in the week.
    assertTrue(this.week.getEventsForDay(DayName.THURSDAY).contains(exampleEvent));
    //Same for the other constructor
    Event exampleEvent2 = new Event(DayName.MONDAY, "Stone2", new Time(4, Period.PM), 4);
    this.week2.addEventToDay(DayName.MONDAY, exampleEvent2);
    assertEquals(exampleEvent2, this.week2.getEventsForDay(DayName.MONDAY).get(0));
  }

  @Test
  void getTasksForDay() {
    Task one = new Task(DayName.WEDNESDAY, "Stone Liu", true);
    this.week.addTaskToDay(DayName.WEDNESDAY, one);
    //We have successfully added this task to the list
    assertTrue(this.week.getTasksForDay(DayName.WEDNESDAY).contains(one));
    Task two = new Task(DayName.FRIDAY, "OMG OOD", false);
    this.week2.addTaskToDay(DayName.FRIDAY, two);
    assertTrue(this.week2.getTasksForDay(DayName.FRIDAY).contains(two));
  }

  @Test
  void getTotalEvents() {
    Event eventOne = new Event(DayName.FRIDAY, "Blah", new Time(3, Period.PM), 2);
    assertEquals(0, this.week.getTotalEvents());
    this.week.addEventToDay(DayName.FRIDAY, eventOne);
    //The total number of events for the day should be 1
    assertEquals(1, this.week.getTotalEvents());
    //Lets add another one but on a different day
    Event eventTwo = new Event(DayName.FRIDAY, "Blah", new Time(2, Period.AM), 3);
    this.week.addEventToDay(DayName.MONDAY, eventTwo);
    assertEquals(2, this.week.getTotalEvents());
  }

  @Test
  void getTotalTasks() {
    Task taskOne = new Task(DayName.FRIDAY, "Blah", "", true);
    assertEquals(0, this.week.getTotalTasks());
    this.week.addTaskToDay(DayName.FRIDAY, taskOne);
    assertEquals(1, this.week.getTotalTasks());
    Task taskTwo = new Task(DayName.FRIDAY, "HI!", "HI!", false);
    this.week.addTaskToDay(DayName.SATURDAY, taskTwo);
    assertEquals(2, this.week.getTotalTasks());
  }

  @Test
  void getTaskPercentage() {
    Task completedTaskOne = new Task(DayName.FRIDAY, "Blah", true);
    this.week.addTaskToDay(DayName.FRIDAY, completedTaskOne);
    assertEquals(100.0, this.week.getTaskPercentage(), 0.1);
    //But if we add another task that isn't completed
    Task completedTaskTwo = new Task(DayName.WEDNESDAY, "Lazy", false);
    this.week.addTaskToDay(DayName.WEDNESDAY, completedTaskTwo);
    assertEquals(50.0, this.week.getTaskPercentage(), 0.1);
  }

  @Test
  void changeMaxTasks() {
    //Initially we specified we wanted 5 tasks and 5 events
    //assertEquals(5, this.week2.getTasks());
    //Lets change that to two
    this.week2.changeMaxTasks(2);
  }

  @Test
  void changeMaxEvents() {
    this.week2.changeMaxEvents(2);
  }

  @Test
  void encode() {
    assertEquals("JsonWeek[weekdays=[JsonWeekday"
        + "[day=MONDAY, tasks=[], events=[]], JsonWeekday"
        + "[day=TUESDAY, tasks=[], events=[]], JsonWeekday[day=WEDNESDAY, "
        + "tasks=[], events=[]], JsonWeekday[day=THURSDAY, tasks=[], events=[]], "
        + "JsonWeekday[day=FRIDAY, tasks=[], events=[]], JsonWeekday[day=SATURDAY, "
        + "tasks=[], events=[]], JsonWeekday[day=SUNDAY, tasks=[], events=[]]], maxTasks=3, "
        + "maxEvents=3]", this.week.encode().toString());
    assertEquals("JsonWeek[weekdays=[JsonWeekday[day=MONDAY, tasks=[], "
        + "events=[]], JsonWeekday[day=TUESDAY, tasks=[], events=[]], JsonWeekday[day=WEDNESDAY, "
        + "tasks=[], events=[]], JsonWeekday[day=THURSDAY, tasks=[], events=[]], "
        + "JsonWeekday[day=FRIDAY, tasks=[], events=[]], JsonWeekday[day=SATURDAY, tasks=[], "
        + "events=[]], JsonWeekday[day=SUNDAY, "
        + "tasks=[], events=[]]], maxTasks=1, maxEvents=1]", this.week2.encode().toString());
  }

  @Test
  void decode() {
    ArrayList<JsonWeekday> jsonWeekdays = new ArrayList<>();
    ArrayList<JsonTask> jsonTasks = new ArrayList<>();
    jsonTasks.add(new JsonTask(DayName.MONDAY, "blah", "", true));
    ArrayList<JsonEvent> jsonEvents = new ArrayList<>();
    jsonEvents.add(new JsonEvent(DayName.MONDAY, "hello", "hello", new JsonTime(
        1, Period.AM), 2));
    JsonWeekday jsonWeekday = new JsonWeekday(DayName.MONDAY, jsonTasks, jsonEvents);
    jsonWeekdays.add(jsonWeekday);
    JsonWeek exampleWeek = new JsonWeek(jsonWeekdays, 1, 1);
    Week actual = Week.decode(exampleWeek);

    assertEquals(1, actual.getTotalTasks());
    assertEquals(1, actual.getTotalEvents());

    //Check events are in proper order
    assertEquals("hello", actual.getEventsForDay(DayName.MONDAY).get(0).getName());
    assertEquals("1 AM", actual.getEventsForDay(DayName.MONDAY).get(0).getStart());
    assertEquals("2", actual.getEventsForDay(DayName.MONDAY).get(0).getDuration());

    //Check tasks are in proper order
    assertEquals("blah", actual.getTasksForDay(DayName.MONDAY).get(0).getName());
    assertEquals("", actual.getTasksForDay(DayName.MONDAY).get(0).getDescription());
    assertEquals(true, actual.getTasksForDay(DayName.MONDAY).get(0).getCompleted());
  }

  @Test
  void testAddWeekTiDay() {
    Event e = new Event(DayName.MONDAY, "Stone", "", new Time(1, Period.AM), 2);
    Task t1 = new Task(DayName.MONDAY, "", true);
    this.week2.addWeekItemToDay(DayName.MONDAY, e);
    this.week.addWeekItemToDay(DayName.MONDAY, t1);

    assertEquals(e, this.week2.getEventsForDay(DayName.MONDAY).get(0));
    assertEquals(t1, this.week.getTasksForDay(DayName.MONDAY).get(0));

    assertThrows(IllegalStateException.class, () -> {
      this.week2.addWeekItemToDay(DayName.MONDAY, e);
    });

    assertThrows(IllegalStateException.class, () -> {
      this.week2.addWeekItemToDay(DayName.MONDAY, t1);
    });
  }

  @Test
  void testGetIncompleteTasksForDay() {
    Task incompleteTask = new Task(DayName.MONDAY, "Stibe", false);
    Task completeTask = new Task(DayName.MONDAY, "Helo", true);
    this.week.addWeekItemToDay(DayName.MONDAY, incompleteTask);
    this.week.addWeekItemToDay(DayName.MONDAY, completeTask);
    assertEquals(1, this.week.getIncompleteTasksForDay(DayName.MONDAY).size());
    assertEquals(incompleteTask, this.week.getIncompleteTasksForDay(DayName.MONDAY).get(0));
  }
}