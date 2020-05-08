import java.util.LinkedList;

public class Scene {
  private LinkedList<Role> roles;
  public boolean visible;
  public int scene;
  private int budget;
  public Scene(LinkedList<Role> roles) {
    this.roles = roles;
  }
  /**
   * @return the roles
   */
  public LinkedList<Role> getRoles() {
    return roles;
  }

  /**
   * @return the budget
   */
  public int getBudget() {
    return budget;
  }

  /**
   * sets the budget
   */
  public void setBudget(int newBudget) {
    budget = newBudget;
  }
}