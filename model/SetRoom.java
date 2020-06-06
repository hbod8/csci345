package model;

import java.util.List;
import java.util.Map;

public class SetRoom extends Room {
  private List<Role> extras;
  private Scene scene;
  private Map<Integer, Area> shots;
  private int shot = 1;
  public SetRoom(List<String> adjacentRooms, String name, List<Role> extras, Map<Integer, Area> shots, int x, int y, int w, int h) {
    super(adjacentRooms, name, x, y, w, h);
    this.extras = extras;
    this.shots = shots;
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
  public boolean hasShots() {
    return (shot == shots.size() + 1);
  }
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
  }

  public void shoot() {
    this.shot++;
  }
}