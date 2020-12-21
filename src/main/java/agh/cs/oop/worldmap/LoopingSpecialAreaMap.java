package agh.cs.oop.worldmap;

import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.Grass;
import agh.cs.oop.mapelement.IMapElement;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
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
    this.mapBoundary = new MapBoundary(leftLowerMapCorner, rightUpperMapCorner);

    Vector2d leftLowerSpecialAreaCorner =
      new Vector2d((xMapSize - xSpecialAreaSize) / 2, (yMapSize - ySpecialAreaSize) / 2);
    Vector2d rightUpperSpecialAreaCorner = leftLowerSpecialAreaCorner.add(new Vector2d(xSpecialAreaSize, ySpecialAreaSize));
    this.jungleBoundary = new MapBoundary(leftLowerSpecialAreaCorner, rightUpperSpecialAreaCorner);
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return true;
  }

  @Override
  public boolean place(Animal animal) {
    animal.setPosition(translate(animal.getPosition()));
    elementsMap.put(animal.getPosition(), animal);
    return true;
  }

  private Vector2d translate(Vector2d animalPos) {
    int translatedX = animalPos.x;
    if (animalPos.x < mapBoundary.getLowerLeft().x)
      translatedX = mapBoundary.getUpperRight().x;
    else if (mapBoundary.getUpperRight().x < animalPos.x)
      translatedX = mapBoundary.getLowerLeft().x;

    int translatedY = animalPos.y;
    if (animalPos.y < mapBoundary.getLowerLeft().y)
      translatedY = mapBoundary.getUpperRight().y;
    else if (mapBoundary.getUpperRight().y < animalPos.y)
      translatedY = mapBoundary.getLowerLeft().y;
    return new Vector2d(translatedX, translatedY);
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
  public IMapBoundary getJungleBoundary() {
    return jungleBoundary;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    return elementsMap.containsKey(position) || grassMap.containsKey(position);
  }

  @Override
  public Collection<IMapElement> objectAt(Vector2d position) {
    Collection<IMapElement> animals = new ArrayList<>(elementsMap.get(position));
    return !animals.isEmpty() ? animals : Collections.singleton(grassMap.get(position));
  }

  @Override
  public IMapBoundary getMapBoundary() {
    return mapBoundary;
  }
}