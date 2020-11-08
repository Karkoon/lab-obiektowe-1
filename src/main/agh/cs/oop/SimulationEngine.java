package agh.cs.oop;

import java.util.LinkedList;
import java.util.List;

public class SimulationEngine implements IEngine {

  private final MoveDirection[] moveDirections;
  private final List<Animal> animals;

  public SimulationEngine(MoveDirection[] moveDirections, IWorldMap map, Vector2d[] initialAnimalPositions) {
    this.moveDirections = moveDirections;
    animals = new LinkedList<>();
    for (var position : initialAnimalPositions) {
      animals.add(new Animal(map, position));
    }
  }

  @Override
  public void run() {
    for (int i = 0; i < moveDirections.length; i++) {
      animals.get(i % animals.size()).move(moveDirections[i]);
    }
  }
}
