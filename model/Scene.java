package model;

import java.util.List;

/**
 * Scene represents a scene card in Deadwood.  Scene's responsibilities include roles, budget, name, image name, and if its visible to the player.
 * 
 * @author Harry Saliba
 * @author Thomas Bidinger
 */
public class Scene {
  private List<Role> roles;
  public boolean visible = false;
  public int scene;
  private int budget;
  private String name;
  private String imageName;
  public Scene(List<Role> roles, int budget, String name, String imageName) {
    this.roles = roles;
    this.budget = budget;
    this.name = name;
    this.imageName = imageName;
    for(Role curRole : roles) {
      curRole.setInScene(true);
    }
  }
  /**
   * @return the roles
   */
  public List<Role> getRoles() {
    return roles;
  }

  /**
   * @return the budget
   */
  public int getBudget() {
    return budget;
  }

  /**
   * Gets name.
   * @return String name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the image name.
   * @return String name
   */
  public String getImageName() { 
    return this.imageName;
  }

  /**
   * Sets whether scene card is visible.
   */
  public void setVisible(boolean x) {
    visible = x;
  }

  /**
   * Returns whether card is visible
   * @return boolean visible
   */
  public boolean getVisible() {
    return this.visible;
  }

  /**
   * sets the budget
   */
  public void setBudget(int newBudget) {
    budget = newBudget;
  }

}