package model;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.LinkedList;

public class Game {
  private List<Player> players = new LinkedList<Player>();
  private int days = 0;
  private int maxDays = 4;
  private Map<String, Room> rooms = XMLUtility.parseRoomsFromXML();
  private List<Scene> deck = XMLUtility.parseScenesFromXML();

  public Game(int playerCount) throws Exception {
    int startingCredits = 0;
    int startingRank = 0;
    if (playerCount > 1 && playerCount <= 2) {
      this.maxDays = 3;
    } else if (playerCount == 5) {
      startingCredits = 2;
    } else if (playerCount == 6) {
      startingCredits = 4;
    } else if (playerCount > 6 && playerCount < 9) {
      startingRank = 2;
    } else if (playerCount < 2 || playerCount > 8) {
      throw new Exception("Failed to create game.");
    }
    for (int i = 0; i < playerCount; i++) {
      this.players.add(new Player(startingCredits, startingRank, rooms.get("office")));
    }
    this.finishSetup();
  }

  public int getScenesOnBoard() {
    int scenes = 0;
    for (Room r : this.rooms.values()) {
      if ((r instanceof SetRoom) && (((SetRoom) r).getScene() != null)) {
        scenes++;
      }
    }
    return scenes;
  }

  private void finishSetup() {
    placeScenes();
    System.out.printf("Started game with %d players.\n", this.players.size());
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

  // aquires and sets player names
  /* private void setPlayerNames() {
    int i = 1;
    for (Player curPlayer : players) {
      System.out.print("Enter player " + i + " name > ");
      String playerName = in.next();
      curPlayer.setName(playerName);
      i++;
    }
    clearScreen();
  } */

  // loops player turns
  /* private void gameLoop() {
    boolean gameEnded = false;
    while (!gameEnded) {
      Player curPlayer = this.players.get(0);
      while (this.getScenesOnBoard() > 1) {
        System.out.println(curPlayer.getName() + "'s turn!");
        boolean endOfTurn = false;
        while (!endOfTurn) {
          boolean hasMoved = false;
          boolean hasActed = false;

          printInfo(curPlayer);

          System.out.println(
              "Which action " + curPlayer.getName() + "?\n >move\n >take role\n >act\n >rehearse\n >upgrade\n >end");

          String action = in.nextLine();
          clearScreen();

          switch (action) {
            case "move":
              if (hasMoved) {
                System.out.println("You already moved!");
              } else if (hasActed) {
                System.out.println("You already acted!");
              } else {
                System.out.println("Where would you like to move?");
                for (String adjRoom : curPlayer.getRoom().getAdjRooms()) {
                  System.out.println(" >" + adjRoom);
                }

                String desiredRoomName = in.nextLine();
                hasMoved = curPlayer.move(desiredRoomName, this.rooms.get(desiredRoomName));
              }
              break;
            case "take role":
              if (!(curPlayer.getRoom() instanceof SetRoom)) {
                System.out.println("Whoops you cannot take a role in the trailer or office!");
                break;
              }
              System.out.println("Possible roles:");
              List<Role> listOfRoles = curPlayer.getRoles();
              for (int i = 0; i < listOfRoles.size(); i++) {
                System.out.printf(" #%d : %s with a required rank of %d\n", i, listOfRoles.get(i).getName(),
                    listOfRoles.get(i).getRank());
              }
              System.out.println("Which number role do you want?");
              int desiredNumRole = in.nextInt();
              while (desiredNumRole < 0 || desiredNumRole > listOfRoles.size() - 1) {
                System.out.println("Seems like thats not a number listed... try again.");
                desiredNumRole = in.nextInt();
              }
              in.nextLine();
              Role desiredRole = listOfRoles.get(desiredNumRole);
              desiredRole.take(curPlayer);
              // role.take()
              break;
            case "act":
              if (hasMoved) {
                System.out.println("You already moved!");
              } else if (hasActed) {
                System.out.println("You already acted!");
              } else {
                endOfTurn = curPlayer.act();
              }
              break;
            case "rehearse":
              endOfTurn = curPlayer.rehearse();
              break;
            case "upgrade":
              // upgrade rank
              System.out.println("What rank do you want?");
              int desiredLevel = in.nextInt();
              System.out.println("Do you want to use credits or dollars?");
              String paymentMethod;
              boolean selectedMethod = false;
              boolean success = false;
              while (!selectedMethod) {
                paymentMethod = in.nextLine();
                if (paymentMethod.equals("credits")) {
                  success = curPlayer.upgrade(desiredLevel, true);
                  selectedMethod = true;
                } else if (paymentMethod.equals("dollars")) {
                  success = curPlayer.upgrade(desiredLevel, false);
                  selectedMethod = true;
                } else {
                  System.out.println("Sorry we dont accept that payment method here... \"credits\" or \"dollars\"?");
                  paymentMethod = in.nextLine();
                }
              }
              if (hasMoved && success)
                endOfTurn = true;
              break;
            case "end":
              endOfTurn = true;
              break;
          }
        }
        if (this.players.indexOf(curPlayer) < this.players.size() - 1) {
          curPlayer = this.players.get(this.players.indexOf(curPlayer) + 1);
        } else {
          curPlayer = this.players.get(0);
        }
      }
      if (this.days <= this.maxDays) {
        // endGame()
        gameEnded = true;
      } else {
        this.days++;
      }
    }
    System.out.println("Seems like you made it far enough testing our program, heres a gold star.");
  } */

  // prints all player info
  /* private void printInfo(Player curPlayer) {
    System.out.println("Day " + days + " of " + maxDays);
    System.out.println("Location: " + curPlayer.getRoom().getName());
    System.out.println("Rank: " + curPlayer.getRank());

    if (curPlayer.onRole()) {
      System.out.println("Role: " + curPlayer.getRole().getName());
    } else {
      System.out.println("Role: ----");
    }

    System.out.println("Cash: " + curPlayer.getDollars());
    System.out.println("Credits: " + curPlayer.getCredits());
    System.out.println("Rehearse tokens: " + curPlayer.getTokens() + "\n");

  } */

  /* public static void main(String[] args) throws Exception {
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
  } */
}