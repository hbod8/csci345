package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.util.List;
import java.util.Map;

import controller.GameController;
import model.Game;
import model.Player;
import model.Room;
import model.Role;
import model.SetRoom;
import model.Scene;

public class Board extends JFrame {

  /* Controller instance */
  private GameController gameController;

  /* Components */
  private JLabel boardLabel;

  /* Action menu and buttons */
  private JPanel menuPanel;
  private JLabel menuLabel;
  private JButton actButton;
  private JButton reherseButton;
  private JButton moveButton;

  // JLayered Pane
  private JLayeredPane layeredPane;

  //Popup
  JFrame popframe;

  /* Image folder location */
  private ImageIcon icon;

  /* Player map name->component */
  private Map<Player, JLabel> players;

  private static final String imageFolder = "assets/images/";
  private final String[] playerIconColors = {"b", "c", "g", "o", "p", "r", "v", "w", "y"};
  private int nextPlayerColor = 0;

  public Board(GameController gameController) {
    // Set the title of the JFrame
    super("Deadwood");
    this.gameController = gameController;
    /* Call setup function */
    setup();
    /* Call create labels function */
    createLabels();
    /* Create action menu */
    createMenu();
  }

  private void setup() {
    // Set the exit option for the JFrame
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    // Create image icon so dimensions can be calculated.
    icon = new ImageIcon(imageFolder + "board.jpg");
    // Set the size of the GUI
    setSize(new Dimension(icon.getIconWidth() + 200, icon.getIconHeight()));
    setPreferredSize(new Dimension(icon.getIconWidth() + 200, icon.getIconHeight()));
    // Create the JLayeredPane to hold the display, cards, dice and buttons
    layeredPane = getLayeredPane();
  }

  private void createLabels() {
    // Create the deadwood board
    boardLabel = new JLabel();
    boardLabel.setIcon(icon);
    boardLabel.setBounds(200, 0, icon.getIconWidth(), icon.getIconHeight());

    // Add the board to the lowest layer
    layeredPane.add(boardLabel, 0);
  }

  private void createMenu() {
    /* Create menu panel */
    this.menuPanel = new JPanel();
    this.menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
    this.menuPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    // this.menuPanel.setMaximumSize(new Dimension(100, 500));
    this.menuPanel.setBounds(0, 0, 200, 500);
    this.menuPanel.setVisible(true);
    layeredPane.add(menuPanel, 0);
    // Create the Menu for action buttons
    this.menuLabel = new JLabel("MENU");
    menuPanel.add(this.menuLabel);

    /* Initalize buttons */
    actButton = new JButton("Act");
    actButton.setBackground(Color.white);
    actButton.addMouseListener(new boardMouseListener());

    reherseButton = new JButton("Reherse");
    reherseButton.setBackground(Color.white);
    reherseButton.addMouseListener(new boardMouseListener());

    moveButton = new JButton("Move");
    moveButton.setBackground(Color.white);
    moveButton.addMouseListener(new boardMouseListener());

    paintActions(false, false);
  }

  /* paint actions */
  public void paintActions(boolean hasMoved, boolean hasActed) {
    if (hasMoved) {
      menuPanel.remove(moveButton);
    } else {
      menuPanel.add(moveButton);
    }
    if (hasActed) {
      menuPanel.remove(actButton);
      menuPanel.remove(reherseButton);
    } else {
      menuPanel.add(actButton);
      menuPanel.add(reherseButton);
    }
    menuPanel.validate();
  }

  /* @TODO paintPlayer(Player) */
  public void paintPlayer(Player p) {
    if (!this.players.containsKey(p)) {
      /* Add player */
      JLabel playerLabel = new JLabel();
      this.players.put(p, playerLabel);
      ImageIcon img = new ImageIcon(imageFolder + this.playerIconColors[nextPlayerColor] + p.getRank() + ".png");
      playerLabel.setIcon(img);
    }
  }

  /* @TODO paintScene(Scene) */
  public void paintScene(Scene s) {

  }

  /* Paint move options */
  public void moveOptions(List<String> adjRooms) {
    // JComboBox<String> moveRooms = new JComboBox<String>();
    // for (String i : adjRooms) {
    //   moveRooms.addItem(i);
    // }
    // JLabel l = new JLabel("Select where to move.");
    // moveRooms.setSelectedIndex(0);
    // moveRooms.addMouseListener(new boardMouseListener());
    // moveRooms.setMaximumSize(new Dimension(100, 16));
    // moveRooms.setAlignmentY(Component.LEFT_ALIGNMENT);
    // menuPanel.add(l);
    // menuPanel.add(moveRooms);
    // menuPanel.validate();
    popframe = new JFrame();
    popframe.setAlwaysOnTop(true);
    Object selectionObject = JOptionPane.showInputDialog(popframe, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, adjRooms.toArray(), adjRooms.get(0));
    String selectionString = selectionObject.toString();
    
  }

  public void displayMessage(String s) {
    JOptionPane.showMessageDialog(this, 2, "Message", 0);
  }

  class boardMouseListener implements MouseListener {
    // Code for the different button clicks
    public void mouseClicked(MouseEvent e) {
  
      if (e.getSource() == actButton) {
        System.out.println("Acting is Selected\n");
      } else if (e.getSource() == reherseButton) {
        System.out.println("Rehearse is Selected\n");
      } else if (e.getSource() == moveButton) {
        System.out.println("Move is Selected\n");
        /* Tell controller the player wants to move */
        gameController.move();
      }
    }
  
    public void mousePressed(MouseEvent e) {
    }
  
    public void mouseReleased(MouseEvent e) {
    }
  
    public void mouseEntered(MouseEvent e) {
    }
  
    public void mouseExited(MouseEvent e) {
    }
  }
}