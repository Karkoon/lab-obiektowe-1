package agh.cs.oop.worldmap;

import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.Grass;
import agh.cs.oop.mapelement.IMapElement;

import java.util.Random;

public class GrassField extends AbstractWorldMap {

  private final MapBoundary mapBoundary;

  public GrassField(int grassAmount) {
    this.mapBoundary = new MapBoundary();
    generateGrass(grassAmount);
  }


  @Override
  public IMapBoundary provideMapBoundary() {
    return mapBoundary;
  }

  @Override
  public boolean place(Animal animal) {
    removeAndRegenerateGrassIfPresentAt(animal.getPosition());
    animal.addObserver(mapBoundary);
    mapBoundary.addToMapBoundary(animal);
    return super.place(animal);
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, IMapElement changedElement) {
    removeAndRegenerateGrassIfPresentAt(newPosition);
    super.positionChanged(oldPosition, newPosition, changedElement);
  }

  private void removeAndRegenerateGrassIfPresentAt(Vector2d position) {
    IMapElement grass = elementsMap.get(position);
    if (grass != null) {
      generateGrass(1);
      elementsMap.remove(position);
    }
  }

  private void generateGrass(int grassAmount) {
    int upperBound = (int) Math.round(Math.sqrt(10 * grassAmount));
    Random rand = new Random();
    for (int i = 0; i < grassAmount; i++) {
      Vector2d potentialPosition;
      do {
        potentialPosition = new Vector2d(rand.nextInt(upperBound), rand.nextInt(upperBound));
      } while (isOccupied(potentialPosition));
      Grass grass = new Grass(potentialPosition);
      grass.addObserver(mapBoundary);
      mapBoundary.addToMapBoundary(grass);
      elementsMap.put(potentialPosition, grass);
    }
  }
}
