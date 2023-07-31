package cs3500.pa05.model.jsons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.DayName;
import java.util.ArrayList;

/**
 * A JSON representation of a weekday
 *
 * @param day    the name of the day
 * @param tasks  the tasks of this day, represented as their JSON equivalents
 * @param events the events of this day, represented as their JSON equivalents
 */
public record JsonWeekday(DayName day, ArrayList<JsonTask> tasks, ArrayList<JsonEvent> events) {
  /**
   * A constructor for a JSONWeekday
   *
   * @param day    the name of the day
   * @param tasks  the tasks of this day, represented as their JSON equivalents
   * @param events the events of this day, represented as their JSON equivalents
   */
  @JsonCreator
  public JsonWeekday(@JsonProperty("day") DayName day,
                     @JsonProperty("tasks") ArrayList<JsonTask> tasks,
                     @JsonProperty("events") ArrayList<JsonEvent> events) {
    this.day = day;
    this.tasks = tasks;
    this.events = events;
  }
}
