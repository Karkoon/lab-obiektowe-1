package agh.cs.oop.engine;

import agh.cs.oop.AnimalGenerator;
import agh.cs.oop.MoveDirection;
import agh.cs.oop.OptionsParser;
import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.IWorldMap;

import java.util.Scanner;

public class ControllableEngine implements IEngine {

  private static final int NUMBER_OF_ANIMALS = 20;

  private final Animal controlledAnimal;
  private final IWorldMap map;

  private final AnimalGenerator generator;
  private final OptionsParser parser = new OptionsParser();
  private final Scanner scanner = new Scanner(System.in);

  public ControllableEngine(IWorldMap map, Vector2d startingPosition) {
    this.map = map;
    this.generator = new AnimalGenerator(map);
    controlledAnimal = generator.createAnimal(startingPosition);
    this.generator.generateAnimals(NUMBER_OF_ANIMALS);
  }

  @Override
  public void run() {
    while (true) {
      System.out.println(map);
      MoveDirection moveDirection = parser.toMoveDirection(scanner.next());
      generator.tryToProcreateAt(controlledAnimal.getPosition());
      controlledAnimal.forceMove(moveDirection);
    }
  }
}
