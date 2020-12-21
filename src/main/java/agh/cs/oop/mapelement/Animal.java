package agh.cs.oop.mapelement;

import agh.cs.oop.Genotype;
import agh.cs.oop.MapDirection;
import agh.cs.oop.Vector2d;
import agh.cs.oop.util.Enums;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractMapElement implements Comparable<Animal> {

  private final Genotype genotype;
  private final IWorldMap map;
  private final int MOVE_ENERGY;
  private final int birthEpoch;
  private final List<Animal> children = new ArrayList<>(5);
  private int deathEpoch;
  private MapDirection orientation;
  private int energy;

  public Animal(IWorldMap map, Vector2d initialPosition, int startingEnergy, int moveEnergy, int birthEpoch, Genotype genotype) {
    super(initialPosition);
    this.genotype = genotype;
    this.map = map;
    placeOnMap(map);
    this.MOVE_ENERGY = moveEnergy;
    this.energy = startingEnergy;
    orientation = Enums.getRandomEnum(MapDirection.class);
    this.birthEpoch = birthEpoch;
  }

  public Animal(IWorldMap map, Vector2d initialPosition, int startingEnergy, int moveEnergy, int birthEpoch) {
    this(map, initialPosition, startingEnergy, moveEnergy, birthEpoch, new Genotype());
  }

  public Animal(IWorldMap map, Vector2d initialPosition, int startingEnergy, int moveEnergy) {
    this(map, initialPosition, startingEnergy, moveEnergy, 0);
  }

  public void move() {
    if (energy < MOVE_ENERGY)
      throw new IllegalStateException("Animal has less than required energy to move.");
    for (int i = 0; i < getGenotype().getRandomGene(); i++) {
      rotate();
    }
    moveForward();
  }

  private void rotate() {
    orientation = orientation.next();
  }

  private void moveForward() {
    Vector2d resultPosition;
    resultPosition = position.add(orientation.toUnitVector());
    if (map.canMoveTo(resultPosition)) {
      position = resultPosition;
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
  public int zIndex() {
    return getEnergy();
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
    energy -= MOVE_ENERGY;
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

  public int getBirthEpoch() {
    return birthEpoch;
  }

  public int getNumberOfChildren() {
    return children.size();
  }

  public void addChild(Animal child) {
    children.add(child);
  }

  public void removeChildren() {
    children.clear();
  }

  public int getNumberOfDescendants() {
    return getNumberOfChildren() + children.stream().map(Animal::getNumberOfDescendants).reduce(Integer::sum).orElse(0);
  }

  public int getDeathEpoch() {
    return deathEpoch;
  }

  public void setDeathEpoch(int deathEpoch) {
    this.deathEpoch = deathEpoch;
  }

  public boolean isDead() {
    return energy <= MOVE_ENERGY;
  }
}
