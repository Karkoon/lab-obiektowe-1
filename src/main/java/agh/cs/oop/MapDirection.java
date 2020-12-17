package agh.cs.oop;

public enum MapDirection {
  NORTH("^", new Vector2d(0, 1)),
  EAST(">", new Vector2d(1, 0)),
  SOUTH("v", new Vector2d(0, -1)),
  WEST("<", new Vector2d(-1, 0));

  private final String friendlyName;
  private final Vector2d unitVector;

  MapDirection(String friendlyName, Vector2d unitVector) {
    this.friendlyName = friendlyName;
    this.unitVector = unitVector;
  }

  @Override
  public String toString() {
    return this.friendlyName;
  }

  public MapDirection next() {
    return values()[(this.ordinal() + 1) % values().length];
  }

  public MapDirection previous() {
    int index = (this.ordinal() - 1) % values().length;
    if (index < 0) index += values().length;
    return values()[index];
  }

  public Vector2d toUnitVector() {
    return unitVector;
  }
}
