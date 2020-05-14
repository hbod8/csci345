import java.util.Map;

public class Room {
  private Map<String, Room> adjacentRooms;
  private String name;
  public Room(Map<String, Room> adjacentRooms, String name) {
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
  public Map<String, Room> getAdjRooms() {
    return this.adjacentRooms;
  }
  
}