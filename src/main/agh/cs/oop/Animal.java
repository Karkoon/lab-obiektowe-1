package agh.cs.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractMapElement {

  private final IWorldMap map;
  private final List<IPositionChangeObserver> positionChangeObserverList = new ArrayList<>();
  private MapDirection orientation = MapDirection.NORTH;

  public Animal(IWorldMap map, Vector2d initialPosition) {
    super(initialPosition);
    this.map = map;
    placeOnMap(map);
  }

  public Animal(IWorldMap map) {
    this(map, new Vector2d(2, 2));
  }

  public void registerPositionChangeObserver(IPositionChangeObserver observer) {
    positionChangeObserverList.add(observer);
  }

  @Override
  public String toString() {
    return orientation.toString();
  }

  public void move(MoveDirection direction) {
    switch (direction) {
      case LEFT, RIGHT -> rotate(direction);
      case FORWARD, BACKWARD -> changePosition(direction);
    }
  }

  private void rotate(MoveDirection direction) {
    switch (direction) {
      case LEFT -> orientation = orientation.previous();
      case RIGHT -> orientation = orientation.next();
      default -> throw new IllegalArgumentException("Illegal direction value. It has to be either LEFT or RIGHT.");
    }
  }

  private void changePosition(MoveDirection direction) {
    Vector2d resultPosition;
    switch (direction) {
      case FORWARD -> resultPosition = position.add(orientation.toUnitVector());
      case BACKWARD -> resultPosition = position.subtract(orientation.toUnitVector());
      default -> throw new IllegalArgumentException("Illegal direction value. It has to be either FORWARD or BACKWARD.");
    }
    if (map.canMoveTo(resultPosition)) {
      positionChangeObserverList.forEach(a -> a.positionChanged(position, resultPosition));
      position = resultPosition;
    }
  }

  private void placeOnMap(IWorldMap map) {
    boolean placeSucceded = map.place(this);
    if (!placeSucceded) {
      throw new IllegalArgumentException(String.format("The position: %s is unavailable", position));
    }
  }
}
