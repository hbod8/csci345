import java.util.LinkedList;
import java.util.Map;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLUtility {
  public Map<String, Room> getRoomsFromXML() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse("assets/cards.xml");
      Element root = document.getDocumentElement();
      NodeList elements = document.getChildNodes();
      // create rooms
      LinkedList<Room> roomList;
      for (int i = 0; i < elements.getLength(); i++) {
        Node element = elements.item(i);
        LinkedList<Room> adjRooms = new LinkedList<Room>();
        // Create new Room
        Room newRoom = new SetRoom(adjRooms, element.getAttributes().getNamedItem("name").getNodeValue());
      }
      for (int i = 0; i < elements.getLength(); i++) {
        Node element = elements.item(i);
        LinkedList<Room> adjRooms = new LinkedList<Room>();
        // Create new Room
        Room newRoom = new SetRoom(adjRooms, element.getAttributes().getNamedItem("name").getNodeValue());
      }
      // add adjcent rooms
        // LinkedList<Room> adjRooms;
          // NodeList ar = element.getChildNodes();
          // Node neighbors = ar.item(0);
          // NodeList neighbornames = neighbors.getChildNodes();
          // newRoom = new SetRoom(, name)
          // if (element.getNodeName().equals("set")) {
          //   newRoom = new SetRoom(new LinkedList<Room>(), element.getAttributes().getNamedItem("name").getNodeValue());
          // } else if (element.getNodeName().equals("trailer")) {
  
          // } else if (element.getNodeName().equals("office")) {
  
          // }
    } catch (Exception e) {
      System.err.println("Error in parsing XML: " + e.getStackTrace());
    }
  }
  public LinkedList<Scene> getScenesFromXML() {
    
  }

  public static void main(String[] args) {
    XMLUtility c = new XMLUtility();
    LinkedList<Room> r = c.getRoomsFromXML();
    for (Room room : r) {
      System.out.printf("%s:\n", room.getName());
      for (Room adj : room.getAdjRooms()) {
        System.out.printf("\t%s\n", adj.getName());
      }
    }
  }
}