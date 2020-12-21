package agh.cs.oop.mapelement;

import agh.cs.oop.util.Vector2d;
import agh.cs.oop.worldmap.IWorldMap;

public class Grass extends AbstractMapElement {

  private final int PLANT_ENERGY;

  public Grass(IWorldMap worldMap, Vector2d position, int plantEnergy) {
    super(position);
    this.PLANT_ENERGY = plantEnergy;
    worldMap.plant(this);
  }

  @Override
  public String toString() {
    return "*";
  }

  @Override
  public int getEnergy() {
    return PLANT_ENERGY;
  }

  private final static int Z_INDEX = -1;

  @Override
  public int zIndex() {
    return Z_INDEX;
  }
}
