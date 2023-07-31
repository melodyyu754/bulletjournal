package cs3500.pa05.model.jsons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.DayName;

/**
 * A JSON representation of an Event
 *
 * @param name        the string name of the event
 * @param description the string description of the event, empty if no description
 * @param start       the start time, represented as a JSONTime
 * @param duration    the length of the event, in integers
 */
public record JsonEvent(DayName dayName, String name, String description, JsonTime start,
                        int duration) {
  /**
   * A constructor for a JSONEvent
   *
   * @param dayName     the Dayname day of the week
   * @param name        the string name of the event
   * @param description the string description of the event, empty if no description
   * @param start       the start time, represented as a JSONTime
   * @param duration    the length of the event, in integers
   */
  @JsonCreator
  public JsonEvent(@JsonProperty("dayName") DayName dayName,
                   @JsonProperty("name") String name,
                   @JsonProperty("description") String description,
                   @JsonProperty("start") JsonTime start,
                   @JsonProperty("duration") int duration) {
    this.dayName = dayName;
    this.name = name;
    this.description = description;
    this.start = start;
    this.duration = duration;
  }
}
