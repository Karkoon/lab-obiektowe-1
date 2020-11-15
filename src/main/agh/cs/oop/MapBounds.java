package agh.cs.oop;

import java.util.Objects;

public class MapBounds {
  private final Vector2d lowerLeftCorner;
  private final Vector2d rightUpperCorner;

  public MapBounds(Vector2d lowerLeftCorner, Vector2d rightUpperCorner) {
    this.lowerLeftCorner = lowerLeftCorner;
    this.rightUpperCorner = rightUpperCorner;
  }

  public Vector2d getLowerLeftCorner() {
    return lowerLeftCorner;
  }

  public Vector2d getRightUpperCorner() {
    return rightUpperCorner;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MapBounds mapBounds = (MapBounds) o;
    return Objects.equals(lowerLeftCorner, mapBounds.lowerLeftCorner) &&
      Objects.equals(rightUpperCorner, mapBounds.rightUpperCorner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lowerLeftCorner, rightUpperCorner);
  }
}
