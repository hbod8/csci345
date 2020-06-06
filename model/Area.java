package model;

public class Area {
  private int x;
  private int y;
  private int h;
  private int w;

  public Area(int x, int y, int h, int w) {
    this.x = x;
    this.y = y;
    this.h = h;
    this.w = w;
  }

  /**
   * Gets X value.
   * @return coordinate
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets Y value.
   * @return coordinate
   */
  public int getY() {
    return this.y;
  }

  /**
   * Gets Height value.
   * @return coordinate
   */
  public int getH() {
    return this.h;
  }

  /**
   * Gets Width value.
   * @return coordinate
   */
  public int getW() {
    return this.w;
  }
}