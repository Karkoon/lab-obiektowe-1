package agh.cs.oop;

import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class AnimalGenerator {

  private final IWorldMap map;
  private final int START_ENERGY;
  private final int MOVE_ENERGY;
  private final int PROCREATION_ENERGY_REQUIREMENT;

  public AnimalGenerator(IWorldMap map, int startEnergy, int moveEnergy) {
    this.map = map;
    this.START_ENERGY = startEnergy;
    this.MOVE_ENERGY = moveEnergy;
    this.PROCREATION_ENERGY_REQUIREMENT = START_ENERGY / 2;
  }

  public List<Animal> generateAnimals(int n) {
    List<Animal> animals = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      Vector2d randomPosition;
      do {
        randomPosition = map.getMapBoundary().randomPosition();
      } while (map.isOccupied(randomPosition));
      animals.add(createAnimal(randomPosition));
    }
    return animals;
  }

  public Animal createAnimal(Vector2d position) {
    return new Animal(map, position, START_ENERGY, MOVE_ENERGY);
  }

  public Animal tryToProcreateAt(Vector2d position, int epoch) {
    List<Animal> topTwoEligibleAnimalsForReproduction = map.animalsAt(position)
      .stream()
      .sorted()
      .filter(this::hasSufficientEnergyForProcreation)
      .limit(2)
      .collect(Collectors.toList());
    if (topTwoEligibleAnimalsForReproduction.size() == 2) {
      Animal parentA = topTwoEligibleAnimalsForReproduction.get(0);
      Animal parentB = topTwoEligibleAnimalsForReproduction.get(1);
      return createAnimalFromParents(parentA, parentB, epoch);
    }
    return null;
  }

  private Animal createAnimalFromParents(Animal parentA, Animal parentB, int epoch) {
    if (!(hasSufficientEnergyForProcreation(parentA) && hasSufficientEnergyForProcreation(parentB))) {
      throw new IllegalStateException(
        String.format("Parents don't meet the sufficient energy requirement %d - they have only ParentA: %d, ParentB: %d units of energy.",
          PROCREATION_ENERGY_REQUIREMENT, parentA.getEnergy(), parentB.getEnergy()));
    }
    Animal child = new Animal(
      map,
      determineChildPosition(parentA.getPosition()),
      parentA.spendEnergyOnReproduction() + parentB.spendEnergyOnReproduction(),
      MOVE_ENERGY,
      epoch,
      new Genotype(parentA.getGenotype(), parentB.getGenotype()));
    parentA.addChild(child);
    parentB.addChild(child);
    return child;
  }

  private boolean hasSufficientEnergyForProcreation(Animal animal) {
    return animal.getEnergy() >= PROCREATION_ENERGY_REQUIREMENT;
  }

  private Vector2d determineChildPosition(Vector2d parentPos) {
    for (MapDirection dir : MapDirection.values()) {
      Vector2d candidatePosition = parentPos.add(dir.toUnitVector());
      if (!map.isOccupied(candidatePosition)) {
        return candidatePosition;
      }
    }
    MapDirection randomDirection = MapDirection.values()[ThreadLocalRandom.current().nextInt(MapDirection.values().length)];
    return parentPos.add(randomDirection.toUnitVector());
  }

}
