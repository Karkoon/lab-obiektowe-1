package agh.cs.oop.engine;

import agh.cs.oop.MoveDirection;
import agh.cs.oop.OptionsParser;
import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.AbstractWorldMap;

import java.util.Random;
import java.util.Scanner;

public class ControllableEngine implements IEngine {

  private static final int NUMBER_OF_ANIMALS = 10;
  private final Animal controlledAnimal;
  private final AbstractWorldMap map;
  private final OptionsParser parser= new OptionsParser();
  private final Scanner scanner = new Scanner(System.in);

  public ControllableEngine(AbstractWorldMap map, Vector2d startingPosition) {
    this.map = map;

    controlledAnimal = new Animal(map, startingPosition);
    addDummyAnimals();
  }

  private void addDummyAnimals() {
    Vector2d upperCorner = map.provideMapBoundary().getUpperRightCorner();
    Random rand = new Random();
    for (int i = 0; i <= NUMBER_OF_ANIMALS; i++) {
      Vector2d potentialPosition;
      do {
        potentialPosition = new Vector2d(rand.nextInt(upperCorner.x), rand.nextInt(upperCorner.y));
      } while (map.isOccupied(potentialPosition));
      new Animal(map, potentialPosition);
    }
  }

  @Override
  public void run() {
    while (true) {
      System.out.println(map);
      MoveDirection moveDirection = parser.toMoveDirection(scanner.next());
      controlledAnimal.move(moveDirection);
    }
  }
}
