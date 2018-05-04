import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
  A GUI launcher for any GridPlayable game.
**/
public class GridPlayer extends JFrame
{
  private GridPlayable game;
  private JPanel grid;
  private int width;
  private int height;
  private StatusBar status;
  private JButton[][] buttonGrid;

  public GridPlayer(GridPlayable game)
  {
    super();
    this.game = game;
    this.width = game.getGridWidth();
    this.height = game.getGridHeight();
    this.setSize(500, 500);
    this.setLayout(new BorderLayout());
    this.grid = new JPanel(); //Button panel
    this.status = new StatusBar(); //Status Bar
    this.grid.setLayout(new GridLayout(this.height, this.width, 1, 1));
    this.buttonGrid = new JButton[this.width][this.height];
    for(int i = 0; i < this.height; i++) //Adds buttons to the panel and hides the labels
    {
      for(int j = 0; j < this.width; j++)
      {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(75, 75));
        button.addMouseListener(new GameButtonListener());
        this.buttonGrid[j][i] = button;
        this.grid.add(button);
        buttonGrid[j][i].setForeground(Color.WHITE);
        buttonGrid[j][i].setText(game.getLabelAt(j, i));
      }
    }
    this.add(this.grid, BorderLayout.CENTER); //Adds the button panel to the window
    this.add(this.status, BorderLayout.SOUTH); //Adds the status bar to the window
    this.setVisible(true);
    this.setResizable(false);
    this.pack();
  }

  /**
    Keeps track of the game's events through interaction with the mouse.
  **/
  private class GameButtonListener implements MouseListener
  {
    public void mouseClicked(MouseEvent e)
		{
      JButton pressedButton = (JButton)e.getSource();
      GridPlayable.GameState state = game.getGameState(); // Saves the game state
      int x = (pressedButton.getX()-((pressedButton.getX()/75)*75))%width; // Converts coordinates into usable numbers
      int y = (pressedButton.getY()-((pressedButton.getY()/75)*75))%height; // "                                     "
      switch(state)
      {
        case TURN_P1:
        {
          if(game.getPressableAt(x, y))
          {
            status.setMessage("PLAYER 2'S TURN!");// Updates the status
            game.press(x, y);
            for(int i = 0; i < width; i++)
            {
              for(int j = 0; j < height; j++)
              {
                if(game.getPressableAt(i, j) == false)
                {
                  buttonGrid[i][j].setForeground(game.getColorAt(i, j)); // Sets the text color
                  buttonGrid[i][j].setText(game.getLabelAt(i, j)); // Sets the proper label
                }
              }
            }
          }
          if(game.getGameState() == GridPlayable.GameState.WIN_P1)
          {status.setMessage("PLAYER 1 WINS!");} // Updates the status
          else if(game.getGameState() == GridPlayable.GameState.LOSE)
          {status.setMessage("GAME OVER!");} // Updates the status
          break;
        }
        case TURN_P2:
        {
          if(game.getPressableAt(x, y))
          {
            status.setMessage("PLAYER 1'S TURN!"); // Updates the status
            game.press(x, y);
            for(int i = 0; i < width; i++)
            {
              for(int j = 0; j < height; j++)
              {
                if(game.getPressableAt(i, j) == false)
                {
                  buttonGrid[i][j].setForeground(game.getColorAt(i, j));
                  buttonGrid[i][j].setText(game.getLabelAt(i, j));
                }
              }
            }
          }
          if(game.getGameState() == GridPlayable.GameState.WIN_P2)
          {status.setMessage("PLAYER 2 WINS!");} // Updates the status
          else if(game.getGameState() == GridPlayable.GameState.LOSE)
          {status.setMessage("GAME OVER!");} // Updates the status
          break;
        }
        case WIN_P1:
        {
          GridPlayer.this.dispatchEvent(new WindowEvent(GridPlayer.this, WindowEvent.WINDOW_CLOSING)); // Closes the game on next click
          break;
        }
        case WIN_P2:
        {
          GridPlayer.this.dispatchEvent(new WindowEvent(GridPlayer.this, WindowEvent.WINDOW_CLOSING)); // Closes the game on next click
          break;
        }
        case LOSE:
        {
          GridPlayer.this.dispatchEvent(new WindowEvent(GridPlayer.this, WindowEvent.WINDOW_CLOSING)); // Closes the game on next click
          break;
        }
      }
    }

    public void mousePressed(MouseEvent e) // Required methods for the MouseListener implementation
    {}

		public void mouseReleased(MouseEvent e) // Required methods for the MouseListener implementation
    {}

		public void mouseEntered(MouseEvent e) // Required methods for the MouseListener implementation
    {}

		public void mouseExited(MouseEvent e) // Required methods for the MouseListener implementation
    {}
  }

  /**
    Gives messages to the user. Tells whose turn it is and when the game is over.
  **/
  private class StatusBar extends JLabel
  {
    public StatusBar()
    {
      super();
      super.setPreferredSize(new Dimension(50, 50));
      this.setFont(new Font("Serif", Font.PLAIN, 20));
      setMessage("PLAYER 1'S TURN!");
    }

    public void setMessage(String message) {
      setText(message);
    }
  }
}
