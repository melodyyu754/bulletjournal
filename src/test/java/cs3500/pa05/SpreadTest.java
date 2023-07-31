package cs3500.pa05;

import static cs3500.pa05.model.Theme.EARTHY;
import static cs3500.pa05.model.Theme.MINIMALIST;
import static cs3500.pa05.model.Theme.SUNSET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.DayName;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Period;
import cs3500.pa05.model.Spread;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.model.jsons.JsonSpread;
import cs3500.pa05.model.jsons.JsonWeek;
import cs3500.pa05.model.jsons.JsonWeekday;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a bullet journal spread.
 */
public class SpreadTest {
  Spread defaultSpread;
  Spread fancySpread;
  Week fancyWeek;
  Weekday mon;
  Weekday tues;
  ArrayList<Task> tuesTasks;
  Task trainTicket;
  Task finishOod;
  Task pack;
  Weekday wed;
  Weekday thurs;
  Weekday fri;
  Task getJob;
  Event bdayParty;
  Weekday sat;
  Weekday sun;
  ArrayList<Weekday> weekdays;

  /**
   * Initializes data for testing.
   */
  @BeforeEach
  public void initData() {
    defaultSpread = new Spread();
    mon = new Weekday(DayName.MONDAY);

    trainTicket = new Task(DayName.MONDAY, "Buy train ticket home", true);
    finishOod = new Task(DayName.MONDAY, "Finish PA05", false);
    pack = new Task(DayName.MONDAY, "Pack dorm", false);
    tuesTasks = new ArrayList<>(Arrays.asList(trainTicket, finishOod, pack));
    tues = new Weekday(DayName.TUESDAY, tuesTasks, new ArrayList<>());

    wed = new Weekday(DayName.WEDNESDAY);
    thurs = new Weekday(DayName.THURSDAY);

    getJob = new Task(DayName.WEDNESDAY, "Get a summer job", false);
    bdayParty = new Event(DayName.WEDNESDAY, "Surprise bday party", new Time(1, Period.PM), 4);
    ArrayList<Task> friTasks = new ArrayList<>(List.of(getJob));
    ArrayList<Event> friEvents = new ArrayList<>(List.of(bdayParty));
    fri = new Weekday(DayName.FRIDAY, friTasks, friEvents);

    sat = new Weekday(DayName.SATURDAY);
    sun = new Weekday(DayName.SUNDAY);

    weekdays = new ArrayList<>(Arrays.asList(mon, tues, wed, thurs, fri, sat, sun));
    fancyWeek = new Week(5, 3, weekdays);
    fancySpread = new Spread(SUNSET, "My Week", fancyWeek,
        "TODO:\n- go to Star Market\n- finish OOD");
  }

  /**
   * Tests for correct integer representing spread theme.
   */
  @Test
  public void testGetThemeAsInt() {
    assertEquals(0, defaultSpread.getThemeAsInt());
    assertEquals(2, fancySpread.getThemeAsInt());
  }

  /**
   * Tests for correct name accessor.
   */
  @Test
  public void testGetName() {
    assertEquals("New Week", defaultSpread.getName());
    assertEquals("My Week", fancySpread.getName());
  }

  /**
   * Tests for correct week accessor.
   */
  @Test
  public void testGetWeek() {
    assertEquals(fancyWeek, fancySpread.getWeek());
  }

  /**
   * Tests for correct quotes and notes accessor.
   */
  @Test
  public void testGetQuotesNotes() {
    assertEquals("", defaultSpread.getQuotesNotes());
    assertEquals("TODO:\n- go to Star Market\n- finish OOD",
        fancySpread.getQuotesNotes());
  }

  /**
   * Tests for correct theme change functionality.
   */
  @Test
  public void testChangeTheme() {
    assertEquals(2, fancySpread.getThemeAsInt());
    fancySpread.changeTheme(EARTHY);
    assertEquals(1, fancySpread.getThemeAsInt());
  }

  /**
   * Tests for correct name change functionality.
   */
  @Test
  public void testChangeName() {
    assertEquals("New Week", defaultSpread.getName());
    defaultSpread.changeName("New Name");
    assertEquals("New Name", defaultSpread.getName());

    assertEquals("My Week", fancySpread.getName());
    fancySpread.changeName("Your Week");
    assertEquals("Your Week", fancySpread.getName());
  }

  /**
   * Tests for correct quotes notes change functionality.
   */
  @Test
  public void testChangeQuotesNotes() {
    assertEquals("", defaultSpread.getQuotesNotes());
    String newNote = "Who is a computer’s favorite singer?\nA Dell.";
    defaultSpread.changeQuotesNotes(newNote);
    assertEquals(newNote, defaultSpread.getQuotesNotes());
  }

  /**
   * Tests encoding to JSON functionality.
   */
  @Test
  public void testEncode() {
    String expected = "{\"theme\":\"SUNSET\",\"name\":\"My Week\",\"week\":{\"weekdays\":"
        + "[{\"day\":\"MONDAY\",\"tasks\":[],\"events\":[]},{\"day\":\"TUESDAY\",\"tasks\":"
        + "[{\"dayName\":\"MONDAY\",\"name\":\"Buy train ticket home\",\"description\":\"\",\""
        + "completed\":true},{\"dayName\":\"MONDAY\",\"name\":\"Finish PA05\",\"description\":\"\""
        + ",\"completed\":false},{\"dayName\":\"MONDAY\",\"name\":\"Pack dorm\",\"description\":\""
        + "\",\"completed\":false}],\"events\":[]},{\"day\":\"WEDNESDAY\",\"tasks\":[],\"events\":"
        + "[]},{\"day\":\"THURSDAY\",\"tasks\":[],\"events\":[]},{\"day\":\"FRIDAY\",\"tasks\":"
        + "[{\"dayName\":\"WEDNESDAY\",\"name\":\"Get a summer job\",\"description\":\"\",\""
        + "completed\":false}],\"events\":[{\"dayName\":\"WEDNESDAY\",\"name\":\"Surprise bday "
        + "party\",\"description\":\"\",\"start\":{\"hour\":1,\"period\":\"PM\"},\"duration\":4}]},"
        + "{\"day\":\"SATURDAY\",\"tasks\":[],\"events\":[]},{\"day\":\"SUNDAY\",\"tasks\":[],\"e"
        + "vents\":[]}],\"maxTasks\":5,\"maxEvents\":3},\"quotesNotes\":\"TODO:\n"
        + "- go to Star Market\n- finish OOD\"}";
    fancySpread.encode();
  }

  /**
   * Tests decoding from JSON functionality.
   */
  @Test
  public void testDecode() {
    JsonWeekday monJson = new JsonWeekday(DayName.MONDAY, new ArrayList<>(), new ArrayList<>());
    JsonWeekday tuesJson = new JsonWeekday(DayName.TUESDAY, new ArrayList<>(), new ArrayList<>());
    JsonWeekday wedJson = new JsonWeekday(DayName.WEDNESDAY, new ArrayList<>(), new ArrayList<>());
    JsonWeekday thursJson = new JsonWeekday(DayName.THURSDAY, new ArrayList<>(), new ArrayList<>());
    JsonWeekday friJson = new JsonWeekday(DayName.FRIDAY, new ArrayList<>(), new ArrayList<>());
    JsonWeekday satJson = new JsonWeekday(DayName.SATURDAY, new ArrayList<>(), new ArrayList<>());
    JsonWeekday sunJson = new JsonWeekday(DayName.SUNDAY, new ArrayList<>(), new ArrayList<>());

    ArrayList<JsonWeekday> weekdaysJson = new ArrayList<>(Arrays.asList(
        monJson, tuesJson, wedJson, thursJson, friJson, satJson, sunJson));

    JsonWeek defaultWeekJson = new JsonWeek(weekdaysJson, 3, 3);
    JsonSpread defaultSpreadJson = new JsonSpread(MINIMALIST, defaultSpread.getName(),
        defaultWeekJson, "");
    assertEquals(defaultSpread, Spread.decode(defaultSpreadJson));
  }

  /**
   * tests for overridden hashcode method
   */
  @Test
  public void testHashCode() {
    assertEquals(296, defaultSpread.hashCode());
    assertEquals(30303, fancySpread.hashCode());
  }

  /**
   * Testing for equals method
   */
  @Test
  public void testEquals() {
    assertFalse(this.defaultSpread.equals(fancySpread));
    Spread s = new Spread(SUNSET, "My Week", new Week(5, 5, weekdays),
        "TODO:\n- go to Star Market\n- finish OOD");
    assertTrue(this.fancySpread.equals(s));
    assertFalse(this.fancySpread.equals("Blah"));
  }
}
