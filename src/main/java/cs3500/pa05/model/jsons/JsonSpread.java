package cs3500.pa05.model.jsons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Theme;

/**
 * A JSON representation of a Spread for a bullet journal
 *
 * @param theme       the current theme of the Spread
 * @param name        the name of the Spread, empty if not changed by the user
 * @param week        the week, containing events and tasks
 * @param quotesNotes the contents of the quotes and notes section of the spread
 */
public record JsonSpread(Theme theme, String name, JsonWeek week, String quotesNotes) {
  /**
   * A constructor for a JSON spread
   *
   * @param theme       the current theme of the Spread
   * @param name        the name of the Spread, empty if not changed by the user
   * @param week        the week, containing events and tasks
   * @param quotesNotes the contents of the quotes and notes section of the spread
   */
  @JsonCreator
  public JsonSpread(@JsonProperty("theme") Theme theme,
                    @JsonProperty("name") String name,
                    @JsonProperty("week") JsonWeek week,
                    @JsonProperty("quotesNotes") String quotesNotes) {
    this.theme = theme;
    this.name = name;
    this.week = week;
    this.quotesNotes = quotesNotes;
  }
}