public class Role {
  private Player player;
  private int practiceChips = 0;
  private int requiredRank;
  private String name;
  public Role(int requiredRank, String name) {
    this.requiredRank = requiredRank;
    this.name = name;
  }
  public void free() {
    this.player = null;
  }
  /**
   * @return the rehersals
   */
  // public int getRehersals() {
  //   return this.rehersals;
  // }
  public boolean isTaken() {
    return (this.player != null);
  }
  public void reherse() {
    this.practiceChips++;
  }
  public void take(Player player) {
    this.player = player;
  }

  /**
   * @param name the name to be set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return rank
   */
  public int getRank() {
    return this.requiredRank;
  }
  
}