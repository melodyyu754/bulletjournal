package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.jsons.JsonTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeTest {

  Time time1;
  Time time2;

  /**
   * Sets up the time object
   */
  @BeforeEach
  void setUp() {
    this.time1 = new Time(1, Period.AM);
    this.time2 = new Time(2, Period.PM);
  }

  /**
   * testing if the converting the time to a string preserves the information of the time class
   */
  @Test
  void getTimeAsString() {
    assertEquals("1 AM", this.time1.getTimeAsString());
    assertEquals("2 PM", this.time2.getTimeAsString());
  }

  /**
   * Tests if encoding a time class into a JSON time preserves the fields
   */
  @Test
  void encode() {
    JsonTime expected = new JsonTime(1, Period.AM);
    JsonTime expected1 = new JsonTime(2, Period.PM);

    JsonTime actual = this.time1.encode();
    JsonTime actual1 = this.time2.encode();

    assertEquals(expected.hour(), actual.hour());
    assertEquals(expected.period(), actual.period());

    assertEquals(expected1.hour(), actual1.hour());
    assertEquals(expected1.period(), actual1.period());
  }

  /**
   * Tests if decoding a JSONTIme into a time class preserves the fields
   */
  @Test
  void decode() {
    JsonTime expected = new JsonTime(1, Period.AM);
    JsonTime expected1 = new JsonTime(2, Period.PM);

    Time actual = Time.decode(expected);
    Time actual1 = Time.decode(expected1);

    assertEquals("1 AM", actual.getTimeAsString());
    assertEquals("2 PM", actual1.getTimeAsString());
  }
}