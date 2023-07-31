package cs3500.pa05.model.jsons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Period;

/**
 * A JSON representation of time
 *
 * @param hour   the hour
 * @param period AM or PM
 */
public record JsonTime(int hour, Period period) {
  /**
   * A constructor for a JsonTime
   *
   * @param hour   the hour
   * @param period AM or PM
   */
  @JsonCreator
  public JsonTime(@JsonProperty("hour") int hour,
                  @JsonProperty("period") Period period) {
    this.hour = hour;
    this.period = period;
  }
}
