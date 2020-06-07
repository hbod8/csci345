package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import controller.GameController;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Game represents a Deadwood game model.  It has many responsibilies,
 * some of which include players, rooms, and the deck of cards.
 * 
 * @author Harry Saliba
 * @author Thomas Bidinger
 */
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
    if (playerCount > 1 && playerCount <= 3) {
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

  /**
   * Places scenes on board from deck randomly.
   */
  public void placeScenes() {
    Collections.shuffle(deck);
    for (Room room : rooms.values()) {
      if (room instanceof SetRoom) {
        ((SetRoom)room).setScene(deck.remove(0));
        ((SetRoom)room).resetShots();
      }
    }
  }

  /**
   * Gives bonus payout at end of a scene
   */
  public void bonusPayout(SetRoom curRoom) {
    Random r = new Random();

    //rolls the dice
    int diceNum = curRoom.getBudget();
    List<Integer> diceRolls = new ArrayList<>();
    for(int i = 0; i < diceNum; i++) {
      diceRolls.add(r.nextInt(6)+1);
    }
    Collections.sort(diceRolls);
    Collections.reverse(diceRolls);

    //gets players in the room
    List<Player> playersInRoom = new ArrayList<>();
    List<Player> playersOnCard = new ArrayList<>();
    List<Player> playersOffCard = new ArrayList<>();
    for (Player curPlayer : players) {
      if(((SetRoom)(curPlayer.getRoom())) == curRoom) {
        playersInRoom.add(curPlayer);
      }
    }

    //check if a player was on the scene
    boolean anyoneOnScene = false;
    for(Player curPlayer : playersInRoom) {
      if(curPlayer.getRole().getInScene()) {
        anyoneOnScene = true;
        playersOnCard.add(curPlayer);
      }else {
        playersOffCard.add(curPlayer);
      }
    }

    //give payout if someone was on the scene
    if(anyoneOnScene) {
      int wrap = 0;
      for(Integer i : diceRolls) {
        playersOnCard.get(wrap).setDollars(playersOnCard.get(wrap).getDollars() + i);
        if(wrap == playersOnCard.size() - 1) {
          wrap = 0;
        }else {
          wrap++;
        }
      }
      for(Player curPlayer : playersOffCard) {
        curPlayer.setDollars(curPlayer.getDollars() + curPlayer.getRole().getRank());
      }
    }
  }
  
  /**
   * Creates a map of players and their final scores.
   * 
   * @return Map of players ansd their scores sorted in decending order.
   */
  public Map<Player, Integer> getScores() {
    Map<Player, Integer> map = new LinkedHashMap<Player, Integer>();
    for (Player p : this.players) {
      map.put(p, p.calculateScore());
    }
    return sortByValue(map);
  }

  /* Online implementation of map sort using entries to keep track of key-value pairs */
  private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
    list.sort(Entry.comparingByValue());

    Map<K, V> result = new LinkedHashMap<>();
    for (Entry<K, V> entry : list) {
        result.put(entry.getKey(), entry.getValue());
    }

    return result;
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