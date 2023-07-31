package cs3500.pa05.model;

import cs3500.pa05.model.jsons.JsonEvent;

/**
 * Represents an Event in a Week
 */
public class Event extends WeekItem {
  private final Time start;
  private final int duration;

  /**
   * Constructor for an Event with a description
   *
   * @param dayName the day that this event occurs
   * @param name the name of the event
   * @param description the description of this event
   * @param start the start time of this event
   * @param duration the duration of this event, in hours
   */
  public Event(DayName dayName, String name, String description, Time start, int duration) {
    super(dayName, name, description);
    this.start = start;
    this.duration = duration;
  }

  /**
   * Constructor for an Event with no description
   *
   * @param dayName the day that this event occurs
   * @param name the name of this event
   * @param start the start time of this event
   * @param duration the duration of this event, in hours
   */
  public Event(DayName dayName, String name, Time start, int duration) {
    super(dayName, name);
    this.start = start;
    this.duration = duration;
  }

  /**
   * returns the start time of this event
   *
   * @return this event's start time
   */
  public String getStart() {
    return this.start.getTimeAsString();
  }

  /**
   * returns the duration of this event, in hours
   *
   * @return this event's duration, in hours
   */
  public String getDuration() {
    return "" + this.duration;
  }

  /**
   * encodes this event into it's JSON equivalent
   *
   * @return the JSON representation of an event
   */
  public JsonEvent encode() {
    return new JsonEvent(dayName, name, description, start.encode(), duration);
  }

  /**
   * decodes an event from it's JSON equivalent
   *
   * @param jsonEvent the JSON representation of an event
   * @return the Event represented by the given JSON
   */
  public static Event decode(JsonEvent jsonEvent) {
    return new Event(jsonEvent.dayName(), jsonEvent.name(), jsonEvent.description(),
        Time.decode(jsonEvent.start()), jsonEvent.duration());
  }
}
