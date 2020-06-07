package model;

import java.util.List;
import java.util.Map;

/**
 * SetRoom represents a room a scene can take place.  Its responsibilities include the scene, "off-card" roles, and position of the shot counter.
 * 
 * @author Harry Saliba
 * @author Thomas Bidinger
 */
public class SetRoom extends Room {
  private List<Role> extras;
  private Scene scene;
  private Map<Integer, Area> shots;
  private int shot = 1;
  private int endingBudget;
  private int shotsLeft;
  public SetRoom(List<String> adjacentRooms, String name, List<Role> extras, Map<Integer, Area> shots, int x, int y, int w, int h) {
    super(adjacentRooms, name, x, y, w, h);
    this.extras = extras;
    this.shots = shots;
    this.shotsLeft = shots.size();
  }
  /**
   * @return the extras
   */
  public List<Role> getExtras() {
    return extras;
  }
  /**
   * @return the scene
   */
  public Scene getScene() {
    return scene;
  }
  /**
   * @return the shots
   */
  // public boolean hasShots() {
  //   return (shotsLeft > 0);
  // }
  public Map<Integer, Area> getShotMap() {
    return this.shots;
  }
  public Area getShotPosition() {
    return this.shots.get(this.shot);
  }
  /**
   * @param scene the scene to set
   */
  public void setScene(Scene scene) {
    this.scene = scene;
  }
  /**
   * @param shots the shots to set
   */
  public void resetShots() {
    this.shot = 1;
    this.shotsLeft = shots.size();
  }

  public void shoot() {
    this.shot++;
    this.shotsLeft--;
  }

  /**
   * Returns number of shots until scene wraps.
   * 
   * @return number of shots left
   */
  public int getShots() {
    return this.shotsLeft;
  }

  public int getBudget() {
    return endingBudget;
  }

  public void setBudget(int newBudget) {
    endingBudget = newBudget;
  }
}