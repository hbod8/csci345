package model;

import java.util.Random;

import java.util.List;
import java.util.LinkedList;

/**
 * @author Harry Saliba
 * @author Thomas Bidinger
 */
public class Player {
  private int credits = 0;
  private int dollars = 0;
  private int practiceChips = 0;
  private int rank = 1;
  private boolean hasMoved = false;
  private boolean hasActed = false;
  private Room room;
  private String name;
  Random dice = new Random();

  private final int[][] upgradeCosts = {
    {4, 5},
    {10, 10},
    {18, 15},
    {28, 20},
    {40, 25}
  };

  public Player(int startCredits, int startRank, Room startRoom) {
    this.credits = startCredits;
    this.rank = startRank;
    this.room = startRoom;
  }

  /**
   * Performs acting
   * @return if successful
   */
  public boolean act() {
    int roll = 0;
    roll = dice.nextInt(6) + 1;
    boolean inscene = this.getRole().getInScene();
    
    // act...
    //successful roll
    if(roll + practiceChips >= ((SetRoom)this.room).getScene().getBudget()) {
      //decrement shot counter
      ((SetRoom)this.room).shoot();
      if (((SetRoom)this.room).getShots() < 1) {
        ((SetRoom)this.room).setBudget(((SetRoom)this.room).getScene().getBudget());
        ((SetRoom)this.room).setScene(null);
        this.freeRole();
      }
      
      //if on/off the card
      if(inscene) {
        this.credits = this.credits + 2;
      }else {
        this.dollars++;
        this.credits++;
      }
    //failed roll
    } else {
      if(!this.getRole().getInScene()) {
        this.dollars++;
      }
      return false;
    }
    return true;
  }

  /**
   * Increments rehearse tokens
   */
  public void rehearse() {
    this.practiceChips++;
  }

  /**
   * Moves player, resets rehearsals
   */
  public void move(Room newroom) {
    this.room = newroom;
    this.practiceChips = 0;
  }

  /**
   * Takes given role
   */
  public void takeRole(Role role) {
    role.take(this);
  }

  /**
   * Frees the role from player
   */
  public void freeRole() {
    if (this.room instanceof SetRoom) {
      for (Role r : this.getRoles()) {
        if (r.getPlayer() != null && r.getPlayer().equals(this)) {
          r.free();
        }
      }
    }
  }

  /**
   * Performs upgrading
   * @return if successful
   */
  public boolean upgrade(int level, boolean credits) {
    if (level > 6 || level < 2) {
      return false;
    }
    if (credits) {
      if (this.getCredits() >= upgradeCosts[level - 2][1]) {
        this.setCredits(this.getCredits() - upgradeCosts[level - 2][1]);
        this.rank = level;
      } else {
        return false;
      }
    } else {
      if (this.getDollars() >= upgradeCosts[level - 2][0]) {
        this.setDollars(this.getDollars() - upgradeCosts[level - 2][0]);
        this.rank = level;
      } else {
        return false;
      }
    }
    return true;
  }
  
  //returns true/false if on a role or not
  public boolean onRole() {
    if(this.room instanceof SetRoom) {
      for(Role curRole : ((SetRoom)this.room).getExtras()) {
        if(this == curRole.getPlayer()) {
          return true;
        }
      }
      if(((SetRoom)this.room).getScene() != null) {
        for(Role curRole : ((SetRoom)this.room).getScene().getRoles()) {
          if(this == curRole.getPlayer()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Get current role
   * @return Role player is on
   */
  public Role getRole() {
    if(this.room instanceof SetRoom) {
      for(Role curRole : ((SetRoom)this.room).getExtras()) {
        if(this == curRole.getPlayer()) {
          return curRole;
        }
      }
      if(((SetRoom)this.room).getScene() != null) {
        for(Role curRole :  ((SetRoom)this.room).getScene().getRoles()) {
          if(this == curRole.getPlayer()) {
            return curRole;
          }
        }
      }
    }
    return null;
  }
 
  /**
   * Get list of roles in room player is in
   * @return List of roles
   */
  public List<Role> getRoles() {
    List<Role> roles = new LinkedList<Role>();
    if(this.room instanceof SetRoom) {
      if(((SetRoom)this.room).getScene() != null) {
        for(Role curRole :  ((SetRoom)this.room).getScene().getRoles()) {
          roles.add(curRole);
        }
      }
      for(Role curRole : ((SetRoom)this.room).getExtras()) {
        roles.add(curRole);
      }
    }
    return roles;
  }

  /**
   * Calculates the score of the given players
   */
  public int calculateScore() {
    return (this.getRank() * 5) + this.rank + this.dollars;
  }

  /**
   * @return the credits
   */
  public int getCredits() {
    return credits;
  }
  /**
   * @return the dollars
   */
  public int getDollars() {
    return dollars;
  }
  /**
   * @return the rank
   */
  public int getRank() {
    return rank;
  }
  /**
   * @return the room
   */
  public Room getRoom() {
    return room;
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * gets rehearsals
   * @return rehearsal tokens
   */
  public int getTokens() {
    return this.practiceChips;
  }
  /**
   * @param credits the credits to set
   */
  public void setCredits(int credits) {
    this.credits = credits;
  }
  /**
   * @param dollars the dollars to set
   */
  public void setDollars(int dollars) {
    this.dollars = dollars;
  }
  /**
   * @param rank the rank to set
   */
  public void setRank(int rank) {
    this.rank = rank;
  }
  /**
   * @param room the room to set
   */
  public void setRoom(Room room) {
    this.room = room;
  }
  /**
   * @param name the name to be set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Whether or not player has moved this turn
   * @return if player has moved
   */
  public boolean hasMoved() {
    return hasMoved;
  }

  /**
   * Whether or not player has acted this turn
   * @return if player has acted
   */
  public boolean hasActed() {
    return hasActed;
  }

  /**
   * Sets whether player has moved.
   * @return set value
   */
  public boolean hasMoved(boolean b) {
    this.hasMoved = b;
    return b;
  }

  /**
   * Sets whether player has acted
   * @return set value
   */
  public boolean hasActed(boolean b) {
    this.hasActed = b;
    return b;
  }

}