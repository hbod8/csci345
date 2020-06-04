package model;

import java.util.List;

public class Scene {
  private String imageName;
  private List<Role> roles;
  public boolean visible = false;
  public int scene;
  private int budget;
  private String name;
  public Scene(List<Role> roles, int budget, String name, String imageName) {
    this.roles = roles;
    this.budget = budget;
    this.name = name;
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
  public String getName() {
    return this.name;
  }

  /**
   * sets the budget
   */
  public void setBudget(int newBudget) {
    budget = newBudget;
  }

}