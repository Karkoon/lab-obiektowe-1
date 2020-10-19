package agh.cs.lab2;

public enum MapDirection {
  NORTH("Północ", new Vector2d(0, 1)),
  SOUTH("Południe", new Vector2d(0, -1)),
  WEST("Zachód", new Vector2d(-1, 0)),
  EAST("Wschód", new Vector2d(1, 0));

  private final String friendlyName;
  private final Vector2d unitVectorDirection;

  MapDirection(String friendlyName, Vector2d unitVectorDirection) {
    this.friendlyName = friendlyName;
    this.unitVectorDirection = unitVectorDirection;
  }

  @Override
  public String toString() {
    return this.friendlyName;
  }

  public MapDirection next() {
    return switch (this) {
      case NORTH -> EAST;
      case EAST -> SOUTH;
      case SOUTH -> WEST;
      case WEST -> NORTH;
    };
  }

  public MapDirection previous() {
    return switch (this) {
      case NORTH -> WEST;
      case WEST -> SOUTH;
      case SOUTH -> EAST;
      case EAST -> NORTH;
    };
  }

  public Vector2d toUnitVector() {
    return unitVectorDirection;
  }

}
