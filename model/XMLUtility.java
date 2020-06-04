package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLUtility {
  public static Map<String, Room> parseRoomsFromXML() {
    Map<String, Room> roomList = new HashMap<String, Room>();
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse("assets/board.xml");
      NodeList elements = document.getChildNodes().item(0).getChildNodes();
      // create rooms
      for (int i = 0; i < elements.getLength(); i++) {
        Node element = elements.item(i);
        List<String> adjRooms = new LinkedList<String>();
        // Create new Room
        if (element.getNodeType() == Node.ELEMENT_NODE && element.getNodeName().equals("set")) {
          Area area = parseArea(element);
          String roomname = element.getAttributes().getNamedItem("name").getNodeValue();
          Room newRoom = new SetRoom(adjRooms, roomname, new LinkedList<Role>(), area.getX(), area.getY(), area.getW(), area.getH());
          // Add set to list
          roomList.put(roomname, newRoom);
          parseNeighbors(element, newRoom);
          parseOffCardRoles(element, (SetRoom) newRoom);
        } else if (element.getNodeName().equals("office")) {
          Area area = parseArea(element);
          Room newRoom = new Room(adjRooms, "office", area.getX(), area.getY(), area.getW(), area.getH());
          // Add office to list
          roomList.put("office", newRoom);
          parseNeighbors(element, newRoom);
        } else if (element.getNodeName().equals("trailer")) {
          Area area = parseArea(element);
          Room newRoom = new Room(adjRooms, "trailer", area.getX(), area.getY(), area.getW(), area.getH());
          // Add trailer to list
          roomList.put("trailer", newRoom);
          parseNeighbors(element, newRoom);
        }
      }
    } catch (Exception e) {
      System.err.println("Error in parsing XML: " + e.toString());
      e.printStackTrace();
    }
    return roomList;
  }

  public static List<Scene> parseScenesFromXML() {
    List<Scene> sceneList = new LinkedList<Scene>();
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse("assets/cards.xml");
      NodeList elements = document.getChildNodes().item(0).getChildNodes();
      // Loop through cards
      for (int i = 0; i < elements.getLength(); i++) {
        Node element = elements.item(i);
        // Double check that element is element and card
        if (element.getNodeType() == Node.ELEMENT_NODE && element.getNodeName().equals("card")) {
          // Parse Scene name
          String sceneName = element.getAttributes().getNamedItem("name").getNodeValue();
          // Parse Scene image name
          String sceneImageName = element.getAttributes().getNamedItem("img").getNodeValue();
          // Parse scene budget
          int sceneBudget = Integer.parseInt(element.getAttributes().getNamedItem("budget").getNodeValue());
          // Loop though parts aka "roles"
          List<Role> newRoles = new LinkedList<Role>();
          NodeList children = element.getChildNodes();
          for (int j = 0; j < children.getLength(); j++) {
            Node child = children.item(j);
            // Double check is role
            if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("part")) {
              // Parse required rank
              int reqRank = Integer.parseInt(child.getAttributes().getNamedItem("level").getNodeValue());
              // Parse rolename
              String roleName = child.getAttributes().getNamedItem("name").getNodeValue();
              // Parse area
              Area area = parseArea(child);
              // Create new role
              Role newRole = new Role(reqRank, roleName, area.getX(), area.getY(), area.getW(), area.getH());
              newRoles.add(newRole);
            }
          }
          // Add scene to list
          sceneList.add(new Scene(newRoles, sceneBudget, sceneName, sceneImageName));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sceneList;
  }

  private static void parseOffCardRoles(Node element, SetRoom newRoom) {
    // parse roles
    NodeList children = element.getChildNodes();
    // Loop through nodes
    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      // Check is role list
      if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("parts")) {
        NodeList partNodes = child.getChildNodes();
        // loop though parts
        for (int j = 0; j < partNodes.getLength(); j++) {
          Node part = partNodes.item(j);
          // check is part
          if (part.getNodeType() == Node.ELEMENT_NODE && part.getNodeName().equals("part")) {
            // Parse required rank
            int reqRank = Integer.parseInt(part.getAttributes().getNamedItem("level").getNodeValue());
            // Parse rolename
            String roleName = part.getAttributes().getNamedItem("name").getNodeValue();
            // Parse area
            Area area = parseArea(part);
            // Create new role
            Role newRole = new Role(reqRank, roleName, area.getX(), area.getY(), area.getW(), area.getH());
            newRoom.getExtras().add(newRole);
          }
        }
      }
    }
  }

  private static void parseNeighbors(Node element, Room newRoom) {
    // get neighbors
    NodeList children = element.getChildNodes();
    // Loop though child nodes
    for (int j = 0; j < children.getLength(); j++) {
      // If child is a neighbors list then...
      if (children.item(j).getNodeType() == Node.ELEMENT_NODE && children.item(j).getNodeName().equals("neighbors")) {
        NodeList neighborNodes = children.item(j).getChildNodes();
        // Loop though children
        for (int k = 0; k < neighborNodes.getLength(); k++) {
          // Double check that child is a neighbor...
          if (neighborNodes.item(k).getNodeName().equals("neighbor") && neighborNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
            // Add neighborname to neighbors
            String neighborname = neighborNodes.item(k).getAttributes().getNamedItem("name").getNodeValue();
            newRoom.getAdjRooms().add(neighborname);
          }
        }
      }
    }
  }

  private static Area parseArea(Node element) {
    Area area = null;
    // get neighbors
    NodeList children = element.getChildNodes();
    // Loop though child nodes
    for (int j = 0; j < children.getLength(); j++) {
      Node child = children.item(j);
      // If child is an image location then...
      if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("area")) {
        // Get data from node
        int x = Integer.parseInt(child.getAttributes().getNamedItem("x").getNodeValue());
        int y = Integer.parseInt(child.getAttributes().getNamedItem("y").getNodeValue());
        int w = Integer.parseInt(child.getAttributes().getNamedItem("w").getNodeValue());
        int h = Integer.parseInt(child.getAttributes().getNamedItem("h").getNodeValue());
        area = new Area(x, y, w, h);
      }
    }
    return area;
  }

  public static void main(String[] args) {
    System.out.println("Test Rooms:");
    Map<String, Room> r = XMLUtility.parseRoomsFromXML();
    for (Room room : r.values()) {
      System.out.printf("\t%s @(%d, %d) %d x %d:\n\t\tAdjacent Rooms:\n", room.getName(), room.getX(), room.getY(), room.getW(), room.getH());
      for (String adj : r.get(room.getName()).getAdjRooms()) {
        System.out.printf("\t\t\t%s\n", adj);
      }
      if (room instanceof SetRoom) {
        System.out.printf("\t\tRoles:\n");
        for (Role role : ((SetRoom) room).getExtras()) {
          System.out.printf("\t\t\t%s (%d) @(%d, %d) %d x %d\n", role.getName(), role.getRank(), role.getX(), role.getY(), role.getW(), role.getH());
        }
      }
    }
    System.out.println("Test Scenes:");
    List<Scene> s = XMLUtility.parseScenesFromXML();
    for (Scene scene : s) {
      System.out.printf("\t%s (%d):\n", scene.getName(), scene.getBudget());
      for (Role role : scene.getRoles()) {
        System.out.printf("\t\t%s (%d) @(%d, %d) %d x %d\n", role.getName(), role.getRank(), role.getX(), role.getY(), role.getW(), role.getH());
      }
    }
  }
}