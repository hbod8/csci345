public class Role {
  private Player player;
  private int rehersals;
  private int requiredRank;
  public Role(int requiredRank) {
    this.requiredRank = requiredRank;
  }
  public void act() {

  }
  public void free(){
    
  }
  /**
   * @return the rehersals
   */
  public int getRehersals() {
    return rehersals;
  }
  public boolean isTaken() {
    return true;
  }
  public void reherse() {

  }
  public void take(Player player) {
    this.player = player;
  }
}