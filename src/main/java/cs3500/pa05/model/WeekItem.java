package cs3500.pa05.model;

/**
 * abstraction of items in a calendar week - i.e. tasks or events
 */
public abstract class WeekItem {
  /**
   * The DayName day of the week
   */
  protected DayName dayName;
  /**
   * The String name of the WeekItem
   */
  protected String name;
  /**
   * The String description of the WeekItem
   */
  protected String description;

  /**
   * constructor for a WeekItem with a description
   *
   * @param dayName the day on which this WeekItem occurs
   * @param name the name of the item
   * @param description the description of the item
   */
  public WeekItem(DayName dayName, String name, String description) {
    this.dayName = dayName;
    this.name = name;
    this.description = description;
  }

  /**
   * constructor for a WeekItem with no description
   *
   * @param dayName the day on which this weekItem occurs
   * @param name the name of the item
   */
  public WeekItem(DayName dayName, String name) {
    this.dayName = dayName;
    this.name = name;
    this.description = "";
  }

  /**
   * returns the day that this event occurs
   *
   * @return this event's day
   */
  public DayName getDayName() {
    return this.dayName;
  }

  /**
   * returns the name of this event
   *
   * @return this event's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * returns the description of this event
   *
   * @return this event's description
   */
  public String getDescription() {
    return this.description;
  }
}
