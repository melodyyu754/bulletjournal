package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.jsons.JsonEvent;
import cs3500.pa05.model.jsons.JsonTask;
import cs3500.pa05.model.jsons.JsonTime;
import cs3500.pa05.model.jsons.JsonWeekday;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeekdayTest {

  Weekday weekday;
  ArrayList<Task> tasks;
  ArrayList<Event> events;
  DayName monday = DayName.MONDAY;
  Task dummyTask = new Task(DayName.MONDAY, "DummyTask", false);
  Task dummyTaskDesp = new Task(DayName.MONDAY, "DummyTask", "Hello", false);
  Time dummyTime = new Time(1, Period.AM);
  Event dummyEvent = new Event(DayName.MONDAY, "Hello", dummyTime, 2);
  JsonTask jsonTask = new JsonTask(DayName.MONDAY, "DummyTask", "", false);
  JsonTime jsonTime = new JsonTime(1, Period.AM);
  JsonEvent jsonEvent = new JsonEvent(DayName.MONDAY, "DummyEvent", "", this.jsonTime, 10);
  ArrayList<JsonEvent> jsonEventsList;
  ArrayList<JsonTask> jsonTasksList;
  JsonWeekday jsonWeekday;

  @BeforeEach
  void setUp() {
    this.tasks = new ArrayList<>();
    this.events = new ArrayList<>();
    this.weekday = new Weekday(this.monday, this.tasks, this.events);
    this.jsonEventsList = new ArrayList<>();
    this.jsonEventsList.add(this.jsonEvent);
    this.jsonTasksList = new ArrayList<>();
    this.jsonTasksList.add(this.jsonTask);
    this.jsonWeekday = new JsonWeekday(this.monday, this.jsonTasksList, this.jsonEventsList);
  }

  @Test
  void getTasks() {
    assertEquals(this.tasks, this.weekday.getTasks());
  }

  @Test
  void getEvents() {
    assertEquals(this.events, this.weekday.getEvents());
  }

  @Test
  void addTask() {
    this.weekday.addTask(this.dummyTask);
    assertEquals(1, this.weekday.getTasks().size());
    assertEquals(this.dummyTask, this.weekday.getTasks().get(0));
  }

  @Test
  void addEvent() {
    this.weekday.addEvent(this.dummyEvent);
    assertEquals(1, this.weekday.getEvents().size());
    assertEquals(this.dummyEvent, this.weekday.getEvents().get(0));
  }

  @Test
  void encode() {
    //First the empty encode
    JsonWeekday actualWeekDay = this.weekday.encode();
    //There are no tasks or events in this weekday therefore the corresponding
    //JSON will look something like this:
    assertEquals(actualWeekDay.day(), actualWeekDay.day());
    assertTrue(actualWeekDay.events().isEmpty());
    assertTrue(actualWeekDay.tasks().isEmpty());
    //Now lets add a corresponding task to the weekday
    this.weekday.addTask(dummyTask);
    //Lets encode it again
    JsonWeekday actualWeekDay1 = this.weekday.encode();
    assertTrue(!actualWeekDay1.tasks().isEmpty());
    //Checking to see if it was added successfully to the JSON
    assertEquals(1, actualWeekDay1.tasks().size());
    assertEquals("[JsonTask[dayName=MONDAY, name=DummyTask, description=, completed=false]]",
        actualWeekDay1.tasks().toString());

    //Lets do the same for event now too
    this.weekday.addEvent(dummyEvent);
    JsonWeekday actualWeekDay2 = this.weekday.encode();
    assertEquals(1, actualWeekDay2.events().size());
    assertEquals("[JsonEvent[dayName=MONDAY, name=Hello, description=, "
            + "start=JsonTime[hour=1, period=AM], duration=2]]",
        actualWeekDay2.events().toString());
  }

  @Test
  void decode() {
    Weekday actual = Weekday.decode(this.jsonWeekday);
    //Confirm all the parts of the week are actually there
    //Only added one event so there should only be one event there in the list
    assertEquals(1, actual.getEvents().size());
    assertEquals("DummyEvent", actual.getEvents().get(0).name);
    assertEquals("", actual.getEvents().get(0).description);
    assertEquals("1 AM", actual.getEvents().get(0).getStart());
    assertEquals("10", actual.getEvents().get(0).getDuration());
  }
}