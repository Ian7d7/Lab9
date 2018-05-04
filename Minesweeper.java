import java.util.Random;
import java.awt.Color;

/**
  * Simulates a game of Minesweeper
  */
public class Minesweeper implements GridPlayable
{
  private String[][][] board;
  private int width;
  private int height;
  private GridPlayable.GameState state;
  private int lastX;
  private int lastY;

  public Minesweeper()
  {
    this.board = new String[15][10][4]; // Creates a three dimensional grid that can hold 4 values at each point
    this.width = 15;
    this.height = 10;
    this.state = GridPlayable.GameState.TURN_P1;
    Random rand = new Random();
    int bombCount = 0;
    while(bombCount < 20) // Sets twenty random spaces as bomb spaces
    {
      int x = rand.nextInt(15);
      int y = rand.nextInt(10);
      if(this.board[x][y][0] != "B")
      {
        this.board[x][y][3] = "Hidden";
        this.board[x][y][2] = "R";
        this.board[x][y][1] = "T";
        this.board[x][y][0] = "B";
        bombCount++;
      }
    }
    for(int i = 0; i < 15; i++) // Sets the appropriate label for each space with respect to where the bombs lie
    {
      for(int j = 0; j < 10; j++)
      {
        if(this.board[i][j][0] != "B")
        {
          int count = this.lookAround(i, j);
          this.board[i][j][1] = "T";
          this.board[i][j][3] = "Hidden";
          if(count == 0)
          {
            this.board[i][j][0] = "SAFE";
          }
          if(count == 1)
          {
            this.board[i][j][0] = "1";
            this.board[i][j][2] = "B";
          }
          if(count == 2)
          {
            this.board[i][j][0] = "2";
            this.board[i][j][2] = "G";
          }
          if(count == 3)
          {
            this.board[i][j][0] = "3";
            this.board[i][j][2] = "Y";
          }
          if(count == 4)
          {
            this.board[i][j][0] = "4";
            this.board[i][j][2] = "M";
          }
          if(count == 5)
          {
            this.board[i][j][0] = "5";
            this.board[i][j][2] = "O";
          }
          if(count == 6)
          {
            this.board[i][j][0] = "6";
            this.board[i][j][2] = "C";
          }
          if(count == 7)
          {
            this.board[i][j][0] = "7";
            this.board[i][j][2] = "W";
          }
          if(count == 8)
          {
            this.board[i][j][0] = "8";
            this.board[i][j][2] = "BL";
          }
        }
      }
    }
  }

  /**
    * Helper method that allows takes in a point and seaches for
    * bombs around that point in all directions
    */
  private int lookAround(int x, int y)
  {
    int truthCount = 0;
    if(x < 14)
    {
      if(this.board[x+1][y][0] == "B")
      {truthCount++;}
    }
    if(x > 0)
    {
      if(this.board[x-1][y][0] == "B")
      {truthCount++;}
    }
    if(y < 9)
    {
      if(this.board[x][y+1][0] == "B")
      {truthCount++;}
    }
    if(y > 0)
    {
      if(this.board[x][y-1][0] == "B")
      {truthCount++;}
    }
    if(x < 14 && y > 0)
    {
      if(this.board[x+1][y-1][0] == "B")
      {truthCount++;}
    }
    if(x < 14 && y < 9)
    {
      if(this.board[x+1][y+1][0] == "B")
      {truthCount++;}
    }
    if(x > 0 && y < 9)
    {
      if(this.board[x-1][y+1][0] == "B")
      {truthCount++;}
    }
    if(x > 0 && y > 0)
    {
      if(this.board[x-1][y-1][0] == "B")
      {truthCount++;}
    }
    return truthCount;
  }

  /**
    * Helper method that presses all the coordinates around a hidden and non-bomb space
    */
  private void pressAround(int x, int y)
  {
    if(x < 14)
    {
      if(this.board[x+1][y][0] != "B" && this.board[x+1][y][3] == "Hidden")
      {press(x+1, y);}
    }
    if(x > 0)
    {
      if(this.board[x-1][y][0] != "B" && this.board[x-1][y][3] == "Hidden")
      {press(x-1, y);}
    }
    if(y < 9)
    {
      if(this.board[x][y+1][0] != "B" && this.board[x][y+1][3] == "Hidden")
      {press(x, y+1);}
    }
    if(y > 0)
    {
      if(this.board[x][y-1][0] != "B" && this.board[x][y-1][3] == "Hidden")
      {press(x, y-1);}
    }
    if(x < 14 && y > 0)
    {
      if(this.board[x+1][y-1][0] != "B" && this.board[x+1][y-1][3] == "Hidden")
      {press(x+1, y-1);}
    }
    if(x < 14 && y < 9)
    {
      if(this.board[x+1][y+1][0] != "B" && this.board[x+1][y+1][3] == "Hidden")
      {press(x+1, y+1);}
    }
    if(x > 0 && y < 9)
    {
      if(this.board[x-1][y+1][0] != "B" && this.board[x-1][y+1][3] == "Hidden")
      {press(x-1, y+1);}
    }
    if(x > 0 && y > 0)
    {
      if(this.board[x-1][y-1][0] != "B" && this.board[x-1][y-1][3] == "Hidden")
      {press(x-1, y-1);}
    }
  }

  /**
   * The grid width of the game. Should not change over the course of a game.
   * @return number of valid columns
   */
  public int getGridWidth()
  {return this.width;}

  /**
   * The grid height of the game. Should not change over the course of a game.
   * @return number of valid rows
   */
  public int getGridHeight()
  {return this.height;}

  /**
   * Interacts with the game by selecting a grid space.
   * @param row interact at specified row, zero indexed
   * @param col interact at specifed col, zero indexed
   */
  public void press(int row, int column)
  {
    if(this.board[row][column][0] == "B")
    {
      this.revealBombs();
      this.state = GridPlayable.GameState.LOSE;
    }
    if(this.board[row][column][0] == "SAFE")
    {
      this.board[row][column][1] = "F";
      this.board[row][column][3] = "Not Hidden";
      this.pressAround(row, column);
    }
    else
    {
      this.board[row][column][1] = "F";
      this.board[row][column][3] = "Not Hidden";
    }
  }

  /**
    * Allows all bombs to be seen on the board
    */
  private void revealBombs()
  {
    for(int i = 0; i < 15; i++)
    {
      for(int j = 0; j < 10; j++)
      {
        if(this.board[i][j][0] == "B")
        {
          this.board[i][j][1] = "F";
          this.board[i][j][3] = "Not Hidden";
        }
      }
    }
  }

  /**
   * Retrives if specified location is avaliable for interaction.
   * @param row grid square row number, zero indexed
   * @param col grid square col number, zero indexed
   * @return true if interaction is possible at the given location
   */
  public boolean getPressableAt(int row, int column)
  {
    if(this.board[row][column][1] == "F")
    {return false;}
    return true;
  }

  /**
   * Retrives a text label of the specified location. Should be short.
   * @param row grid square row number, zero indexed
   * @param col grid square col number, zero indexed
   * @return the label at the given location
   */
  public String getLabelAt(int row, int column)
  {return this.board[row][column][0];}

  /**
   * Retrives a color for the given location.
   * @param row grid square row number, zero indexed
   * @param col grid square col number, zero indexed
   * @return the color at the given location
   */
  public Color getColorAt(int row, int column)
  {
    if(this.board[row][column][2] == "R")
    {return Color.RED;}
    if(this.board[row][column][2] == "B")
    {return Color.BLUE;}
    if(this.board[row][column][2] == "G")
    {return Color.GREEN;}
    if(this.board[row][column][2] == "Y")
    {return Color.YELLOW;}
    if(this.board[row][column][2] == "M")
    {return Color.MAGENTA;}
    if(this.board[row][column][2] == "O")
    {return Color.ORANGE;}
    if(this.board[row][column][2] == "C")
    {return Color.CYAN;}
    if(this.board[row][column][2] == "W")
    {return Color.WHITE;}
    if(this.board[row][column][2] == "BL")
    {return Color.BLACK;}
    else {return Color.DARK_GRAY;}
  }

  /**
   * Retrives the current state of the game.
   * @return the apprpriate GameState state for the game.
   */
  public GameState getGameState()
  {return this.state;}
}
