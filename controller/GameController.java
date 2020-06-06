package controller;

import java.util.List;
import java.util.ArrayList;

import model.Game;
import model.Player;
import model.Role;
import model.SetRoom;
import model.Room;
import view.Board;

public class GameController {

  /* Model instance */
  private Game game;

  /* View instance */
  private Board board;

  /* current player */
  private Player curPlayer;

  public GameController(int players, List<String> playerNames) {
    this.game = null;
    /* Create instance of Model. */
    try {
      this.game = new Game(players, playerNames, this);
      this.curPlayer = this.game.getPlayers().get(0);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    /* Create instance of View. */
    this.board = new Board(this);
    this.board.setVisible(true);
    /* Display player */
    System.out.println(this.curPlayer);
    this.board.paintPlayer(this.curPlayer);
    this.board.paintActions(this.curPlayer);
    List<Room> roomList = new ArrayList<Room>(this.game.getRoomMap().values());
    this.board.paintAllScenes(roomList);
  }

  public void move() {
    /* move player */
    if (curPlayer.hasMoved() || curPlayer.hasActed()) {
      displayMessage("You have alread moved this turn!");
    } else {
      String desiredRoomName = board.moveOptions(curPlayer.getRoom().getAdjRooms());
      curPlayer.hasMoved(curPlayer.move(desiredRoomName, game.getRoomMap().get(desiredRoomName)));
      //System.out.println(desiredRoomName);
      if(curPlayer.getRoom() instanceof SetRoom) {
        ((SetRoom)curPlayer.getRoom()).getScene().setVisible(true);
        board.paintScene(((SetRoom)curPlayer.getRoom()));
      }
    }
    /* make sure scene is visible and display it accordingly */
    board.paintActions(curPlayer);
    board.paintPlayer(curPlayer);
  }

  public void takeRole() {
    if (!(curPlayer.getRoom() instanceof SetRoom)) {
      displayMessage("Whoops you cannot take a role in the trailer or office!");
    }
    /* get list of roles + list of role names*/
    List<Role> listOfRoles = curPlayer.getRoles();
    List<String> roleNames = new ArrayList<String>();
    for (Role curRole : listOfRoles) {
      roleNames.add(curRole.getName());
    }
    /* Display options + get selected role*/
    String selectedRoleName = board.takeRoleOptions(roleNames);
    for (Role r : listOfRoles) {
      if(r.getName().equals(selectedRoleName)) {
        if(!(curPlayer.takeRole(r))) {
          displayMessage("Sorry bud, you need a little more experience for that role.");
        }
      }
    }
    /* make sure scene is visible and display it accordingly */
    board.paintActions(curPlayer);
    board.paintPlayer(curPlayer);
  }

  public void act() {
    if (curPlayer.hasMoved()) {
      displayMessage("You already moved!");
    } else if (curPlayer.hasActed()) {
      displayMessage("You already acted!");
    } else {
      if (curPlayer.act()) {
        endTurn();
      } else {
        /* err */
      }
    }
    /* make sure scene is visible and display it accordingly */
    board.paintActions(curPlayer);
    board.paintPlayer(curPlayer);
  }

  public void reherse() {
    curPlayer.rehearse();
    endTurn();
  }

  public void upgrade() {
    /* upgrade rank */
    // displayMessage("What rank do you want?");
    // int desiredLevel = in.nextInt();
    // displayMessage("Do you want to use credits or dollars?");
    // String paymentMethod;
    // boolean selectedMethod = false;
    // boolean success = false;
    // while (!selectedMethod) {
    //   paymentMethod = in.nextLine();
    //   if (paymentMethod.equals("credits")) {
    //     success = curPlayer.upgrade(desiredLevel, true);
    //     selectedMethod = true;
    //   } else if (paymentMethod.equals("dollars")) {
    //     success = curPlayer.upgrade(desiredLevel, false);
    //     selectedMethod = true;
    //   } else {
    //     displayMessage("Sorry we dont accept that payment method here... \"credits\" or \"dollars\"?");
    //     paymentMethod = in.nextLine();
    //   }
    // }
    endTurn();
  }

  public void endTurn() {
    this.curPlayer.hasActed(false);
    this.curPlayer.hasMoved(false);
    /* Check if game is over. */
    checkGameEnd();
    /* Update current player */
    if (this.game.getPlayers().indexOf(curPlayer) < this.game.getPlayers().size() - 1) {
      curPlayer = this.game.getPlayers().get(this.game.getPlayers().indexOf(curPlayer) + 1);
    } else {
      curPlayer = this.game.getPlayers().get(0);
    }
    /* Update actions */
    board.paintActions(curPlayer);
    /* Display player */
    this.board.paintPlayer(curPlayer);
  }

  private void checkGameEnd() {
    /* Check if game is over. */
    if (this.game.getDay() < this.game.getMaxDays()) {
      // endGame()
    } else {
      this.game.nextDay();
    }
  }

  private void displayMessage(String message) {
    board.displayMessage(message);
  }

  public int getDay() {
    return game.getDay();
  }
}