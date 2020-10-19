package agh.cs.lab3;

import agh.cs.lab2.MapDirection;
import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;

public class Animal {
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
    return !(position.x < 0 || position.y < 0 || position.x > 4 || position.y > 4);
  }
}
