package model;

import java.io.IOException;

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

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getH() {
    return this.h;
  }

  public int getW() {
    return this.w;
  }
}