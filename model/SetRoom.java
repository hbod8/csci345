package model;

import java.util.List;

public class SetRoom extends Room {
  private List<Role> extras;
  private Scene scene;
  private List<Area> shots;
  private int shot = 0;
  public SetRoom(List<String> adjacentRooms, String name, List<Role> extras, List<Area> shots, int x, int y, int w, int h) {
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
  public int getShots() {
    return shots.size() - shot;
  }
  public List<Area> getShotList() {
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
  public void setShots(int shot) {
    this.shot = shots.size() - 1;
  }
}