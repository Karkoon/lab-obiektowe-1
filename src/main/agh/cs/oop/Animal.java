package agh.cs.oop;

public class Animal {
  private final IWorldMap map;
  private MapDirection orientation = MapDirection.NORTH;
  private Vector2d position;

  public Animal(IWorldMap map, Vector2d initialPosition) {
    this.map = map;
    position = initialPosition;
    placeOnMap(map);
  }

  public Animal(IWorldMap map) {
    this(map, new Vector2d(2, 2));
  }

  public Vector2d getPosition() {
    return position;
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
