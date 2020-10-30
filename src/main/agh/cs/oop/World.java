package agh.cs.oop;

public class World {
  public static void main(String[] args) {

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
