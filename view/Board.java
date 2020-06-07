package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import controller.GameController;
import model.Player;
import model.Room;
import model.SetRoom;
import model.Scene;

/**
 * Board represents a visual representation of the Deadwood game using java swing.
 * 
 * @author Harry Saliba
 * @author Thomas Bidinger
 */
public class Board extends JFrame {

  /* Serializable version (even though class is never serialized). */
  public static final long serialVersionUID = 0;

  /* Controller instance */
  private GameController gameController;

  /* Components */
  private JLabel boardLabel;

  /* Action menu and buttons */
  private JPanel menuPanel;
  private JLabel menuLabel;
  private JLabel playerIcon;
  private JLabel dayLabel;
  private JLabel locationLabel;
  private JLabel rankLabel;
  private JLabel roleLabel;
  private JLabel cashLabel;
  private JLabel creditsLabel;
  private JLabel reherseLabel;
  private JLabel playerTurn;
  private JLabel playerScore;
  private JButton moveButton;
  private JButton takeRoleButton;
  private JButton actButton;
  private JButton reherseButton;
  private JButton upgradeButton;
  private JButton endTurnButton;
  private JButton testButton;

  /* JLayered Pane */
  private JLayeredPane layeredPane;

  /* Popup */
  JFrame popframe;

  /* Image folder location */
  private ImageIcon icon;

  /* Player map Player->component */
  private Map<Player, JLabel> players;

  /* Scenes map Scene->component */
  private Map<SetRoom, JLabel> scenes;

  /* Shot counters map SetRoom->component */
  private Map<SetRoom, JLabel> shotTokens;

  private static final String imageFolder = "assets/images/";
  private final String[] playerIconColors = { "b", "r", "g", "o", "p", "c", "v", "w", "y" };
  private int nextPlayerColor = 0;

  public Board(GameController gameController) {
    // Set the title of the JFrame
    super("Deadwood");
    this.gameController = gameController;
    this.players = new HashMap<Player, JLabel>();
    this.scenes = new HashMap<SetRoom, JLabel>();
    this.shotTokens = new HashMap<SetRoom, JLabel>();
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
    /* Create the deadwood board */
    boardLabel = new JLabel();
    boardLabel.setIcon(icon);
    boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
    /* Add the board to the lowest layer */
    layeredPane.add(boardLabel, 0);
  }

  private void createMenu() {
    /* Create menu panel */
    this.menuPanel = new JPanel();
    this.menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
    this.menuPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
    this.menuPanel.setBounds(icon.getIconWidth(), 0, 200, 500);
    this.menuPanel.setVisible(true);
    layeredPane.add(menuPanel, 0);
    /* Create the Menu for action buttons */
    this.menuLabel = new JLabel(" --Menu--");
    /* Create player icon */
    this.playerIcon = new JLabel();
    /* Initalize labels */
    this.dayLabel = new JLabel();
    this.locationLabel = new JLabel();
    this.rankLabel = new JLabel();
    this.roleLabel = new JLabel();
    this.cashLabel = new JLabel();
    this.creditsLabel = new JLabel();
    this.reherseLabel = new JLabel();
    this.playerTurn = new JLabel();
    this.playerScore = new JLabel();
    /* Initalize buttons */
    moveButton = new JButton("Move");
    moveButton.setBackground(Color.white);
    moveButton.addMouseListener(new boardMouseListener());

    takeRoleButton = new JButton("Take Role");
    takeRoleButton.setBackground(Color.white);
    takeRoleButton.addMouseListener(new boardMouseListener());

    actButton = new JButton("Act");
    actButton.setBackground(Color.white);
    actButton.addMouseListener(new boardMouseListener());

    reherseButton = new JButton("Reherse");
    reherseButton.setBackground(Color.white);
    reherseButton.addMouseListener(new boardMouseListener());

    upgradeButton = new JButton("Upgrade");
    upgradeButton.setBackground(Color.white);
    upgradeButton.addMouseListener(new boardMouseListener());

    endTurnButton = new JButton("End Turn");
    endTurnButton.setBackground(Color.white);
    endTurnButton.addMouseListener(new boardMouseListener());

    testButton = new JButton("TEST");
    testButton.setBackground(Color.white);
    testButton.addMouseListener(new boardMouseListener());
  }

  /**
   * Paints the player dice icon on board.
   * 
   * @param p Player to paint
   */
  public void paintActions(Player p) {
    /* Clear menu */
    menuPanel.removeAll();
    /* Add title */
    menuPanel.add(menuLabel);
    /* Display player icon */
    playerIcon.setIcon(this.players.get(p).getIcon());
    menuPanel.add(playerIcon);
    /* Display player turn */
    playerTurn.setText(" " + p.getName() + "\'s turn.");
    menuPanel.add(playerTurn);
    /* Display player score */
    playerScore.setText("Score: " + p.calculateScore());
    menuPanel.add(playerScore);
    /* Display Day */
    dayLabel.setText(" Day: " + gameController.getDay());
    menuPanel.add(dayLabel);
    /* Display location */
    locationLabel = new JLabel(" Location: " + p.getRoom().getName());
    locationLabel.setText(" Location: " + p.getRoom().getName());
    menuPanel.add(locationLabel);
    /* Display rank */
    rankLabel.setText(" Rank: " + p.getRank());
    menuPanel.add(rankLabel);
    /* Display Role */
    if (p.onRole()) {
      roleLabel.setText(" Role: " + p.getRole().getName());
      menuPanel.add(roleLabel);
    }
    /* Display Cash */
    cashLabel.setText(" Dollars: " + p.getDollars());
    menuPanel.add(cashLabel);
    /* Display Credits */
    creditsLabel.setText(" Credits:" + p.getCredits());
    menuPanel.add(creditsLabel);
    /* Display rehersals */
    if (p.getTokens() > 0) {
      reherseLabel.setText(" Rehersals:" + p.getTokens());
      menuPanel.add(reherseLabel);
    }
    /* Display action buttons */
    if (!p.hasMoved() && !p.hasActed()) {
      menuPanel.add(moveButton);
      menuPanel.add(reherseButton);
      menuPanel.add(actButton);
    }
    if (!p.hasActed()) {
      menuPanel.add(takeRoleButton);
      if (p.getRoom().getName().equals("office")) {
        menuPanel.add(upgradeButton);
      }
    }
    menuPanel.add(endTurnButton);
    // menuPanel.add(testButton);
    menuPanel.validate();
  }

  public void paintPlayer(Player p) {
    if (!this.players.containsKey(p)) {
      /* Add new player */
      JLabel playerLabel = new JLabel();
      ImageIcon img = new ImageIcon(
          imageFolder + "dice/" + this.playerIconColors[nextPlayerColor] + p.getRank() + ".png");
      playerLabel.setIcon(img);
      nextPlayerColor++;
      layeredPane.add(playerLabel, 2);
      this.players.put(p, playerLabel);
    }
    JLabel playerLabel = this.players.get(p);
    if (p.onRole()) {
      if (p.getRoom() instanceof SetRoom && ((SetRoom) p.getRoom()).getScene() != null
          && ((SetRoom) p.getRoom()).getScene().getRoles().contains(p.getRole())) {
        playerLabel.setBounds(p.getRoom().getX() + p.getRole().getX(), p.getRoom().getY() + p.getRole().getY(), 46, 46);
      } else {
        playerLabel.setBounds(p.getRole().getX(), p.getRole().getY(), 46, 46);
      }
    } else {
      playerLabel.setBounds(p.getRoom().getX(), p.getRoom().getY(), 46, 46);
    }
    layeredPane.validate();
    layeredPane.repaint();
  }

  public void paintAllScenes(List<Room> rooms) {
    for (Room r : rooms) {
      if (r instanceof SetRoom) {
        paintScene((SetRoom) r);
      }
    }
  }

  public void paintScene(SetRoom r) {
    if (!this.scenes.containsKey(r)) {
      /* Add scene */
      JLabel sceneLabel = new JLabel();
      layeredPane.add(sceneLabel, 1);
      this.scenes.put(r, sceneLabel);
      JLabel shotLabel = new JLabel();
      ImageIcon shotImage = new ImageIcon(imageFolder + "shot.png");
      shotLabel.setIcon(shotImage);
      layeredPane.add(shotLabel, 2);
      this.shotTokens.put(r, shotLabel);
    }
    if (r.getScene() == null) {
      JLabel sceneLabel = this.scenes.get(r);
      sceneLabel.setVisible(false);
    } else {
      Scene s = r.getScene();
      JLabel sceneLabel = this.scenes.get(r);
      ImageIcon sceneIcon;
      if (!s.visible) {
        sceneIcon = new ImageIcon(imageFolder + "CardBack-small.jpg");
        sceneLabel.setIcon(sceneIcon);
      } else {
        sceneIcon = new ImageIcon(imageFolder + "cards/" + s.getImageName());
        sceneLabel.setIcon(sceneIcon);
      }
      sceneLabel.setVisible(true);
      sceneLabel.setBounds(r.getX(), r.getY(), 205, 115);
    }
    paintShotCounter(r);
    layeredPane.validate();
    layeredPane.repaint();
  }

  private void paintShotCounter(SetRoom r) {
    JLabel shotCounterLabel = this.shotTokens.get(r);
    if (r.getScene() == null) {
      shotCounterLabel.setVisible(false);
    } else {
      shotCounterLabel.setVisible(true);
      shotCounterLabel.setBounds(r.getShotPosition().getX(), r.getShotPosition().getY(), r.getShotPosition().getW(),
          r.getShotPosition().getH());
    }
  }

  public void displayMessage(String s) {
    JOptionPane.showMessageDialog(this, s, "Message", 0);
  }

  public String selectionBox(String prompt, List<String> selections) {
    popframe = new JFrame();
    popframe.setAlwaysOnTop(true);
    Object selectionObject = JOptionPane.showInputDialog(popframe, prompt, "", JOptionPane.PLAIN_MESSAGE, null,
        selections.toArray(), selections.get(0));
    String selectionString = selectionObject.toString();
    return selectionString;
  }

  class boardMouseListener implements MouseListener {
    /* Code for the different button clicks */
    public void mouseClicked(MouseEvent e) {
      if (e.getSource() == moveButton) {
        System.out.println("Move is Selected\n");
        gameController.move();
      } else if (e.getSource() == takeRoleButton) {
        System.out.println("Take Role is Selected\n");
        gameController.takeRole();
      } else if (e.getSource() == actButton) {
        System.out.println("Act is Selected\n");
        gameController.act();
      } else if (e.getSource() == reherseButton) {
        System.out.println("Reherse is Selected\n");
        gameController.reherse();
      } else if (e.getSource() == upgradeButton) {
        System.out.println("Upgrade is Selected\n");
        gameController.upgrade();
      } else if (e.getSource() == endTurnButton) {
        System.out.println("End Turn is Selected\n");
        gameController.endTurn();
      } else if (e.getSource() == testButton) {
        System.out.println("TEST!\n");
        gameController.test();
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