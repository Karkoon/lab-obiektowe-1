package agh.cs.oop.mapelement;

import agh.cs.oop.util.Vector2d;
import agh.cs.oop.worldmap.IMapBoundary;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GrassGenerator {

  private final IWorldMap map;
  private final int PLANT_ENERGY;


  public GrassGenerator(IWorldMap map, int plantEnergy) {
    this.map = map;
    this.PLANT_ENERGY = plantEnergy;
  }

  public List<Grass> generateGrasses(int n) {
    List<Grass> grasses = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      Vector2d randomPosition;
      do {
        randomPosition = map.getMapBoundary().randomPosition();
      } while (map.isOccupied(randomPosition));
      grasses.add(newGrass(randomPosition));
    }
    return grasses;
  }

  public Grass generateGrassOn(IMapBoundary mapBoundary) {
    return this.generateGrassOnUnless(mapBoundary, (a) -> false);
  }

  public Grass generateGrassOnUnless(IMapBoundary mapBoundary, Predicate<Vector2d> ifNot) {
    Vector2d position;
    int tries = 0;
    do {
      position = mapBoundary.randomPosition();
      tries++;
    } while (
      tries < mapBoundary.size()
        && (map.isOccupied(position)
        || ifNot.test(position)));
    if (tries == mapBoundary.size()) return null;
    return newGrass(position);
  }

  public Grass newGrass(Vector2d position) {
    return new Grass(map, position, PLANT_ENERGY);
  }

}
