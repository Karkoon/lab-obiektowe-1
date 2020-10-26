package agh.cs.oop;

public class Animal {
  private final Vector2d lowerBound = new Vector2d(0, 0);
  private final Vector2d upperBound = new Vector2d(4, 4);
  private MapDirection orientation = MapDirection.NORTH;
  private Vector2d position = new Vector2d(2, 2);

  @Override
  public String toString() {
    return String.format("Orientacja: %s, Pozycja: %s", orientation, position);
  }

  public void move(MoveDirection direction) {
    switch (direction) {
      case LEFT -> orientation = orientation.previous();
      case RIGHT -> orientation = orientation.next();
      case FORWARD -> {
        Vector2d result = position.add(orientation.toUnitVector());
        if (isInBounds(result)) position = result;
      }
      case BACKWARD -> {
        Vector2d result = position.subtract(orientation.toUnitVector());
        if (isInBounds(result)) position = result;
      }
    }
  }

  private boolean isInBounds(Vector2d position) {
    return lowerBound.precedes(position) && upperBound.follows(position);
  }
}
