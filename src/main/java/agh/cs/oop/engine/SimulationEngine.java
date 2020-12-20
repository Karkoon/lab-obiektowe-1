package agh.cs.oop.engine;

import agh.cs.oop.AnimalGenerator;
import agh.cs.oop.GrassGenerator;
import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.Grass;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimulationEngine implements IEngine {

  private final List<Animal> animals;
  private final List<Grass> grasses;
  private final AnimalGenerator generator;
  private final GrassGenerator grassGenerator;
  private final IWorldMap map;

  private final Set<Vector2d> dirtyPositions = new HashSet<>();

  public SimulationEngine(IWorldMap map, int numberOfAnimals, int numberOfGrasses) {
    this.generator = new AnimalGenerator(map);
    this.grassGenerator = new GrassGenerator(map);
    this.map = map;
    this.animals = generator.generateAnimals(numberOfAnimals);
    this.grasses = grassGenerator.generateGrasses(numberOfGrasses);
  }

  @Override
  public void run() {
    cleanUpDeadAnimals();
    moveAnimals();
    feedAnimals();
    tryToProcreateAnimals();
    dirtyPositions.clear();
    regenerateGrass();
  }

  private void feedAnimals() {
    for (Vector2d position : dirtyPositions) {
      Grass grass = map.grassAt(position);
      if (grass != null) {
        Collection<Animal> animals = map.animalsAt(position);
        List<Animal> energiestAnimals = animals.stream().filter(
          a -> a.getEnergy() == (animals.stream().map(Animal::getEnergy).max(Integer::compareTo).get()))
          .collect(Collectors.toList());
        map.pickUpGrass(grass);
        for (Animal animal : energiestAnimals) {
          animal.addEnergy(grass.getEnergy());
        }
      }
    }
  }

  private void regenerateGrass() {
    grasses.add(grassGenerator.newGrass(map.provideMapBoundary().randomPosition()));
    grasses.add(grassGenerator.newGrass(map.provideJungleBoundary().randomPosition()));
  }

  private void moveAnimals() {
    for (int i = 0; i < animals.size(); i++) {
      Animal animal = animals.get(i);
      map.removeAnimalBody(animal);
      animal.move();
      map.place(animal);
      dirtyPositions.add(animal.getPosition());
    }
  }

  public boolean notFinished() {
    return animals.size() != 0;
  }

  private void tryToProcreateAnimals() {
    for (Vector2d position : dirtyPositions) {
      Animal child = generator.tryToProcreateAt(position);
      if (child != null) {
        animals.add(child);
      }
    }
  }

  private void cleanUpDeadAnimals() {
    final Iterator<Animal> each = animals.iterator();
    while (each.hasNext()) {
      Animal animal = each.next();
      if (animal.getEnergy() == 0) {
        each.remove();
        if (!map.removeAnimalBody(animal)) System.out.println("nie działa");
      }
    }
  }

}
