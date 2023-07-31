package cs3500.pa05.model.jsons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * A JSON representation for a week
 *
 * @param weekdays  the weekdays in this week, represented as JSON equivalents
 * @param maxTasks  the maximum number of tasks for each day in this week
 * @param maxEvents the maximum number of events for each day in this week
 */
public record JsonWeek(ArrayList<JsonWeekday> weekdays, int maxTasks, int maxEvents) {
  /**
   * A constructor for a JSONWeek
   *
   * @param weekdays  the weekdays in this week, represented as JSON equivalents
   * @param maxTasks  the maximum number of tasks for each day in this week
   * @param maxEvents the maximum number of events for each day in this week
   */
  @JsonCreator
  public JsonWeek(@JsonProperty("weekdays") ArrayList<JsonWeekday> weekdays,
                  @JsonProperty("maxTasks") int maxTasks,
                  @JsonProperty("maxEvents") int maxEvents) {
    this.weekdays = weekdays;
    this.maxTasks = maxTasks;
    this.maxEvents = maxEvents;
  }
}
