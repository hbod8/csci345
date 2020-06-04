package model;

import java.util.List;

public class Room extends Area {
  private List<String> adjacentRooms;
  private String name;
  public Room(List<String> adjacentRooms, String name, int x, int y, int w, int h) {
    super(x, y, h, w);
    this.adjacentRooms = adjacentRooms;
    this.name = name;
    
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  public boolean isAdjacent(String roomname) {
    for (String name : this.adjacentRooms) {
      if (name.equals(roomname)) {
        return true;
      }
    }
    return false;
  }
  public List<String> getAdjRooms() {
    return this.adjacentRooms;
  }
  
}