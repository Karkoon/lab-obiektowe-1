package agh.cs.oop.mapelement;

import agh.cs.oop.Genotype;
import agh.cs.oop.MapDirection;
import agh.cs.oop.MoveDirection;
import agh.cs.oop.Vector2d;
import agh.cs.oop.util.Enums;
import agh.cs.oop.worldmap.IWorldMap;

public class Animal extends AbstractMapElement implements Comparable<Animal> {

  public static final int STARTING_ENERGY = 50; // czy coś

  private final Genotype genotype;
  private final IWorldMap map;

  private MapDirection orientation = Enums.getRandomEnum(MapDirection.class); // random orientation
  private int energy;

  public Animal(IWorldMap map, Vector2d initialPosition, Genotype genotype, int startingEnergy) {
    super(initialPosition);
    this.genotype = genotype;
    this.map = map;
    placeOnMap(map);
    this.energy = startingEnergy;
  }

  public Animal(IWorldMap map, Vector2d initialPosition, Genotype genotype) {
    this(map, initialPosition, genotype, STARTING_ENERGY);
  }

  public Animal(IWorldMap map, Vector2d initialPosition) {
    this(map, initialPosition, new Genotype());
  }

  public Animal(IWorldMap map) {
    this(map, new Vector2d(2, 2));
  }

  public MapDirection getOrientation() {
    return orientation;
  }

  @Override
  public String toString() {
    return orientation.toString();
  }

  // ruch to obrót i ruch
  public void move() { //
    for (int i = 0; i < getGenotype().getRandomGene(); i++) {
      rotate(MoveDirection.RIGHT);
    }
    changePosition(MoveDirection.FORWARD);
  }

  public void forceMove(MoveDirection direction) {
    switch (direction) {
      case LEFT, RIGHT -> rotate(direction);
      default -> changePosition(direction);
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
      position = resultPosition; // tu do modyfikacji jeśli mapa ma móc się zawijać
      spendEnergyOnMovement();
    }
  }

  private void placeOnMap(IWorldMap map) {
    boolean placeSucceded = map.place(this);
    if (!placeSucceded) {
      throw new IllegalArgumentException(String.format("The position: %s is unavailable", position));
    }
  }

  @Override
  public int getEnergy() {
    return this.energy;
  }

  public int spendEnergyOnReproduction() {
    int energyReduction = energy / 4;
    energy -= energyReduction;
    return energyReduction;
  }

  private void spendEnergyOnMovement() {
    energy -= 1;
  }

  public void addEnergy(int additionalEnergy) {
    this.energy += additionalEnergy;
  }

  public Genotype getGenotype() {
    return this.genotype;
  }

  @Override
  public int compareTo(Animal o) {
    return this == o ? 0 : this.getEnergy() - o.getEnergy();
  }
}
