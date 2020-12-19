package agh.cs.oop;

import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.IMapElement;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AnimalGenerator {

  public static final int PROCREATION_ENERGY_REQUIREMENT = Animal.STARTING_ENERGY / 2;
  private final static Random rand = new Random();
  private final IWorldMap map;
  private List<Animal> animals;

  public AnimalGenerator(IWorldMap map) {
    this.map = map;
  }

  public List<Animal> generateAnimals(int n) {
    if (animals == null) animals = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      Vector2d randomPosition;
      do {
        randomPosition = map.provideMapBoundary().randomPosition();
      } while (!map.canMoveTo(randomPosition));
      animals.add(createAnimal(randomPosition));
    }
    return animals;
  }

  public Animal createAnimal(Vector2d position) {
    return new Animal(map, position);
  }

  public void tryToProcreateAt(Vector2d position) {
    List<IMapElement> topTwoEligibleAnimalsForReproduction = map.objectAt(position)
      .stream()
      .filter(a -> a instanceof Animal && hasSufficientEnergyForProcreation(a))
      .sorted(Comparator.comparingInt(IMapElement::getEnergy))
      .limit(2)
      .collect(Collectors.toList());
    if (topTwoEligibleAnimalsForReproduction.size() == 2) {
      Animal parentA = (Animal) topTwoEligibleAnimalsForReproduction.get(0);
      Animal parentB = (Animal) topTwoEligibleAnimalsForReproduction.get(1);
      Animal child = createAnimalFromParents(parentA, parentB);
      animals.add(child);
    }
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
      new Genotype(parentA.getGenotype(), parentB.getGenotype()));
    child.addEnergy(parentA.spendEnergyOnReproduction() + parentB.spendEnergyOnReproduction());
    return child;
  }

  private boolean hasSufficientEnergyForProcreation(IMapElement mapElement) {
    return mapElement.getEnergy() >= PROCREATION_ENERGY_REQUIREMENT;
  }

  private Vector2d determineChildPosition(Vector2d parentPos) {
    for (MapDirection dir : MapDirection.values()) {
      Vector2d candidatePosition = parentPos.add(dir.toUnitVector());
      if (!map.isOccupied(candidatePosition)) {
        return candidatePosition;
      }
    }
    MapDirection randomDirection = MapDirection.values()[rand.nextInt(MapDirection.values().length)];
    return parentPos.add(randomDirection.toUnitVector());
  }


}
