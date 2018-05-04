import java.awt.Color;

/**
  Simulates a game of Connect Four
**/
public class ConnectFour implements GridPlayable
{
  private String[][][] board;
  private int width;
  private int height;
  private GridPlayable.GameState state;
  private int lastX;
  private int lastY;

  public ConnectFour()
  {
    this.board = new String[10][8][3]; //Creates a grid that can hold 3 values at each point
    this.width = 10;
    this.height = 8;
    this.state = GridPlayable.GameState.TURN_P1;
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
    while(this.fall(row, column)) // Determines whether or not the label is at the lowest possible space
    {column++;}
    if(this.getPressableAt(row, column));
    {
      if(this.state == GridPlayable.GameState.TURN_P1)
      {
        this.board[row][column][0] = "X";
        this.board[row][column][1] = "F";
        this.board[row][column][2] = "B";
        this.lastX = row;
        this.lastY = column;
        if(this.checkWin()){return;}
        this.state = GridPlayable.GameState.TURN_P2;
      }
      else if(this.state == GridPlayable.GameState.TURN_P2)
      {
        this.board[row][column][0] = "O";
        this.board[row][column][1] = "F";
        this.board[row][column][2] = "R";
        this.lastX = row;
        this.lastY = column;
        if(this.checkWin()){return;}
        this.state = GridPlayable.GameState.TURN_P1;
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
  {
    return this.board[row][column][0];
  }


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
    else {return Color.BLUE;}
  }

  /**
    Checks for any instance of a Connect Four win and returns a boolean if any instance exits.
    Also checks for a full board and returns true if the board it full.
    **/
  private boolean checkWin()
  {
    if(this.checkVertical() || this.checkHorizontal() || this.checkUpDiagonal() || this.checkDownDiagonal())
    {
      if(this.state == GridPlayable.GameState.TURN_P1)
      {this.state = GridPlayable.GameState.WIN_P1;}
      if(this.state == GridPlayable.GameState.TURN_P2)
      {this.state = GridPlayable.GameState.WIN_P2;}
      return true;
    }
    if(this.checkFull())
    {
      this.state = GridPlayable.GameState.LOSE;
      return true;
    }
    return false;
  }

  /**
    Checks for a full board
    **/
  private boolean checkFull()
  {
    for(int i = 0; i < 10; i++)
    {
      for(int j = 0; j < 8; j++)
      {
        if(this.board[i][j][0] == null)
        {return false;}
      }
    }
    return true;
  }

  /**
    Checks for an instance of a vertical win.
    **/
  private boolean checkVertical()
  {
    for(int i = 0; i < 3; i++)
    {
      if(this.checkDown(this.lastX, this.lastY + i) == false)
      {return false;}
    }
    return true;
  }

  /**
    Checks for an instance of a horizontal win.
   */
  private boolean checkHorizontal()
  {
    boolean r1 = false;
    boolean r2 = false;
    boolean r3 = false;
    boolean l1 = false;
    boolean l2 = false;
    boolean l3 = false;
    String label = this.getLabelAt(this.lastX, this.lastY);
    for(int i = 0; i < 3; i++)
    {
      switch(i)
      {
        case 0:
        {
          if(this.checkLeft(this.lastX, this.lastY)){l1 = true;}
          if(this.checkRight(this.lastX, this.lastY)){r1 = true;}
          break;
        }
        case 1:
        {
          if(this.checkLeft(this.lastX-i, this.lastY)){l2 = true;}
          if(this.checkRight(this.lastX+i, this.lastY)){r2 = true;}
          break;
        }
        case 2:
        {
          if(this.checkLeft(this.lastX-i, this.lastY)){l3 = true;}
          if(this.checkRight(this.lastX+i, this.lastY)){r3 = true;}
          break;
        }
      }
      if((r1 && r2 && r3) || (l1 && l2 && l3) || (l1 && r1 && r2) || (r1 && l1 && l2))
      {return true;}
    }
    return false;
  }

  /**
    Checks for an instance of a positive slope diagonal win.
    **/
  private boolean checkUpDiagonal()
  {
    boolean r1 = false;
    boolean r2 = false;
    boolean r3 = false;
    boolean l1 = false;
    boolean l2 = false;
    boolean l3 = false;
    String label = this.getLabelAt(this.lastX, this.lastY);
    for(int i = 0; i < 3; i++)
    {
      switch(i)
      {
        case 0:
        {
          if(this.checkDiagDownLeft(this.lastX, this.lastY)){l1 = true;}
          if(this.checkDiagUpRight(this.lastX, this.lastY)){r1 = true;}
          break;
        }
        case 1:
        {
          if(this.checkDiagDownLeft(this.lastX-i, this.lastY+i)){l2 = true;}
          if(this.checkDiagUpRight(this.lastX+i, this.lastY-i)){r2 = true;}
          break;
        }
        case 2:
        {
          if(this.checkDiagDownLeft(this.lastX-i, this.lastY+i)){l3 = true;}
          if(this.checkDiagUpRight(this.lastX+i, this.lastY-i)){r3 = true;}
          break;
        }
      }
      if((r1 && r2 && r3) || (l1 && l2 && l3) || (l1 && r1 && r2) || (r1 && l1 && l2))
      {return true;}
    }
    return false;
  }

  /**
    Checks for an instance of a negative sloped diagonal win
    **/
  private boolean checkDownDiagonal()
  {
    boolean r1 = false;
    boolean r2 = false;
    boolean r3 = false;
    boolean l1 = false;
    boolean l2 = false;
    boolean l3 = false;
    String label = this.getLabelAt(this.lastX, this.lastY);
    for(int i = 0; i < 3; i++)
    {
      switch(i)
      {
        case 0:
        {
          if(this.checkDiagUpLeft(this.lastX, this.lastY)){l1 = true;}
          if(this.checkDiagDownRight(this.lastX, this.lastY)){r1 = true;}
          break;
        }
        case 1:
        {
          if(this.checkDiagUpLeft(this.lastX-i, this.lastY-i)){l2 = true;}
          if(this.checkDiagDownRight(this.lastX+i, this.lastY+i)){r2 = true;}
          break;
        }
        case 2:
        {
          if(this.checkDiagUpLeft(this.lastX-i, this.lastY-i)){l3 = true;}
          if(this.checkDiagDownRight(this.lastX+i, this.lastY+i)){r3 = true;}
          break;
        }
      }
      if((r1 && r2 && r3) || (l1 && l2 && l3) || (l1 && r1 && r2) || (r1 && l1 && l2))
      {return true;}
    }
    return false;
  }

  /**
    Helper method used in checkDown/Up/Left/Right methods
    **/
  private String next(int x, int y)
  {return this.getLabelAt(x+1, y);}

  /**
    Helper method used in checkDown/Up/Left/Right methods
    **/
  private String previous(int x, int y)
  {return this.getLabelAt(x-1, y);}

  /**
    Helper method used in checkDown/Up/Left/Right methods
    **/
  private String up(int x, int y)
  {return this.getLabelAt(x, y-1);}

  /**
    Helper method used in checkDown/Up/Left/Right methods
    **/
  private String down(int x, int y)
  {return this.getLabelAt(x, y+1);}

  /**
    Checks for a matching label directly below the inputed coordinates
    **/
  private boolean checkDown(int x, int y)
  {
    if(y >= 7)
    {return false;}
    return this.getLabelAt(x, y) == this.down(x, y);
  }

  /**
    Checks for a matching label directly to the left of the inputed coordinates
    **/
  private boolean checkLeft(int x, int y)
  {
    if(x <= 0)
    {return false;}
    return this.getLabelAt(x, y) == this.previous(x, y);
  }

  /**
    Checks for a matching label directly to the right of the inputed coordinates
    **/
  private boolean checkRight(int x, int y)
  {
    if(x >= 9)
    {return false;}
    return this.getLabelAt(x, y) == this.next(x, y);
  }

  /**
    Checks for a matching label directly up and to the right of the inputed coordinates
    **/
  private boolean checkDiagUpRight(int x, int y)
  {
    if(y <= 0)
    {return false;}
    if(x >= 9)
    {return false;}
    return this.getLabelAt(x, y) == this.getLabelAt(x + 1, y - 1);
  }

  /**
    Checks for a matching label directly up and to the left of the inputed coordinates
    **/
  private boolean checkDiagUpLeft(int x, int y)
  {
    if(y <= 0)
    {return false;}
    if(x <= 0)
    {return false;}
    return this.getLabelAt(x, y) == this.getLabelAt(x - 1 , y - 1);
  }

  /**
    Checks for a matching label directly below and to the right of the inputed coordinates
    **/
  private boolean checkDiagDownRight(int x, int y)
  {
    if(y >= 7)
    {return false;}
    if(x >= 9)
    {return false;}
    return this.getLabelAt(x, y) == this.getLabelAt(x + 1, y + 1);
  }

  /**
    Checks for a matching label directly below and to the left of the inputed coordinates
    **/
  private boolean checkDiagDownLeft(int x, int y)
  {
    if(y >= 7)
    {return false;}
    if(x <= 0)
    {return false;}
    return this.getLabelAt(x, y) == this.getLabelAt(x - 1, y + 1);
  }

  /**
    * Checks for an empty space directly below the inputed coordinates.
    */
  private boolean fall(int x, int y)
  {
    if(y == 7)
    {return false;}
    if(this.getLabelAt(x, y+1) != null)
    {return false;}
    return true;
  }

  /**
   * Retrives the current state of the game.
   * @return the apprpriate GameState state for the game.
   */
  public GameState getGameState()
  {return this.state;}
}
