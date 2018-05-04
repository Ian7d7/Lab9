import java.awt.Color;
/**
 * An interface for games playable on a grid by a selecting a location on that
 * grid.  A grid playable game can report its grid dimensions, accept presses,
 * and report if a location is pressable, what label that grid space should
 * show to the user, and the color associated with that location.  In addition
 * a grid game can report its state, being a player turn, win or lose scenario
 * based on the build in GameState enumeration.
 */
interface GridPlayable
{
  /**
   * The grid width of the game. Should not change over the course of a game.
   * @return number of valid columns
   */
  int getGridWidth();

  /**
   * The grid height of the game. Should not change over the course of a game.
   * @return number of valid rows
   */
  int getGridHeight();

  /**
   * Interacts with the game by selecting a grid space.
   * @param row interact at specified row, zero indexed
   * @param col interact at specifed col, zero indexed
   */
  void press(int row, int column);

  /**
   * Retrives if specified location is avaliable for interaction.
   * @param row grid square row number, zero indexed
   * @param col grid square col number, zero indexed
   * @return true if interaction is possible at the given location
   */
  boolean getPressableAt(int row, int column);

  /**
   * Retrives a text label of the specified location. Should be short.
   * @param row grid square row number, zero indexed
   * @param col grid square col number, zero indexed
   * @return the label at the given location
   */
  String getLabelAt(int row, int column);

  /**
   * Retrives a color for the given location.
   * @param row grid square row number, zero indexed
   * @param col grid square col number, zero indexed
   * @return the color at the given location
   */
  Color getColorAt(int row, int column);

  /**
   * Enumeration of game states.  The player should be able to show a unique
   * status message for each of these possiblities.
   */
  public enum GameState { TURN_P1, TURN_P2, WIN_P1, WIN_P2, LOSE }

  /**
   * Retrives the current state of the game.
   * @return the apprpriate GameState state for the game.
   */
  GameState getGameState();
}
