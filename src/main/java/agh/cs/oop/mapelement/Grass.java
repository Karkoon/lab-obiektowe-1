package agh.cs.oop.mapelement;

import agh.cs.oop.Vector2d;

public class Grass extends AbstractMapElement {

  private final static int Z_INDEX = 5;

  public Grass(Vector2d position) {
    super(position);
  }

  @Override
  public String toString() {
    return "*";
  }

  @Override
  public int zIndex() {
    return Z_INDEX;
  }

  @Override
  public int getEnergy() {
    return 1;
  }
}
