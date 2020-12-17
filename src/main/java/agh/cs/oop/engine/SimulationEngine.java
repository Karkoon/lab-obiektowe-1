package agh.cs.oop.engine;

import agh.cs.oop.MoveDirection;
import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.AbstractWorldMap;

import java.util.LinkedList;
import java.util.List;

public class SimulationEngine implements IEngine {

  private final MoveDirection[] moveDirections;
  private final List<Animal> animals;

  public SimulationEngine(MoveDirection[] moveDirections, AbstractWorldMap map, Vector2d[] initialAnimalPositions) {
    this.moveDirections = moveDirections;
    animals = new LinkedList<>();
    for (Vector2d position : initialAnimalPositions) {
      Animal animal = new Animal(map, position);
      animals.add(animal);
    }
  }

  @Override
  public void run() {
    for (int i = 0; i < moveDirections.length; i++) {
      Animal animal = animals.get(i % animals.size());
      animal.move(moveDirections[i]);
    }
  }
}
