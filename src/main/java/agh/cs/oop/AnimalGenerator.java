package agh.cs.oop;

import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// TODO: 20.12.2020 klasa chyba ok
public class AnimalGenerator {

  public static final int PROCREATION_ENERGY_REQUIREMENT = Animal.STARTING_ENERGY / 2;
  private final IWorldMap map;

  public AnimalGenerator(IWorldMap map) {
    this.map = map;
  }

  public List<Animal> generateAnimals(int n) {
    List<Animal> animals = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      Vector2d randomPosition;
      do {
        randomPosition = map.provideMapBoundary().randomPosition();
      } while (map.isOccupied(randomPosition));
      animals.add(createAnimal(randomPosition));
    }
    return animals;
  }

  public Animal createAnimal(Vector2d position) {
    return new Animal(map, position);
  }

  public Animal tryToProcreateAt(Vector2d position) {
    List<Animal> topTwoEligibleAnimalsForReproduction = map.animalsAt(position)
      .stream()
      .sorted()
      .filter(this::hasSufficientEnergyForProcreation)
      .limit(2)
      .collect(Collectors.toList()); // TODO: 20.12.2020 zastanowić się czy to faktycznie działa
    if (topTwoEligibleAnimalsForReproduction.size() == 2) {
      Animal parentA = topTwoEligibleAnimalsForReproduction.get(0);
      Animal parentB = topTwoEligibleAnimalsForReproduction.get(1);
      Animal child = createAnimalFromParents(parentA, parentB);
      System.out.println("CHILD BIRTHED AT" + child.getPosition());
      return child;
    }
    return null;
  }

  private Animal createAnimalFromParents(Animal parentA, Animal parentB) {
    if (!(hasSufficientEnergyForProcreation(parentA) && hasSufficientEnergyForProcreation(parentB))) {
      throw new IllegalStateException(
        String.format("Parents don't meet the sufficient energy requirement %d - they have only ParentA: %d, ParentB: %d units of energy.",
          PROCREATION_ENERGY_REQUIREMENT, parentA.getEnergy(), parentB.getEnergy()));
    }
    Animal child = new Animal(
      map,
      determineChildPosition(parentA.getPosition()),
      new Genotype(parentA.getGenotype(), parentB.getGenotype()),
      parentA.spendEnergyOnReproduction() + parentB.spendEnergyOnReproduction()
    );
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
