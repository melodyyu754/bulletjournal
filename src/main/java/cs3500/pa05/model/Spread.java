package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.jsons.JsonSpread;

/**
 * Represents the underlying information contained by a Spread in a bullet journal,
 * allows for updating this information
 */
public class Spread {
  private Theme theme;
  private String name;
  private final Week week;
  private String quotesNotes;

  /**
   * Default constructor for a new spread - i.e. with no
   * user customization of theme/name.
   */
  public Spread() {
    this.theme = Theme.MINIMALIST;
    this.name = "New Week";
    this.week = new Week();
    this.quotesNotes = "";
  }

  /**
   * /**
   * Constructor for a spread with known theme, name, and week -
   * i.e. when loading a .bujo file.
   *
   * @param theme       the theme for this spread
   * @param name        the name of this spread
   * @param week        the week displayed by this spread
   * @param quotesNotes the string displayed by the quotes and notes section of this spread
   */
  public Spread(Theme theme, String name, Week week, String quotesNotes) {
    this.theme = theme;
    this.name = name;
    this.week = week;
    this.quotesNotes = quotesNotes;
  }

  /**
   * Returns the current theme as the ordinal representing it
   *
   * @return the ordinal of the current theme
   */
  public int getThemeAsInt() {
    return this.theme.ordinal();
  }

  /**
   * returns the name of this spread
   *
   * @return this spread's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * returns the week of this spread
   *
   * @return this spread's week
   */
  public Week getWeek() {
    return this.week;
  }

  /**
   * returns the content of the quotes and notes section of this spread
   *
   * @return the content of this spread's quotes and notes
   */
  public String getQuotesNotes() {
    return this.quotesNotes;
  }

  /**
   * changes the theme of this spread
   *
   * @param newTheme the new theme of this spread
   */
  public void changeTheme(Theme newTheme) {
    this.theme = newTheme;
  }

  /**
   * changes the name of this spread
   *
   * @param newName the new name of this spread
   */
  public void changeName(String newName) {
    this.name = newName;
  }

  /**
   * changes the quotes and notes of this spread
   *
   * @param newQuotesNotes the new quotes and notes of this spread
   */
  public void changeQuotesNotes(String newQuotesNotes) {
    this.quotesNotes = newQuotesNotes;
  }

  /**
   * encodes this spread into it's JSON equivalent
   *
   * @return the JSON representation of a spread
   */
  public String encode() {
    JsonSpread jsonSpread = new JsonSpread(theme, name, week.encode(), quotesNotes);

    String encodedSpread;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      encodedSpread = objectMapper.writeValueAsString(jsonSpread);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable to encode spread.");
    }

    return encodedSpread;
  }

  /**
   * decodes this spread from it's JSON equivalent
   *
   * @param jsonSpread the JSON representation of this spread
   * @return the Spread represented by the given JSON
   */
  public static Spread decode(JsonSpread jsonSpread) {
    return new Spread(jsonSpread.theme(), jsonSpread.name(),
        Week.decode(jsonSpread.week()), jsonSpread.quotesNotes());
  }

  /**
   * overrides the .equals method for testing purposes -
   * simplified
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Spread other) {
      return this.theme.equals(other.theme)
          && this.name.equals(other.name)
          && this.quotesNotes.equals(other.quotesNotes);
    }

    return false;
  }

  /**
   * overrides the .hashCode method for testing purposes
   *
   * @return an integer representing the hasCode of this Spread
   */
  @Override
  public int hashCode() {
    return (this.theme.ordinal() + 1) * (this.name.length() * 37)
        * (this.quotesNotes.length() + 1);
  }


}