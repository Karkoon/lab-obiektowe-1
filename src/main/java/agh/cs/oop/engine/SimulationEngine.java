package agh.cs.oop.engine;

import agh.cs.oop.mapelement.AnimalGenerator;
import agh.cs.oop.mapelement.GrassGenerator;
import agh.cs.oop.util.Vector2d;
import agh.cs.oop.app.IEpochObserver;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.Grass;
import agh.cs.oop.worldmap.IMapBoundary;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimulationEngine implements IEngine {

  private final List<Animal> animals;
  private final List<Grass> grasses;

  private final AnimalGenerator generator;
  private final GrassGenerator grassGenerator;


  private final IWorldMap map;

  private final List<Animal> deadAnimals;
  private final Set<Vector2d> dirtyPositions = new HashSet<>();
  private final SimulationStats simulationStats;
  private List<IEpochObserver> epochDurationObservers = new ArrayList<>(1);
  private int epoch = 0;

  public SimulationEngine(IWorldMap map, int numberOfAnimals, int numberOfGrasses, int startEnergy, int plantEnergy, int moveEnergy) {
    this.generator = new AnimalGenerator(map, startEnergy, moveEnergy);
    this.grassGenerator = new GrassGenerator(map, plantEnergy);
    this.map = map;
    this.animals = generator.generateAnimals(numberOfAnimals);
    this.grasses = grassGenerator.generateGrasses(numberOfGrasses);
    this.simulationStats = new SimulationStats(numberOfAnimals, numberOfGrasses, startEnergy);
    animals.forEach(a -> this.simulationStats.addGenotype(a.getGenotype()));
    this.deadAnimals = new ArrayList<>();
  }

  public void registerEpochObserver(IEpochObserver observer) {
    epochDurationObservers.add(observer);
  }

  private void notifyEpochObservers() {
    epochDurationObservers.forEach(IEpochObserver::epochPassed);
  }

  public void removeEpochObserver(IEpochObserver observer) {
    epochDurationObservers = epochDurationObservers.stream()
      .filter(Predicate.not(a -> a.equals(observer)))
      .collect(Collectors.toList());
  }

  @Override
  public void run() {
    cleanUpDeadAnimals();
    moveAnimals();
    feedAnimals();
    tryToProcreateAnimals();
    dirtyPositions.clear();
    regenerateGrass();
    epoch++;
    notifyEpochObservers();
    simulationStats.updateStatsOnEpochEnd(animals, deadAnimals);
    deadAnimals.clear();
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
        simulationStats.decrementNumberOfGrasses();
        for (Animal animal : energiestAnimals) {
          animal.addEnergy(grass.getEnergy() / energiestAnimals.size());
        }
      }
    }
  }

  private void regenerateGrass() {
    IMapBoundary jungle = map.getJungleBoundary();
    Grass jungleGrass = grassGenerator.generateGrassOn(jungle);
    if (jungleGrass != null) {
      grasses.add(jungleGrass);
      simulationStats.incrementNumberOfGrasses();
    }
    Grass grass = grassGenerator.generateGrassOnUnless(
      map.getMapBoundary(),
      pos -> pos.follows(jungle.getLowerLeft()) && pos.precedes(jungle.getUpperRight()));
    if (grass != null) {
      grasses.add(grass);
      simulationStats.incrementNumberOfGrasses();
    }
  }

  private void moveAnimals() {
    animals.forEach(animal -> {
      map.removeAnimalBody(animal);
      animal.move();
      map.place(animal);
      dirtyPositions.add(animal.getPosition());
    });
  }

  public boolean notFinished() {
    return animals.size() != 0;
  }

  private void tryToProcreateAnimals() {
    dirtyPositions.stream()
      .map(position -> generator.tryToProcreateAt(position, epoch))
      .filter(Objects::nonNull).forEach(child -> {
      animals.add(child);
      simulationStats.addGenotype(child.getGenotype());
      simulationStats.incrementNumberOfAnimals();
    });
  }

  private void cleanUpDeadAnimals() {
    final Iterator<Animal> each = animals.iterator();
    while (each.hasNext()) {
      Animal animal = each.next();
      if (animal.isDead()) {
        animal.setDeathEpoch(epoch);
        simulationStats.decrementNumberOfAnimals();
        simulationStats.removeGenotype(animal.getGenotype());
        each.remove();
        deadAnimals.add(animal);
        map.removeAnimalBody(animal);
      }
    }
  }

  public SimulationStats getSimulationStats() {
    return simulationStats;
  }
}