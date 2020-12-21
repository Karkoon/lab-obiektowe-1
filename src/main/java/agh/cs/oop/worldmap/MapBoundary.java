package agh.cs.oop.worldmap;

import agh.cs.oop.util.Vector2d;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public class MapBoundary implements IMapBoundary {

  private final Vector2d lowerLeftCorner;
  private final Vector2d upperRightCorner;

  private final Deque<Vector2d> positions;

  MapBoundary(Vector2d lowerLeftCorner, Vector2d upperRightCorner) {
    this.lowerLeftCorner = lowerLeftCorner;
    this.upperRightCorner = upperRightCorner;

    List<Vector2d> cartesianProduct = new ArrayList<>();
    for (int i = lowerLeftCorner.x; i < upperRightCorner.x; i++) {
      for (int j = lowerLeftCorner.y; j < upperRightCorner.y; j++) {
        cartesianProduct.add(new Vector2d(i, j));
      }
    }
    Collections.shuffle(cartesianProduct);
    this.positions = new ArrayDeque<>(cartesianProduct);
  }

  @Override
  public Vector2d getLowerLeft() {
    return lowerLeftCorner;
  }

  @Override
  public Vector2d getUpperRight() {
    return upperRightCorner;
  }

  @Override
  public Vector2d randomPosition() {
    Vector2d res = this.positions.pop();
    this.positions.offer(res);
    return res;
  }

  @Override
  public int size() {
    return positions.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MapBoundary that = (MapBoundary) o;
    return Objects.equals(lowerLeftCorner, that.lowerLeftCorner) &&
      Objects.equals(upperRightCorner, that.upperRightCorner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lowerLeftCorner, upperRightCorner);
  }
}
