package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.jsons.JsonEvent;
import cs3500.pa05.model.jsons.JsonTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

  Event event;
  Time time;
  Event event1;
  Time time1;

  /**
   * Sets up the event and time objects
   */
  @BeforeEach
  void setUp() {
    this.time = new Time(3, Period.AM);
    this.event = new Event(DayName.SATURDAY, "StoneEvent", this.time, 1);
    this.time1 = new Time(4, Period.PM);
    this.event1 = new Event(DayName.SUNDAY, "Event", "Hello", this.time1, 2);
  }

  /**
   * Tests for getting the day name of the event
   */
  @Test
  void getDayName() {
    assertEquals(DayName.SATURDAY, this.event.getDayName());
    assertEquals(DayName.SUNDAY, this.event1.getDayName());
  }

  /**
   * Tests for getting the name of the event
   */
  @Test
  void getName() {
    assertEquals("StoneEvent", this.event.getName());
    assertEquals("Event", this.event1.getName());
  }

  /**
   * Tests getting the description of the event
   */
  @Test
  void getDescription() {
    assertEquals("", this.event.getDescription());
    assertEquals("Hello", this.event1.getDescription());
  }

  /**
   * Tests for geting the start time of the event
   */
  @Test
  void getStart() {
    assertEquals("3 AM", this.event.getStart());
    assertEquals("4 PM", this.event1.getStart());
  }

  /**
   * Tests for getting the duration of the event
   */
  @Test
  void getDuration() {
    assertEquals("1", this.event.getDuration());
    assertEquals("2", this.event1.getDuration());
  }

  /**
   * Tests for encoding an event into a JsonEvent
   */
  @Test
  void encode() {
    JsonTime time = new JsonTime(3, Period.AM);
    JsonEvent expected = new JsonEvent(DayName.SATURDAY, "StoneEvent",  "", time,
        1);
    JsonEvent actual = this.event.encode();
    assertEquals(expected.dayName(), actual.dayName());
    assertEquals(expected.name(), actual.name());
    assertEquals(expected.description(), actual.description());
    assertEquals(expected.start(), actual.start());
    assertEquals(expected.duration(), actual.duration());
    JsonEvent actual1 = this.event1.encode();
    JsonTime time1 = new JsonTime(4, Period.PM);
    JsonEvent expected1 = new JsonEvent(DayName.SUNDAY, "Event", "Hello", time1,
        2);
    assertEquals(expected1.dayName(), actual1.dayName());
    assertEquals(expected1.name(), actual1.name());
    assertEquals(expected1.description(), actual1.description());
    assertEquals(expected1.start(), actual1.start());
    assertEquals(expected1.duration(), actual1.duration());
  }

  /**
   * Tests for decoding a JsonEvent into an event
   */
  @Test
  void decode() {
    JsonTime time = new JsonTime(3, Period.AM);
    JsonEvent expected = new JsonEvent(DayName.SATURDAY, "StoneEvent", "", time,
        1);

    Event event = Event.decode(expected);

    assertEquals(expected.dayName(), event.getDayName());
    assertEquals(expected.name(), event.getName());
    assertEquals(expected.description(), event.getDescription());
    assertEquals("3 AM", event.getStart());
    assertEquals("1", event.getDuration());

    JsonTime time1 = new JsonTime(4, Period.PM);
    JsonEvent expected1 = new JsonEvent(DayName.SUNDAY, "Event", "Hello",
        time1, 2);
    Event event1 = Event.decode(expected1);
    assertEquals(expected1.dayName(), event1.getDayName());
    assertEquals(expected1.name(), event1.getName());
    assertEquals(expected1.description(), event1.getDescription());
    assertEquals("4 PM", event1.getStart());
    assertEquals("2", event1.getDuration());

  }
}