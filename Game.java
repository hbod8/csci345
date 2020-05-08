import java.util.LinkedList;
import javax.xml.*;
import java.util.Scanner;

public class Game {
  private LinkedList<Player> players;
  private int days;
  private LinkedList<Room> rooms;
  private Scanner s;
  public Game(int playerCount) throws Exception {
    if (playerCount)

  }
  public static void main(String[] args) {
    s = new Scanner(System.in);
    System.out.println("Welcome to Deadwood!");
    System.out.print("How many players? >");
    int playerCount = s.nextInt();
    System.out.print("\n");
    if (players > 8 || players < 2) {
      throw new Exception("Error: you can only have 2-8 players.");
    }
    Game activeGame = new Game(playerCount);
  }

}