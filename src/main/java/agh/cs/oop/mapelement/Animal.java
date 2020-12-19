package agh.cs.oop.mapelement;

import agh.cs.oop.Genotype;
import agh.cs.oop.MapDirection;
import agh.cs.oop.MoveDirection;
import agh.cs.oop.Vector2d;
import agh.cs.oop.worldmap.IWorldMap;

public class Animal extends AbstractMapElement {

  public static final int STARTING_ENERGY = 20; // czy coś
  private final static int Z_INDEX = 10;
  private final Genotype genotype;
  private final IWorldMap map;
  private MapDirection orientation = MapDirection.NORTH; // random orientation
  private int energy = STARTING_ENERGY;

  public Animal(IWorldMap map, Vector2d initialPosition, Genotype genotype) {
    super(initialPosition);
    this.genotype = genotype;
    this.map = map;
    placeOnMap(map);
  }

  public Animal(IWorldMap map, Vector2d initialPosition) {
    this(map, initialPosition, new Genotype());
  }


  public Animal(IWorldMap map) {
    this(map, new Vector2d(2, 2));
  }

  @Override
  public String toString() {
    return orientation.toString();
  }

  public void move() {
    MoveDirection direction = MoveDirection.values()[getGenotype().getRandomGene()];
    switch (direction) {
      case LEFT, RIGHT -> rotate(direction);
      case FORWARD, BACKWARD -> changePosition(direction);
    }
  }

  public void forceMove(MoveDirection direction) {
    switch (direction) {
      case LEFT, RIGHT -> rotate(direction);
      case FORWARD, BACKWARD -> changePosition(direction);
    }
  }

  private void rotate(MoveDirection direction) {
    switch (direction) {
      case LEFT -> orientation = orientation.previous();
      case RIGHT -> orientation = orientation.next();
      default -> throw new IllegalArgumentException("Illegal direction value. It has to be either LEFT or RIGHT.");
    }
  }

  private void changePosition(MoveDirection direction) {
    Vector2d resultPosition;
    switch (direction) {
      case FORWARD -> resultPosition = position.add(orientation.toUnitVector());
      case BACKWARD -> resultPosition = position.subtract(orientation.toUnitVector());
      default -> throw new IllegalArgumentException("Illegal direction value. It has to be either FORWARD or BACKWARD.");
    }
    if (map.canMoveTo(resultPosition)) {
      Vector2d oldPosition = position;
      position = resultPosition; // tu do modyfikacji jeśli mapa ma móc się zawijać
      notifyObservers(oldPosition); // można byłoby to zrobić tak, że mapa mogłaby zwrócić proponowaną pozycję
    }
  }

  private void placeOnMap(IWorldMap map) {
    boolean placeSucceded = map.place(this);
    if (!placeSucceded) {
      throw new IllegalArgumentException(String.format("The position: %s is unavailable", position));
    }
  }

  @Override
  public int zIndex() {
    return Z_INDEX;
  }

  @Override
  public int getEnergy() {
    return this.energy;
  }

  public int spendEnergyOnReproduction() {
    int energyReduction = STARTING_ENERGY / 2;
    energy -= energyReduction;
    return energyReduction;
  }

  public void addEnergy(int additionalEnergy) {
    this.energy += additionalEnergy;
  }

  public Genotype getGenotype() {
    return this.genotype;
  }
}
