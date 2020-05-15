import java.util.List;
import java.util.Map;

public class Room {
  private List<String> adjacentRooms;
  private String name;
  public Room(List<String> adjacentRooms, String name) {
    this.adjacentRooms = adjacentRooms;
    this.name = name;
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  public boolean isAdjacent() {
    return false;
  }
  public List<String> getAdjRooms() {
    return this.adjacentRooms;
  }
  
}