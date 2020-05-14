import java.util.Random;
import java.lang.Exception;


public class Player {
  private int credits = 0;
  private int dollars = 0;
  private int practiceChips = 0;
  private int rank = 1;
  private Room room;
  public boolean inScene;
  Random dice = new Random();


  public Player(Room startRoom) {
    this.room = startRoom;
  }
  public Player(int startCredits, Room startRoom) {
    this.credits = startCredits;
    this.room = startRoom;
  }
  public Player(int startCredits, int startRank, Room startRoom) {
    this.credits = startCredits;
    this.rank = startRank;
    this.room = startRoom;
  }

  //performs acting////////////////
  public void act() throws Exception {
    int roll = 0;
    roll = dice.nextInt(6) + 1;
    // check if you can act in rooms
    if (!(this.room instanceof SetRoom)) {
      throw new Exception("Player is not in a room that they can act in.");
    }
    
    // act...
    //successful roll
    if(roll >= ((SetRoom)this.room).getScene().getBudget()) {
      //decrement shot counter
      ((SetRoom)this.room).setShots(((SetRoom)this.room).getShots() - 1);
      
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
    }
  }

  public void rehearse() throws Exception {
    if(((SetRoom)this.room).getScene().getBudget() == (practiceChips + 1)) {
      throw new Exception("Cannot rehearse anymore, must act.");
    }
    this.practiceChips++;
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
}