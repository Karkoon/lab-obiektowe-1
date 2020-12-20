package agh.cs.oop.worldmap;

import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.Grass;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class LoopingSpecialAreaMap implements IWorldMap {

  private final IMapBoundary mapBoundary;
  private final IMapBoundary jungleBoundary;

  private final Multimap<Vector2d, Animal> elementsMap = ArrayListMultimap.create();
  private final Map<Vector2d, Grass> grassMap = new HashMap<>();

  public LoopingSpecialAreaMap(int xMapSize, int yMapSize, int xSpecialAreaSize, int ySpecialAreaSize) {
    Vector2d leftLowerMapCorner = new Vector2d(0, 0);
    Vector2d rightUpperMapCorner = new Vector2d(xMapSize, yMapSize);
    this.mapBoundary = new NondynamicMapBoundary(leftLowerMapCorner, rightUpperMapCorner);

    Vector2d leftLowerSpecialAreaCorner =
      new Vector2d(xMapSize / 2 - xSpecialAreaSize / 2, yMapSize / 2 - ySpecialAreaSize / 2);
    Vector2d rightUpperSpecialAreaCorner = leftLowerSpecialAreaCorner.add(new Vector2d(xSpecialAreaSize, ySpecialAreaSize));
    this.jungleBoundary = new NondynamicMapBoundary(leftLowerSpecialAreaCorner, rightUpperSpecialAreaCorner);
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return isInBounds(position);
  }

  private boolean isInBounds(Vector2d position) {
    return mapBoundary.getLowerLeftCorner().precedes(position)
      && mapBoundary.getUpperRightCorner().follows(position);
  }

  @Override
  public boolean place(Animal element) {
    elementsMap.put(element.getPosition(), element);
    return true;
  }

  @Override
  public boolean removeAnimalBody(Animal animal) {
    return elementsMap.remove(animal.getPosition(), animal);
  }
  @Override
  public boolean pickUpGrass(Grass grass) {
    return grassMap.remove(grass.getPosition(), grass);
  }

  @Override
  public boolean plant(Grass grass) {
    grassMap.put(grass.getPosition(), grass);
    return true;
  }

  @Override
  public Collection<Animal> animalsAt(Vector2d position) {
    return elementsMap.get(position);
  }

  @Override
  public Grass grassAt(Vector2d position) {
    return grassMap.get(position);
  }

  @Override
  public IMapBoundary provideJungleBoundary() {
    return jungleBoundary;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    return elementsMap.containsKey(position) || grassMap.containsKey(position);
  }

  @Override
  public Collection objectAt(Vector2d position) {
    Collection animals = elementsMap.get(position);
    return !animals.isEmpty() ? animals : Collections.singleton(grassMap.get(position));
  }

  @Override
  public IMapBoundary provideMapBoundary() {
    return mapBoundary;
  }

  private Vector2d translateToBoundary(Vector2d pos) {
    int xRes = Math.abs(pos.x % mapBoundary.getUpperRightCorner().x); // TODO: 19.12.2020 fixme
    int yRes = Math.abs(pos.y % mapBoundary.getUpperRightCorner().y);
    return new Vector2d(xRes, yRes);
  }

}