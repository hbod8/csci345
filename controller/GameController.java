package controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import model.Game;
import model.Player;
import model.Role;
import model.SetRoom;
import model.Room;
import model.Area;
import model.Scene;
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
    /* Paint all rooms and scenes */
    List<Room> roomList = new ArrayList<Room>(this.game.getRoomMap().values());
    this.board.paintAllScenes(roomList);
    /* Paint all players */
    for (Player p : game.getPlayers()) {
      this.board.paintPlayer(p);
    }
    /* Paint the current player */
    this.board.paintPlayer(this.curPlayer);
    /* Paint actions */
    this.board.paintActions(this.curPlayer);
  }


  public void move() {
    /* move player */
    if (curPlayer.hasMoved() || curPlayer.hasActed()) {
      displayMessage("You have alread moved or acted this turn!");
    } else {
      /* Make sure the player dosent have a role */
      curPlayer.freeRole();
      /* Display list of adjacent rooms */
      String desiredRoomName = board.selectionBox("Where do you want to move?", curPlayer.getRoom().getAdjRooms());
      /* Move and update has moved */
      curPlayer.move(game.getRoomMap().get(desiredRoomName));
      curPlayer.hasMoved(true);
      if(curPlayer.getRoom() instanceof SetRoom && ((SetRoom)curPlayer.getRoom()).getScene() != null) {
        /* Make sure scene is visible  */
        ((SetRoom)curPlayer.getRoom()).getScene().setVisible(true);
        board.paintScene(((SetRoom)curPlayer.getRoom()));
      }
    }
    /* Update actions and player location */
    board.paintActions(curPlayer);
    board.paintPlayer(curPlayer);
  }

  public void takeRole() {
    /* Make sure they are in a set room */
    if (!(curPlayer.getRoom() instanceof SetRoom)) {
      displayMessage("Whoops, you cannot take a role in the trailer or office!");
      return;
    }
    /* Make sure there is still a movie being filmed */
    if (((SetRoom)curPlayer.getRoom()).getScene() == null) {
      displayMessage("Whoops, looks like the scene has ended filming for the day.");
      return;
    }
    /* Get list of roles and convert too list of role names */
    List<String> roleNames = new ArrayList<String>();
    for (Role curRole : curPlayer.getRoles()) {
      roleNames.add(curRole.getName());
    }
    /* Display possible roles and get selected role */
    String selectedRoleName = board.selectionBox("What role?", roleNames);
    for (Role r : curPlayer.getRoles()) {
      if(r.getName().equals(selectedRoleName)) {
        if(curPlayer.getRank() < r.getRank()) {
          displayMessage("Sorry bud, you need a little more experience for that role.");
          return;
        } else {
          curPlayer.takeRole(r);
        }
      }
    }
    /* Update player information in view and location */
    board.paintActions(curPlayer);
    board.paintPlayer(curPlayer);
    endTurn();
  }

  public void act() {
    /* Make sure they qualify for acting that turn */
    if (curPlayer.hasMoved()) {
      displayMessage("You already moved!");
      return;
    }
    if (curPlayer.hasActed()) {
      displayMessage("You already acted!");
      return;
    }
    /* Make sure player is in set room */
    if (!(curPlayer.getRoom() instanceof SetRoom)) {
      displayMessage("Player is not in a set room.");
      return;
    }
    /* Make sure there is still a shoot happening. */
    if (((SetRoom)curPlayer.getRoom()).getScene() == null) {
      displayMessage("Whoops looks like the set is closed for the day.");
      return;
    }
    if (curPlayer.act()) {
      displayMessage("Success!");
      board.paintScene(((SetRoom)curPlayer.getRoom()));
    } else {
      /* Failed roll */
      displayMessage("Try again next time!");
    }
    /* make sure scene is visible and display it accordingly */
    curPlayer.hasActed(true);
    board.paintActions(curPlayer);
    board.paintPlayer(curPlayer);
    board.paintScene((SetRoom)curPlayer.getRoom());
  }

  public void reherse() {
    /* Check if player is in a room with roles*/
    if (!(curPlayer.getRoom() instanceof SetRoom)) {
      displayMessage("You can only reherse in a set room!");
      return;
    }
    /* Make sure there is still a shoot happening. */
    if (((SetRoom)curPlayer.getRoom()).getScene() == null) {
      displayMessage("Whoops looks like the set is closed for the day.");
      return;
    }
    
    /* Check if player is on a role */
    if (!curPlayer.onRole()){
      displayMessage("You are not on a role!");
      return;
    }
    
    /* Check if player has max rehearsal tokens */
    if (((SetRoom)curPlayer.getRoom()).getScene().getBudget() == (curPlayer.getTokens() + 1)) {
      displayMessage("Cannot rehearse anymore, must act.");
      return;
    }
    
    /* Finally, reherse */
    curPlayer.rehearse();
    endTurn();
  }

  public void upgrade() {
    /* Display upgrade options */
    List<String> ranks = Arrays.asList(new String[]{"2", "3", "4", "5", "6"});
    /* Get selected option */
    int desiredLevel = Integer.parseInt(board.selectionBox("What rank do you want?", ranks));
    /* Display payment options.  Did you just assume my tender? */
    List<String> payments = Arrays.asList(new String[]{"Credits", "Dollars"});
    /* Get selected option */
    String paymentMethod = board.selectionBox("Credits or Dollars?", payments);
    /* Attempt upgrade */
    boolean success = false;
    if (paymentMethod.equals("Credits")) {
      success = curPlayer.upgrade(desiredLevel, true);
    } else if (paymentMethod.equals("Dollars")) {
      success = curPlayer.upgrade(desiredLevel, false);
    }
    if(!success) {
      displayMessage("Could not upgrade.");
      return;
    }
    /* Update player info in view */
    board.paintActions(curPlayer);
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

  /* TESTING PUPOSES ONLY */
  public void test() {
    System.out.println("Test Room:");
    Room room = curPlayer.getRoom();
    System.out.printf("\t%s @(%d, %d) %d x %d:\n\t\tAdjacent Rooms:\n", room.getName(), room.getX(), room.getY(), room.getW(), room.getH());
    for (String adj : room.getAdjRooms()) {
      System.out.printf("\t\t\t%s\n", adj);
    }
    if (room instanceof SetRoom) {
      System.out.printf("\t\tShots:\n");
      for (Area area : ((SetRoom) room).getShotMap().values()) {
        System.out.printf("\t\t\t@(%d, %d) %d x %d\n", area.getX(), area.getY(), area.getW(), area.getH());
      }
      System.out.printf("\t\tRoles:\n");
      for (Role role : ((SetRoom) room).getExtras()) {
        System.out.printf("\t\t\t%s (%d) @(%d, %d) %d x %d\n", role.getName(), role.getRank(), role.getX(), role.getY(), role.getW(), role.getH());
      }
    }
    System.out.println("Test Scene:");
    if (curPlayer.getRoom() instanceof SetRoom && ((SetRoom)curPlayer.getRoom()).getScene() != null) {
      Scene scene = ((SetRoom)curPlayer.getRoom()).getScene();
      System.out.printf("\t%s (%d) %s:\n", scene.getName(), scene.getBudget(), scene.getImageName());
      for (Role role : scene.getRoles()) {
        System.out.printf("\t\t%s (%d) @(%d, %d) %d x %d\n", role.getName(), role.getRank(), role.getX(), role.getY(), role.getW(), role.getH());
      }
    }
  }
}