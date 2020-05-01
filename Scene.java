import java.util.LinkedList;

public class Scene {
  private LinkedList<Role> roles;
  public boolean visible;
  public Scene(LinkedList<Role> roles) {
    this.roles = roles;
  }
  /**
   * @return the roles
   */
  public LinkedList<Role> getRoles() {
    return roles;
  }
}