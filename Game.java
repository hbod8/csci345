import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.lang.Exception;
import java.util.Collections;
import java.util.LinkedList;

public class Game {
  private List<Player> players = new LinkedList<Player>();
  private int days;
  private int maxDays = 4;
  private Map<String, Room> rooms = new XMLUtility().getRoomsFromXML();
  private List<Scene> deck = new XMLUtility().getScenesFromXML();
  private Scanner in;

  public Game(Scanner in, int playerCount) {
    this.in = in;
    for (int i = 0; i < playerCount; i++) {
      this.players.add(new Player(rooms.get("office")));
    }
  }
  public Game(Scanner in, int playerCount, int maxDays) {
    this.in = in;
    this.maxDays = maxDays;
    for (int i = 0; i < playerCount; i++) {
      this.players.add(new Player(rooms.get("trailer")));
    }
  }
  public Game(Scanner in, int playerCount, int maxDays, int startingCredits) {
    this.in = in;
    this.maxDays = maxDays;
    for (int i = 0; i < playerCount; i++) {
      this.players.add(new Player(startingCredits, rooms.get("trailer")));
    }
  }
  public Game(Scanner in, int playerCount, int maxDays, int startingCredits, int startingRank) {
    this.in = in;
    this.maxDays = maxDays;
    for (int i = 0; i < playerCount; i++) {
      this.players.add(new Player(startingCredits, startingRank, rooms.get("trailer")));
    }
  }

  //entry point following contructor
  private void startGame() {
    placeScenes();
    setPlayerNames();
    gameLoop();
  }

  //shuffles deck and deals scenes to board
  private void placeScenes() {
    Collections.shuffle(deck);
    for (Room room : rooms.values()) {
      if (room instanceof SetRoom) {
        ((SetRoom)room).setScene(deck.remove(0));
      }
    }
  }

  //aquires and sets player names
  private void setPlayerNames() {
    int i = 1;
    for(Player curPlayer : players) {
      System.out.print("Enter player " + i +  " name > ");
      String playerName = in.next();
      curPlayer.setName(playerName);
      i++;
    }
    
  }

  //loops player turns
  private void gameLoop() {
    System.out.println(getScenesOnBoard());

      while(getScenesOnBoard() > 1) {
        for(Player curPlayer : players) {
          System.out.println(curPlayer.getName() + "'s turn!");
          printInfo(curPlayer);

          System.out.println("Wou");

        }
      }
      days++;
  }

  private void printInfo(Player curPlayer) {
    System.out.println("Day " + days + " of " + maxDays);
  }

  private int getScenesOnBoard() {
    int scenes = 0;
    for (Room r : this.rooms.values()) {
      if ((r instanceof SetRoom) && (((SetRoom)r).getScene() != null)) {
        scenes++;
      }
    }
    return scenes;
  }

  public static void main(String[] args) throws Exception {
    Scanner s = new Scanner(System.in);
    System.out.println("Welcome to Deadwood!");
    System.out.print("How many players? > ");
    int playerCount = s.nextInt();
    System.out.print("\n");
    if (playerCount > 8 || playerCount < 2) {
      s.close();
      throw new Exception("Error: you can only have 2-8 players.");
    }
    Game activeGame;
    if (playerCount <= 3) {
      activeGame = new Game(s, playerCount, 3);
    } else if (playerCount <= 4) {
      activeGame = new Game(s, playerCount, 4);
    } else if (playerCount <= 5) {
      activeGame = new Game(s, playerCount, 4, 2);
    } else if (playerCount <= 6) {
      activeGame = new Game(s, playerCount, 4, 4);
    } else if (playerCount <= 8) {
      activeGame = new Game(s, playerCount, 4, 0, 2);
    } else {
      s.close();
      throw new Exception("Failed to create game.");
    }
    activeGame.startGame();
  }
}