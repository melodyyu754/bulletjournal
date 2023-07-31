package cs3500.pa05.model.jsons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.DayName;

/**
 * A JSON representation of a task
 *
 * @param name        the name of the task
 * @param description the string description of the event, empty if no description
 * @param completed   whether the task has been completed
 */
public record JsonTask(DayName dayName, String name, String description, boolean completed) {
  /**
   * A constructor for a JSONTask
   *
   * @param dayName     the Dayname day of the week of the task
   * @param name        the name of the task
   * @param description the string description of the event, empty if no description
   * @param completed   whether the task has been completed
   */
  @JsonCreator
  public JsonTask(@JsonProperty("dayName") DayName dayName,
                  @JsonProperty("name") String name,
                  @JsonProperty("description") String description,
                  @JsonProperty("completed") boolean completed) {
    this.dayName = dayName;
    this.name = name;
    this.description = description;
    this.completed = completed;
  }
}
