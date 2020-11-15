package agh.cs.oop;


import java.util.HashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

  protected final HashMap<Vector2d, IMapElement> elementsMap;
  private final MapVisualizer mapVisualizer;

  public AbstractWorldMap() {
    elementsMap = new HashMap<>();
    mapVisualizer = new MapVisualizer(this);
  }

  @Override
  public String toString() {
    MapBounds mapBounds = provideMapBounds();
    return mapVisualizer.draw(mapBounds.getLowerLeftCorner(), mapBounds.getRightUpperCorner());
  }

  /**
   * Override to provide bounds of the map fragment to be rendered.
   */
  protected abstract MapBounds provideMapBounds();

  @Override
  public boolean canMoveTo(Vector2d position) {
    return !(objectAt(position) instanceof Animal);
  }

  @Override
  public boolean place(Animal animal) {
    if (canMoveTo(animal.getPosition())) {
      elementsMap.put(animal.getPosition(), animal);
      return true;
    }
    return false;
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
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    IMapElement movingElement = elementsMap.get(oldPosition);
    elementsMap.remove(oldPosition);
    elementsMap.put(newPosition, movingElement);
  }
}
