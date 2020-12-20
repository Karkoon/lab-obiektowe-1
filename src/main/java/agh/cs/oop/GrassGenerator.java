package agh.cs.oop;

import agh.cs.oop.mapelement.Grass;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.ArrayList;
import java.util.List;

public class GrassGenerator {

  private final IWorldMap map;

  public GrassGenerator(IWorldMap map) {
    this.map = map;
  }

  public List<Grass> generateGrasses(int n) {
    List<Grass> grasses = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      Vector2d randomPosition;
      do {
        randomPosition = map.provideMapBoundary().randomPosition();
      } while (map.isOccupied(randomPosition));
      grasses.add(newGrass(randomPosition));
    }
    return grasses;
  }

  public Grass newGrass(Vector2d position) {
    return new Grass(map, position);
  }

}
