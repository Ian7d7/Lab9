import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
  * Displays a GUI for selecting from three GridPlayable games
  */
public class GridGameLauncher extends JFrame
{
  private JFrame window;
  private JButton ticTacToe;
  private JButton connectFour;
  private JButton minesweeper;

  /**
    * Constructs a window with three buttons
    * Sets the proper action commands
    * Formats the buttons and window
    */
  public GridGameLauncher(String name)
  {
    super(name);
    this.setLayout(new FlowLayout());
    this.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent windowevent)
      {System.exit(0);}
    });
    this.setVisible(true);

    this.ticTacToe = new JButton("Tic-Tac-Toe");
    this.connectFour = new JButton("Connect Four");
    this.minesweeper = new JButton("Minesweeper");

    this.add(this.ticTacToe);
    this.add(this.connectFour);
    this.add(this.minesweeper);

    this.ticTacToe.setActionCommand("TicTacToe");
    this.connectFour.setActionCommand("ConnectFour");
    this.minesweeper.setActionCommand("Minesweeper");

    this.addListeners();
    this.setResizable(false);
    this.pack();
  }

  /**
    * Adds listeners to each of the buttons
    */
  private void addListeners()
  {
    ButtonListener listener = new ButtonListener();
    this.ticTacToe.addActionListener(listener);
    this.connectFour.addActionListener(listener);
    this.minesweeper.addActionListener(listener);
  }

  /**
    * Opens a GridPlayer for each specific game when the button is pressed.
    */
  private class ButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if(e.getActionCommand().equals("TicTacToe"))
      {
        TicTacToe ticTacToe = new TicTacToe();
        GridPlayer player = new GridPlayer(ticTacToe);
      }
      else if(e.getActionCommand().equals("ConnectFour"))
      {
        ConnectFour connectFour = new ConnectFour();
        GridPlayer player = new GridPlayer(connectFour);
      }
      else if(e.getActionCommand().equals("Minesweeper"))
      {
        Minesweeper minesweeper = new Minesweeper();
        GridPlayer player = new GridPlayer(minesweeper);
      }
    }
  }

  public static void main(String[] args)
  {GridGameLauncher launcher = new GridGameLauncher("Game Launcher");} //Constructs a game Launcher and runs the entire program
}
