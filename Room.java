import java.util.LinkedList;

public class Room {
  private LinkedList<Room> adjacentRooms;
  private String name;
  public Room(LinkedList<Room> adjacentRooms, String name) {
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
}