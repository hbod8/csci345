import java.util.List;

public class Scene {
  private List<Role> roles;
  public boolean visible;
  public int scene;
  private int budget;
  public Scene(List<Role> roles) {
    this.roles = roles;
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
   * sets the budget
   */
  public void setBudget(int newBudget) {
    budget = newBudget;
  }
}