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
        ((SetRoom)room).setShots(3);
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
    clearScreen();    
  }

  //loops player turns
  private void gameLoop() {
      //while(getScenesOnBoard() > 1) {
        Player curPlayer = this.players.get(0);
        while (this.getScenesOnBoard() > 1) {
          System.out.println(curPlayer.getName() + "'s turn!");
          printInfo(curPlayer);

          System.out.println("Which action?\n >move\n >act\n >rehearse\n >upgrade\n >end");

          String action = in.next();
          clearScreen();

          switch (action) {
            case "move":
              break;
            case "act":
              break;
            case "rehearse":
              break;
            case "upgrade":
              break;
            case "end":
              break;

          }
          if (this.players.indexOf(curPlayer) < this.players.size() - 1) {
            curPlayer = this.players.get(this.players.indexOf(curPlayer) + 1);
          } else {
            curPlayer = this.players.get(0);
          }
        }
        if (this.days <= this.maxDays) {
          //endGame()
        } else {
          this.days++;
        }
  }

  //prints all player info
  private void printInfo(Player curPlayer) {
    System.out.println("Day " + days + " of " + maxDays);
    System.out.println("Location: " + curPlayer.getRoom().getName());
    System.out.println("Rank: " + curPlayer.getRank());
    
    System.out.print("Role: ");
    if(curPlayer.getRoom() instanceof SetRoom) {
      for(Role curRole : ((SetRoom)(curPlayer.getRoom())).getExtras()) {
        if(curPlayer == curRole.getPlayer()) {
          System.out.println(curRole.getName() + " in the " + curPlayer.getRoom().getName());
        }
      }

      if(((SetRoom)(curPlayer.getRoom())).getScene() != null) {
        for(Role curRole : ((SetRoom)(curPlayer.getRoom())).getScene().getRoles()) {
          if(curPlayer == curRole.getPlayer()) {
            System.out.println(curRole.getName() + " in the " + curPlayer.getRoom().getName());
          }
        }
      }
    }else {
      System.out.println(curPlayer.getRoom().getName());
    }

    System.out.println("Cash: " + curPlayer.getDollars());
    System.out.println("Credits: " + curPlayer.getCredits());
    System.out.println("Rehearse tokens: " + curPlayer.getTokens());

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

  //clears the console
  public static void clearScreen() {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
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