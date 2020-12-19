package agh.cs.oop.engine;

import agh.cs.oop.AnimalGenerator;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.List;

public class SimulationEngine implements IEngine {

  private final static int NUMBER_OF_ANIMALS = 10;
  private final List<Animal> animals;
  private final AnimalGenerator generator;
  private final IWorldMap map;

  public SimulationEngine(IWorldMap map) {
    this.generator = new AnimalGenerator(map);
    this.map = map;
    this.animals = generator.generateAnimals(NUMBER_OF_ANIMALS);
  }

  @Override
  public void run() {
    while (animals.size() != 0) {
      cleanUpDeadAnimals();
      moveAnimals();
      tryToProcreateAnimals();
      System.out.println(map);

      /*try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }*/
    }

  }

  private void moveAnimals() {
    for (int i = 0; i < animals.size(); i++) {
      animals.get(i).move();
    }
  }

  private void tryToProcreateAnimals() {
    for (int i = 0; i < animals.size(); i++) {
      generator.tryToProcreateAt(animals.get(i).getPosition());
    }
  }

  private void cleanUpDeadAnimals() {
    animals.removeIf(a -> a.getEnergy() == 0);
  }

}
