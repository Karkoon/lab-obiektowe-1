package agh.cs.oop.worldmap;

import agh.cs.oop.Vector2d;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class NondynamicMapBoundary implements IMapBoundary {

  private final Vector2d lowerLeftCorner;
  private final Vector2d upperRightCorner;

  NondynamicMapBoundary(Vector2d lowerLeftCorner, Vector2d upperRightCorner) {
    this.lowerLeftCorner = lowerLeftCorner;
    this.upperRightCorner = upperRightCorner;
  }

  @Override
  public Vector2d getLowerLeftCorner() {
    return lowerLeftCorner;
  }

  @Override
  public Vector2d getUpperRightCorner() {
    return upperRightCorner;
  }

  @Override
  public Vector2d randomPosition() {
    return new Vector2d(
      ThreadLocalRandom.current().nextInt(lowerLeftCorner.x, upperRightCorner.x),
      ThreadLocalRandom.current().nextInt(lowerLeftCorner.y, upperRightCorner.y));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NondynamicMapBoundary that = (NondynamicMapBoundary) o;
    return Objects.equals(lowerLeftCorner, that.lowerLeftCorner) &&
      Objects.equals(upperRightCorner, that.upperRightCorner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lowerLeftCorner, upperRightCorner);
  }
}
