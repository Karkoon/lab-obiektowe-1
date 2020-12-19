package agh.cs.oop.worldmap;

import agh.cs.oop.IPositionChangeObserver;
import agh.cs.oop.MapVisualizer;
import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.IMapElement;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;


public class LoopingSpecialAreaMap implements IWorldMap, IPositionChangeObserver {

  private final IMapBoundary mapBoundary;
  private final IMapBoundary specialAreaBoundary;
  private final MapVisualizer mapVisualizer;

  private final Multimap<Vector2d, IMapElement> elementsMap = ArrayListMultimap.create();

  public LoopingSpecialAreaMap(int xMapSize, int yMapSize, int xSpecialAreaSize, int ySpecialAreaSize) {
    Vector2d leftLowerMapCorner = new Vector2d(0, 0);
    Vector2d rightUpperMapCorner = new Vector2d(xMapSize, yMapSize);
    this.mapBoundary = new NondynamicMapBoundary(leftLowerMapCorner, rightUpperMapCorner);

    Vector2d leftLowerSpecialAreaCorner = new Vector2d(xMapSize / 2, yMapSize / 2);
    Vector2d rightUpperSpecialAreaCorner = leftLowerSpecialAreaCorner.add(new Vector2d(xSpecialAreaSize, ySpecialAreaSize));
    this.specialAreaBoundary = new NondynamicMapBoundary(leftLowerSpecialAreaCorner, rightUpperSpecialAreaCorner);

    this.mapVisualizer = new MapVisualizer(this);
  }


  @Override
  public String toString() {
    return mapVisualizer.draw(mapBoundary.getLowerLeftCorner(), mapBoundary.getUpperRightCorner());
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return true;
  }

  @Override
  public boolean place(Animal animal) {
    elementsMap.put(animal.getPosition(), animal);
    animal.addObserver(this);
    return true;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    return elementsMap.containsKey(position);
  }

  @Override
  public Collection<IMapElement> objectAt(Vector2d position) {
    return elementsMap.get(position);
  }

  @Override
  public IMapBoundary provideMapBoundary() {
    return mapBoundary;
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, IMapElement changedElement) {
    elementsMap.remove(oldPosition, changedElement);
    elementsMap.put(translateToBoundary(newPosition), changedElement);
  }

  private Vector2d translateToBoundary(Vector2d pos) {
    int xRes = Math.abs(pos.x % mapBoundary.getUpperRightCorner().x);
    int yRes = Math.abs(pos.y % mapBoundary.getUpperRightCorner().y);
    return new Vector2d(xRes, yRes);
  }
}

