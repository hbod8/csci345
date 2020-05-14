import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Exception;

public class Game {
  private static LinkedList<Player> players;
  private int days;
  private int maxDays;
  private LinkedList<Room> rooms;
  private static Scanner s;
  public Game(int playerCount) throws Exception {
    if (playerCount < 4) {
      this.maxDays = 3;
      for (int i = 0; i < playerCount; i++) {
        this.players.push(new Player());
      }
    }

  }
  public static void main(String[] args) throws Exception {
    s = new Scanner(System.in);
    System.out.println("Welcome to Deadwood!");
    System.out.print("How many players? > ");
    int playerCount = s.nextInt();
    System.out.print("\n");
    if (players.size() > 8 || players.size() < 2) {
      throw new Exception("Error: you can only have 2-8 players.");
    }
    Game activeGame = new Game(playerCount);
  }
  public LinkedList<Room> getRooms() {
    return this.rooms;
  }
}