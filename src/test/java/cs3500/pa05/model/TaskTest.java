package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.jsons.JsonTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {

  Task task;
  Task task1;

  /**
   * Sets up the Task objects
   */
  @BeforeEach
  void setUp() {
    this.task = new Task(DayName.MONDAY, "Task", true);
    this.task1 = new Task(DayName.TUESDAY, "Task1", "Hello", false);
  }

  /**
   * Tests for getting the Day of the Task object
   */
  @Test
  void getDay() {
    assertEquals(DayName.MONDAY, this.task.getDayName());
    assertEquals(DayName.TUESDAY, this.task1.getDayName());
  }

  /**
   * Tests for getting the name of the task object
   */
  @Test
  void getName() {
    assertEquals("Task", this.task.getName());
    assertEquals("Task1", this.task1.getName());
  }

  /**
   * Tests for getting the description of the task object
   */
  @Test
  void getDescription() {
    assertEquals("", this.task.getDescription());
    assertEquals("Hello", this.task1.getDescription());
  }

  /**
   * Tests for getting completed of the task object
   */
  @Test
  void getCompleted() {
    assertTrue(this.task.getCompleted());
    assertFalse(this.task1.getCompleted());
  }

  /**
   * tests the switch method for the task object for completed
   */
  @Test
  void setCompleted() {
    this.task.setCompleted();
    assertFalse(this.task.getCompleted());
    this.task1.setCompleted();
    assertTrue(this.task1.getCompleted());
  }

  /**
   * Tests if encoding the task into a JsonTask preserves the data and fields of the task object
   */
  @Test
  void encode() {
    JsonTask exTask = new JsonTask(DayName.MONDAY, "Task", "", true);
    JsonTask actualTask = this.task.encode();
    assertEquals(exTask.dayName(), actualTask.dayName());
    assertEquals(exTask.name(), actualTask.name());
    assertEquals(exTask.description(), actualTask.description());
    assertEquals(exTask.completed(), actualTask.completed());

    JsonTask actualTask1 = this.task1.encode();
    JsonTask exTask1 = new JsonTask(DayName.TUESDAY, "Task1", "Hello", false);
    assertEquals(exTask1.dayName(), actualTask1.dayName());
    assertEquals(exTask1.name(), actualTask1.name());
    assertEquals(exTask1.description(), actualTask1.description());
    assertEquals(exTask1.completed(), actualTask1.completed());
  }

  /**
   * Tests if decoding the JsonTask properly decodes into a proper Task class with its
   * fields and data from the JsonTask preserved
   */
  @Test
  void decode() {
    JsonTask exTask = new JsonTask(DayName.MONDAY, "Task", "", true);
    Task actual = Task.decode(exTask);
    assertEquals(DayName.MONDAY, actual.getDayName());
    assertEquals("Task", actual.getName());
    assertEquals("", actual.getDescription());
    assertTrue(actual.getCompleted());

    JsonTask exTask1 = new JsonTask(DayName.TUESDAY, "Task1",  "Hello", false);
    Task actual1 = Task.decode(exTask1);
    assertEquals(DayName.TUESDAY, actual1.getDayName());
    assertEquals("Task1", actual1.getName());
    assertEquals("Hello", actual1.getDescription());
    assertFalse(actual1.getCompleted());
  }
}