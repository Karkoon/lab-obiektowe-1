package agh.cs.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {

  private final Vector2d lowerLeftCorner;
  private final Vector2d upperRightCorner;
  private final List<Animal> animals;
  private final MapVisualizer mapVisualizer;

  public RectangularMap(int width, int height) {
    mapVisualizer = new MapVisualizer(this);
    lowerLeftCorner = new Vector2d(0, 0);
    upperRightCorner = new Vector2d(width - 1, height - 1);
    animals = new ArrayList<>();
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return isInBounds(position) && !isOccupied(position);
  }

  @Override
  public boolean place(Animal animal) {
    if (canMoveTo(animal.getPosition())) {
      animals.add(animal);
      return true;
    }
    return false;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    return animals.stream()
      .anyMatch(animal -> isOnPosition(animal, position));
  }

  @Override
  public Object objectAt(Vector2d position) {
    return animals.stream()
      .filter(animal -> isOnPosition(animal, position))
      .findFirst()
      .orElse(null);
  }

  private boolean isInBounds(Vector2d position) {
    return lowerLeftCorner.precedes(position) && upperRightCorner.follows(position);
  }

  private boolean isOnPosition(Animal animal, Vector2d position) {
    return animal.getPosition().equals(position);
  }

  @Override
  public String toString() {
    return mapVisualizer.draw(lowerLeftCorner, upperRightCorner);
  }
}
