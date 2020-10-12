package agh.cs.lab1;

import java.util.stream.Stream;

public class World {

  // przed zadaniem 18
  /*  public static void run(String[] args) { // 12.
   *//*    System.out.println("Zwierzak poszedł do przodu"); // 10.
    for (int i = 0; i < args.length - 1; i++) { // 13.
      System.out.print(args[i] + ", ");
    }
    if (args.length > 0) System.out.println(args[args.length - 1]); // 14.
    *//*
    for (String arg : args) {
      switch (arg) { //16, 17
        case "f" -> System.out.println("Zwierzak poszedł do przodu.");
        case "b" -> System.out.println("Zwierzak poszedł do tyłu.");
        case "r" -> System.out.println("Zwierzak skręcił w prawo.");
        case "l" -> System.out.println("Zwierzak skręcił w lewo.");
      }
    }
  }*/

  // przed zadaniem 24
/*  public static void run(Direction[] directions) { // 12.
    for (Direction direction : directions) {
      System.out.println(switch (direction) {
        case FORWARD -> "Zwierzak poszedł do przodu";
        case BACKWARD -> "Zwierzak poszedł do tyłu";
        case RIGHT -> "Zwierzak skręcił w prawo";
        case LEFT -> "Zwierzak skręcił w lewo";
      });
    }
  }*/

  //24
  public static void run(Direction direction) { // 12.
    System.out.println(switch (direction) {
      case FORWARD -> "Zwierzak poszedł do przodu";
      case BACKWARD -> "Zwierzak poszedł do tyłu";
      case RIGHT -> "Zwierzak skręcił w prawo";
      case LEFT -> "Zwierzak skręcił w lewo";
    });
  }

  private static boolean isUnderstandable(String str) {
    return str.equals("f") || str.equals("b") || str.equals("l") || str.equals("r");
  }

  private static Direction toDirection(String str) {
    return switch (str) {
      case "f" -> Direction.FORWARD;
      case "b" -> Direction.BACKWARD;
      case "r" -> Direction.RIGHT;
      case "l" -> Direction.LEFT;
      default -> null;
    };
  }

  // przed zadaniem 18
/*  public static void main(String[] args) {
    System.out.println("Start");
    run(args);
    System.out.println("Stop");
  }*/

  // przed zadaniem 24
/*  public static void main(String[] args) {
    System.out.println("Start");
    Direction[] directions = Stream.of(args)
      .filter(World::isUnderstandable)
      .map(World::toDirection)
      .toArray(Direction[]::new);
    run(directions);
    System.out.println("Stop");
  }*/

  //24
  public static void main(String[] args) {
    System.out.println("Start");
    Stream.of(args)
      .filter(World::isUnderstandable)
      .map(World::toDirection)
      .forEach(World::run);
    System.out.println("Stop");
  }
}
