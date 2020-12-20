package agh.cs.oop.mapelement;

import agh.cs.oop.Vector2d;
import agh.cs.oop.worldmap.IWorldMap;

public class Grass extends AbstractMapElement {

  public Grass(IWorldMap worldMap, Vector2d position) {
    super(position);
    worldMap.plant(this);
  }

  @Override
  public String toString() {
    return "*";
  }

  @Override
  public int getEnergy() {
    return 40;
  }
}
