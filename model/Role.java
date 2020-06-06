package model;

public class Role extends Area {
  private Player player;
  private int requiredRank;
  private String name;
  public Role(int requiredRank, String name, int x, int y, int w, int h) {
    super(x, y, h, w);
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

  public Player getPlayer() {
    return this.player;
  }
  
}