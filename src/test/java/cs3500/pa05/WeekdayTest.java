package cs3500.pa05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.DayName;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for WeekdayTest
 */
public class WeekdayTest {
  Weekday weekday;
  DayName monday = DayName.MONDAY;
  ArrayList<Task> tasks;
  ArrayList<Event> events;
  Task dummyTask;
  Task dummyTaskDes;

  @BeforeEach
  void initData() {
    this.tasks = new ArrayList<>();
    this.events = new ArrayList<>();
    this.weekday = new Weekday(this.monday, this.tasks, this.events);
  }

  @Test
  void testGetTask() {
    assertEquals(this.tasks, this.weekday.getTasks());
  }

  @Test
  void testGetEvents() {
    assertEquals(this.events, this.weekday.getEvents());
  }

  @Test
  void addTask() {
    this.weekday.addTask(this.dummyTask);
    assertEquals(1, this.weekday.getTasks().size());
    assertEquals(this.dummyTask, this.weekday.getTasks().get(0));
  }
}
