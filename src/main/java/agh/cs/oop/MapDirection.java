package agh.cs.oop;

public enum MapDirection { // TODO: 19.12.2020 remove dir as gui
  NORTH("^", new Vector2d(0, 1)),
  NORTHEAST("/", new Vector2d(1, 1)),
  EAST(">", new Vector2d(1, 0)),
  SOUTHEAST("\\", new Vector2d(1, -1)),
  SOUTH("v", new Vector2d(0, -1)),
  SOUTHWEST("/", new Vector2d(-1, -1)),
  WEST("<", new Vector2d(-1, 0)),
  NORTHWEST("\\", new Vector2d(-1, 1));

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

  private final static int DEGREE_GRANULARITY = 45;

  public int toDegrees() {
    return this.ordinal() * DEGREE_GRANULARITY;
  }
}
