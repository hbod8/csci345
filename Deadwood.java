import javax.swing.JOptionPane;

import controller.GameController;
import model.Game;
import view.Board;

public class Deadwood {
  public static void main(String[] args) {
    System.out.println("Starting Deadwood...");
    int players = Integer.parseInt(JOptionPane.showInputDialog(null, "How many players?"));

    /* Create instances of Models. */
    Game game = null;
    try {
      game = new Game(players);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    /* Create Instances of Controllers. */
    GameController gameController = new GameController(game);

    /* Create instances of Views. */
    Board gameBoard = new Board(game, gameController);
    gameBoard.setVisible(true);
  }
}