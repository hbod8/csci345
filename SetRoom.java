import java.util.LinkedList;

public class SetRoom extends Room {
  private LinkedList<Role> extras;
  private Scene scene;
  private int shots = 3;
  public SetRoom(LinkedList<Room> adjacentRooms, String name) {
    super(adjacentRooms, name);
  }
  /**
   * @return the extras
   */
  public LinkedList<Role> getExtras() {
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