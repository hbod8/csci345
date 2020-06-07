package model;

/**
 * Role represents a possibly actable role a Deadwood player can have.
 * Its responsibilites include required rank to take the role, the
 * current player on the role, and its name.
 * 
 * @author Harry Saliba
 * @author Thomas Bidinger
 */
public class Role extends Area {
  private Player player;
  private int requiredRank;
  private String name;
  private boolean inScene = false;
  public Role(int requiredRank, String name, int x, int y, int w, int h) {
    super(x, y, h, w);
    this.requiredRank = requiredRank;
    this.name = name;
  }
  public void free() {
    this.player = null;
  }

  /**
   * Returns role if taken
   * @return if taken
   */
  public boolean isTaken() {
    return (this.player != null);
  }

  /**
   * Puts player on role
   */
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

  /**
   * Returns player on role
   * @return player
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * @param newVal the inScene value to be set
   */
  public void setInScene(boolean newVal) {
    this.inScene = newVal;
  }

  /**
   * Returns whether on card
   * @return inscene
   */
  public boolean getInScene() {
    return this.inScene;
  }
  
}