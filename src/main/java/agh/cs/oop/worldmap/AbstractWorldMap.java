package agh.cs.oop.worldmap;

import agh.cs.oop.IPositionChangeObserver;
import agh.cs.oop.MapVisualizer;
import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.IMapElement;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

  protected final HashMap<Vector2d, IMapElement> elementsMap;
  private final MapVisualizer mapVisualizer;

  public AbstractWorldMap() {
    this.elementsMap = new LinkedHashMap<>();
    this.mapVisualizer = new MapVisualizer(this);
  }

  public abstract IMapBoundary provideMapBoundary();

  @Override
  public String toString() {
    IMapBoundary mapBoundary = provideMapBoundary();
    return mapVisualizer.draw(mapBoundary.getLowerLeftCorner(), mapBoundary.getUpperRightCorner());
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return !(objectAt(position) instanceof Animal);
  }

  @Override
  public boolean place(Animal animal) {
    if (canMoveTo(animal.getPosition())) {
      elementsMap.put(animal.getPosition(), animal);
      animal.addObserver(this);
      return true;
    }
    throw new IllegalArgumentException(String.format("%s is unavailable", animal.getPosition().toString()));
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    return elementsMap.containsKey(position);
  }

  @Override
  public Object objectAt(Vector2d position) {
    return elementsMap.get(position);
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, IMapElement changedElement) {
    elementsMap.remove(oldPosition);
    elementsMap.put(newPosition, changedElement);
  }
}
