import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLUtility {
  public Map<String, Room> getRoomsFromXML() {
    Map<String, Room> roomList = new HashMap<String, Room>();
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse("assets/board.xml");
      Element root = document.getDocumentElement();
      NodeList elements = document.getChildNodes().item(0).getChildNodes();
      System.out.printf("Nodes:%d\n", elements.getLength());
      // create rooms
      for (int i = 0; i < elements.getLength(); i++) {
        Node element = elements.item(i);
        List<String> adjRooms = new LinkedList<String>();
        // Create new Room
        if (element.getNodeName().equals("set")) {
          String roomname = element.getAttributes().getNamedItem("name").getNodeValue();
          Room newRoom = new SetRoom(adjRooms, roomname);
          // Add set to list
          roomList.put(roomname, newRoom);
          // get neighbors
          NodeList children = element.getChildNodes();
          // Loop though child nodes
          for (int j = 0; j < children.getLength(); j++) {
            // If child is a neighbors list then...
            if (children.item(j).getNodeName().equals("neighbors")) {
              NodeList neighborNodes = children.item(j).getChildNodes();
              System.out.println("Neighbornodelistname: " + children.item(j).getNodeName());
              // Loop though children
              for (int k = 0; k < neighborNodes.getLength(); k++) {
                System.out.println("Neighbornodename: " + neighborNodes.item(k).getNodeName());
                String neighborname = neighborNodes.item(k).getAttributes().getNamedItem("name").getNodeValue();
                newRoom.getAdjRooms().add(neighborname);
              }
            }
          }
        } else if (element.getNodeName().equals("office")) {
          Room newRoom = new Room(adjRooms, "office");
          // Add office to list
          roomList.put("office", newRoom);
          // get neighbors
          NodeList children = element.getChildNodes();
          // Loop though child nodes
          for (int j = 0; j < children.getLength(); j++) {
            // If child is a neighbors list then...
            if (children.item(j).getNodeName().equals("neighbors")) {
              NodeList neighborNodes = children.item(j).getChildNodes();
              // Loop though children
              for (int k = 0; k < neighborNodes.getLength(); k++) {
                String neighborname = neighborNodes.item(k).getAttributes().getNamedItem("name").getNodeValue();
                newRoom.getAdjRooms().add(neighborname);
              }
            }
          }
        } else if (element.getNodeName().equals("trailer")) {
          Room newRoom = new Room(adjRooms, "trailer");
          // Add trailer to list
          roomList.put("trailer", newRoom);
          // get neighbors
          NodeList children = element.getChildNodes();
          // Loop though child nodes
          for (int j = 0; j < children.getLength(); j++) {
            // If child is a neighbors list then...
            if (children.item(j).getNodeName().equals("neighbors")) {
              NodeList neighborNodes = children.item(j).getChildNodes();
              // Loop though children
              for (int k = 0; k < neighborNodes.getLength(); k++) {
                String neighborname = neighborNodes.item(k).getAttributes().getNamedItem("name").getNodeValue();
                newRoom.getAdjRooms().add(neighborname);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      System.err.println("Error in parsing XML: " + e.toString());
      e.printStackTrace();
    }
    return roomList;
  }
  public List<Scene> getScenesFromXML() {
    return new LinkedList<Scene>();
  }

  public static void main(String[] args) {
    System.out.println("Test:");
    XMLUtility c = new XMLUtility();
    Map<String, Room> r = c.getRoomsFromXML();
    for (Room room : r.values()) {
      System.out.printf("%s:\n", room.getName());
      for (String adj : r.get(room.getName()).getAdjRooms()) {
        System.out.printf("\t%s\n", adj);
      }
    }
  }
}