import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.lang.Exception;

public class Game {
  private LinkedList<Player> players;
  private int days;
  private int maxDays = 4;
  private Map<String, Room> rooms;
  private static Scanner s;
  public Game(int playerCount) {
    for (int i = 0; i < playerCount; i++) {
      this.players.push(new Player(rooms.getFirst()));
    }
  }
  public Game(int playerCount, int maxDays) {
    this.maxDays = maxDays;
    for (int i = 0; i < playerCount; i++) {
      this.players.push(new Player(rooms.getFirst()));
    }
  }
  public Game(int playerCount, int maxDays, int startingCredits) {
    this.maxDays = maxDays;
    for (int i = 0; i < playerCount; i++) {
      this.players.push(new Player(startingCredits, rooms.removeFirst()));
    }
  }
  public Game(int playerCount, int maxDays, int startingCredits, int startingRank) {
    this.maxDays = maxDays;
    for (int i = 0; i < playerCount; i++) {
      this.players.push(new Player(startingCredits, startingRank, rooms.removeFirst()));
    }
  }
  public static void main(String[] args) throws Exception {
    s = new Scanner(System.in);
    System.out.println("Welcome to Deadwood!");
    System.out.print("How many players? > ");
    int playerCount = s.nextInt();
    System.out.print("\n");
    if (playerCount > 8 || playerCount < 2) {
      throw new Exception("Error: you can only have 2-8 players.");
    }
    Game activeGame;
    if (playerCount <= 3) {
      activeGame = new Game(playerCount, 3);
    } else if (playerCount <= 4) {
      activeGame = new Game(playerCount);
    } else if (playerCount <= 5) {
      activeGame = new Game(playerCount, 4, 2);
    } else if (playerCount <= 6) {
      activeGame = new Game(playerCount, 4, 4);
    } else if (playerCount <= 8) {
      activeGame = new Game(playerCount, 4, 0, 2);
    }
    // @TODO: Loop and get player names!
    //Loop for game

  }
  public Map<String, Room> getRooms() {
    return this.rooms;
  }
}