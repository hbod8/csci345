package model;

import java.util.Random;
import java.util.List;
import java.util.LinkedList;


public class Player {
  private int credits = 0;
  private int dollars = 0;
  private int practiceChips = 0;
  private int rank = 1;
  private boolean hasMoved = false;
  private boolean hasActed = false;
  private Room room;
  public boolean inScene;
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

  //performs acting////////////////
  public boolean act() {
    int roll = 0;
    roll = dice.nextInt(6) + 1;
    
    // act...
    //successful roll
    if(roll + practiceChips >= ((SetRoom)this.room).getScene().getBudget()) {
      //decrement shot counter
      ((SetRoom)this.room).shoot();
      if (!((SetRoom)this.room).hasShots()) {
        ((SetRoom)this.room).setScene(null);
      }
      
      //if on/off the card
      if(inScene) {
        this.credits = this.credits + 2;
      }else {
        this.dollars++;
        this.credits++;
      }
    //failed roll
    } else {
      if(!inScene) {
        this.dollars++;
      }
      return false;
    }
    return true;
  }

  public void rehearse() {
    this.practiceChips++;
  }

  public boolean move(String roomname, Room newroom) {
    if (!this.room.isAdjacent(roomname)) {
      return false;
    } else {
      this.room = newroom;
      this.practiceChips = 0;
      return true;
    }
  }

  public boolean takeRole(Role role) {
    return role.take(this);
  }

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

  //returns Role object that player is on
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

  public boolean hasMoved() {
    return hasMoved;
  }

  public boolean hasActed() {
    return hasActed;
  }

  public boolean hasMoved(boolean b) {
    this.hasMoved = b;
    return b;
  }

  public boolean hasActed(boolean b) {
    this.hasActed = b;
    return b;
  }

}