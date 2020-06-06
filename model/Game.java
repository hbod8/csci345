package model;

import java.util.List;
import java.util.Map;

import controller.GameController;

import java.util.Collections;
import java.util.LinkedList;

public class Game {
  /* Controller instance */
  GameController gameController;

  private List<Player> players = new LinkedList<Player>();
  private int day = 1;
  private int maxDays = 4;
  private Map<String, Room> rooms = XMLUtility.parseRoomsFromXML();
  private List<Scene> deck = XMLUtility.parseScenesFromXML();

  public Game(int playerCount, List<String> playerNames, GameController gameController) throws Exception {
    this.gameController = gameController;
    int startingCredits = 0;
    int startingRank = 1;
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
      this.players.add(new Player(startingCredits, startingRank, rooms.get("trailer")));
    }
    for (int i = 0; i < playerCount; i++) {
      this.players.get(i).setName(playerNames.get(i));
    }
    this.finishSetup();
  }

  /**
   * Gets number of scenes on the board.
   * @return int scenes
   */
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
        ((SetRoom)room).resetShots();
      }
    }
  }

  /**
   * Gets Map Roomname:Room
   * @return room Map
   */
  public Map<String, Room> getRoomMap() {
    return this.rooms;
  }

  /**
   * All players
   * @return List of players
   */
  public List<Player> getPlayers() {
    return this.players;
  }

  /**
   * Gets day
   * @return int day
   */
  public int getDay() {
    return this.day;
  }

  /**
   * Gets maximum number of days
   * @return int max days
   */
  public int getMaxDays() {
    return this.maxDays;
  }

  /**
   * Increments day
   */
  public void nextDay() {
    this.day++;
  }
}