package agh.cs.oop.engine;

import agh.cs.oop.AnimalGenerator;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.List;

public class SimulationEngine implements IEngine {

  private final static int NUMBER_OF_ANIMALS = 20;
  private final List<Animal> animals;
  private final AnimalGenerator generator;
  private final IWorldMap map;
  private final boolean simulationRunning = true; // TODO: 19.12.2020 create some end conditions for the simulation to have it actually end

  public SimulationEngine(IWorldMap map) {
    this.generator = new AnimalGenerator(map);
    this.map = map;
    this.animals = generator.generateAnimals(NUMBER_OF_ANIMALS);
  }

  @Override
  public void run() {
    while (simulationRunning) {
      for (int i = 0; i < animals.size(); i++) {
        Animal animal = animals.get(i);
        generator.tryToProcreateAt(animal.getPosition());
        animal.move(); // TODO: 19.12.2020 get the move direction from the animals genotype
      }
      System.out.println(map);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}
