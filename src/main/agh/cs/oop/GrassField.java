package agh.cs.oop;

import java.util.Random;

public class GrassField extends AbstractWorldMap {

  public GrassField(int grassAmount) {
    super();
    generateGrass(grassAmount);
  }

  @Override
  protected MapBounds provideMapBounds() {
    Vector2d lowerLeftCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    Vector2d upperRightCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    for (Vector2d position : elementsMap.keySet()) {
      lowerLeftCorner = position.lowerLeft(lowerLeftCorner);
      upperRightCorner = position.upperRight(upperRightCorner);
    }
    return new MapBounds(lowerLeftCorner, upperRightCorner);
  }

  @Override
  public boolean place(Animal animal) {
    removeAndRegenerateGrassIfPresentAt(animal.getPosition());
    return super.place(animal);
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    removeAndRegenerateGrassIfPresentAt(newPosition);
    super.positionChanged(oldPosition, newPosition);
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
      elementsMap.put(potentialPosition, new Grass(potentialPosition));
    }
  }
}
