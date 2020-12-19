package agh.cs.oop;

import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.IMapElement;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AnimalGenerator {

  public static final int PROCREATION_ENERGY_REQUIREMENT = Animal.STARTING_ENERGY / 2; //połowa energii początkowej startEnergy?
  private final static Random rand = new Random();
  private final IWorldMap map;
  private ArrayList<Animal> animals;

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

  public void tryToProcreateAt(Vector2d position) { // TODO: 19.12.2020 rozmnażają się te, które mają największą energię
    Animal parentA = (Animal) map.objectAt(position)
      .stream()
      .filter(a -> a instanceof Animal && a.getEnergy() >= PROCREATION_ENERGY_REQUIREMENT)
      .max(Comparator.comparingInt(IMapElement::getEnergy)).orElse(null);
    if (parentA != null) {
      Animal parentB = (Animal) map.objectAt(parentA.getPosition())
        .stream()
        .filter(a -> a instanceof Animal && a != parentA && a.getEnergy() >= PROCREATION_ENERGY_REQUIREMENT)
        .max(Comparator.comparingInt(IMapElement::getEnergy)).orElse(null);
      if (parentB != null) {
        determineChildPosition(parentA.getPosition());
        Animal child = createAnimalFromParents(parentA, parentB);
        animals.add(child);
      }
    }
  }

  private Animal createAnimalFromParents(Animal parentA, Animal parentB) {
    if (parentA.getEnergy() >= PROCREATION_ENERGY_REQUIREMENT && parentB.getEnergy() >= PROCREATION_ENERGY_REQUIREMENT) {
      parentA.spendEnergyOnReproduction();
      parentB.spendEnergyOnReproduction();
      Vector2d childPosition = determineChildPosition(parentA.getPosition());
      Animal child = new Animal(map, childPosition, new Genotype(parentA.getGenotype(), parentB.getGenotype()));
      child.forceMove(MoveDirection.LEFT);
      child.addEnergy(parentA.getEnergy() / 4 + parentB.getEnergy() / 4);
    }
    return null; // TODO: 19.12.2020
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
