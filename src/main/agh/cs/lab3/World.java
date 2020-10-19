package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;

public class World {
  public static void main(String[] args) {
    /*Animal animal = new Animal();
    System.out.println(animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARXD);
    animal.move(MoveDirection.FORWARD);
    System.out.println(animal.toString());
    */

    Animal animal = new Animal();
    System.out.println(animal.toString());
    OptionsParser parser = new OptionsParser();
    MoveDirection[] directions = parser.parse(args);
    for (var direction : directions) {
      animal.move(direction);
    }
    System.out.println(animal.toString());

  }
}
