import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controller.GameController;

/**
 * This class represents the enire game of Deadwood.
 * 
 * @author Harry Saliba
 * @author Thomas Bidinger
 */
public class Deadwood {
  /**
   * Main entry point for Deadwood.  Creates instance of Model-View-Controller.
   * 
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    System.out.println("Starting Deadwood...");
    int players = Integer.parseInt(JOptionPane.showInputDialog(null, "How many players?"));

    List<String> playerNames = new ArrayList<>(2);
    for(int i = 1; i <= players; i++) {
      playerNames.add(JOptionPane.showInputDialog(null, "Player " + i + " name?")); 
    }

    /* Create Instance of Controller. */
    // GameController gameController = new GameController(players, playerNames);
    new GameController(players, playerNames);
  }
}