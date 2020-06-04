package model;

import java.util.List;

public class SetRoom extends Room {
  private List<Role> extras;
  private Scene scene;
  private int shots = 3;
  public SetRoom(List<String> adjacentRooms, String name, List<Role> extras) {
    super(adjacentRooms, name);
    this.extras = extras;
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
  public int getShots() {
    return shots;
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
  public void setShots(int shots) {
    this.shots = shots;
  }
}