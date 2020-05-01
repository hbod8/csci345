public class Player {
  private int credits = 0;
  private int dollars = 0;
  private int rank = 0;
  private Room room;
  public Player(int startCredits, int startDollars, Room startRoom) {
    this.credits = startCredits;
    this.dollars = startDollars;
    this.room = startRoom;
  }
  public Player(Room startRoom) {
    this.room = startRoom;
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