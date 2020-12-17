package agh.cs.oop.worldmap;

import agh.cs.oop.IPositionChangeObserver;
import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.IMapElement;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver, IMapBoundary {

  private final SortedSet<Pair<Vector2d, IMapElement>> sortedByX = new TreeSet<>((a, b) -> {
    var aPos = a.first;
    var bPos = b.first;
    if (aPos.x > bPos.x
      || (aPos.x == bPos.x && aPos.y > bPos.y)
      || (aPos.x == bPos.x && aPos.y == bPos.y && a.second.zIndex() > b.second.zIndex())) {
      return 1;
    } else if (aPos.x == bPos.x && aPos.y == bPos.y && a.second.zIndex() == b.second.zIndex()) {
      return 0;
    } else {
      return -1;
    }
  });

  private final SortedSet<Pair<Vector2d, IMapElement>> sortedByY = new TreeSet<>((a, b) -> {
    var aPos = a.first;
    var bPos = b.first;
    if (aPos.y > bPos.y
      || (aPos.y == bPos.y && aPos.x > bPos.x)
      || (aPos.y == bPos.y && aPos.x == bPos.x && a.second.zIndex() > b.second.zIndex())) {
      return 1;
    } else if (aPos.y == bPos.y && aPos.x == bPos.x && a.second.zIndex() == b.second.zIndex()) {
      return 0;
    } else {
      return -1;
    }
  });

  public Vector2d getLowerLeftCorner() {
    return new Vector2d(sortedByX.first().first.x, sortedByY.first().first.y);
  }

  public Vector2d getUpperRightCorner() {
    return new Vector2d(sortedByX.last().first.x, sortedByY.last().first.y);
  }

  public void addToMapBoundary(IMapElement element) {
    sortedByX.removeIf(a -> a.first.equals(element.getPosition()));
    sortedByY.removeIf(a -> a.first.equals(element.getPosition()));
    var pair = new Pair<>(element.getPosition(), element);
    sortedByX.add(pair);
    sortedByY.add(pair);
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, IMapElement changedElement) {
    sortedByX.removeIf(a -> a.first.equals(oldPosition));
    sortedByY.removeIf(a -> a.first.equals(oldPosition));
    addToMapBoundary(changedElement);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MapBoundary that = (MapBoundary) o;
    return Objects.equals(getLowerLeftCorner(), that.getLowerLeftCorner()) &&
      Objects.equals(getUpperRightCorner(), that.getUpperRightCorner());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLowerLeftCorner(), getUpperRightCorner());
  }

  private static class Pair<T, U> {
    final T first;
    final U second;

    Pair(T firstElement, U secondElement) {
      this.first = firstElement;
      this.second = secondElement;
    }
  }
}
