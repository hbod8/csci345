import javax.swing.JOptionPane;

import controller.GameController;
import model.Game;
import view.Board;

public class Deadwood {
  public static void main(String[] args) {
    System.out.println("Starting Deadwood...");
    int players = Integer.parseInt(JOptionPane.showInputDialog(null, "How many players?"));

    /* Create Instance of Controller. */
    GameController gameController = new GameController(players);
  }
}