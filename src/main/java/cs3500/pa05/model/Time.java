package cs3500.pa05.model;

import cs3500.pa05.model.jsons.JsonTime;

/**
 * self-made representation of time for bullet journal purposes
 */
public class Time {
  private final int hour;
  private final Period period;

  /**
   * constructor for a time
   *
   * @param hour the hour of the day
   * @param period the period of time - AM or PM
   */
  public Time(int hour, Period period) {
    this.hour = hour;
    this.period = period;
  }

  /**
   * returns this time as a string
   *
   * @return this time as a string
   */
  public String getTimeAsString() {
    return hour + " " + period.toString();
  }

  /**
   * encodes this Time into it's JSON equivalent
   *
   * @return the JSON representation of this time
   */
  public JsonTime encode() {
    return new JsonTime(hour, period);
  }

  /**
   * decodes a time from it's JSON equivalent
   *
   * @param jsonTime the JSON equivalent of a time
   * @return the Time represented by the given JSON
   */
  public static Time decode(JsonTime jsonTime) {
    System.out.println("time decoded.");
    return new Time(jsonTime.hour(), jsonTime.period());
  }
}
