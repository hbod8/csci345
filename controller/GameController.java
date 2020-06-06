package controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
    this.board.paintActions(this.curPlayer);
    List<Room> roomList = new ArrayList<Room>(this.game.getRoomMap().values());
    this.board.paintAllScenes(roomList);
    this.board.paintPlayer(this.curPlayer);
  }

  public void move() {
    /* move player */
    if (curPlayer.hasMoved() || curPlayer.hasActed()) {
      displayMessage("You have alread moved this turn!");
    } else {
      String desiredRoomName = board.selectionBox("Where do you want to move?", curPlayer.getRoom().getAdjRooms());
      curPlayer.hasMoved(curPlayer.move(desiredRoomName, game.getRoomMap().get(desiredRoomName)));
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
    String selectedRoleName = board.selectionBox("What role?", roleNames);
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
      // check if you can act in rooms
      if (!(curPlayer.getRoom() instanceof SetRoom)) {
        displayMessage("Player is not in a room that they can act in.");
        return;
      }
      if (((SetRoom)curPlayer.getRoom()).hasShots()) {
        displayMessage("Whoops no more shots");
        return;
      }
      if (curPlayer.act()) {
        displayMessage("Success!");
        board.paintScene(((SetRoom)curPlayer.getRoom()));
        endTurn();
      } else {
        /* Failed roll */
        displayMessage("Try again next time!");
      }
    }
    /* make sure scene is visible and display it accordingly */
    board.paintActions(curPlayer);
    board.paintPlayer(curPlayer);
  }

  public void reherse() {
    if (!(curPlayer.getRoom() instanceof SetRoom)) {
      displayMessage("You can only reherse in a set room!");
      return;
    }
    if (((SetRoom)curPlayer.getRoom()).getScene().getBudget() == (curPlayer.getTokens() + 1)) {
      displayMessage("Cannot rehearse anymore, must act.");
      return;
    }
    curPlayer.rehearse();
    endTurn();
  }

  public void upgrade() {
    List<String> ranks = Arrays.asList(new String[]{"2", "3", "4", "5", "6"});
    List<String> payments = Arrays.asList(new String[]{"Credits", "Dollars"});
    int desiredLevel = Integer.parseInt(board.selectionBox("What rank do you want?", ranks));
    String paymentMethod = board.selectionBox("Credits or Dollars?", payments);
    boolean success = false;
    if(paymentMethod.equals("credits")) {
      success = curPlayer.upgrade(desiredLevel, true);
    } else if (paymentMethod.equals("dollars")) {
      success = curPlayer.upgrade(desiredLevel, false);
    }
    if(!success) {
      displayMessage("Could not upgrade.");
    }
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