package agh.cs.lab1;

public class World {


  public static void run(String[] args) {
    for (int i = 0; i < args.length - 1; i++) {
      System.out.print(interpret(Direction.valueOf(args[i])) + ", ");
    }
    if (args.length > 0) System.out.println(args[args.length - 1]);
  }

  private static String interpret(Direction direction) {
      return switch (direction) {
          case FORWARD -> "Zwierzak poszedł do przodu";
          case BACKWARD -> "Zwierzak poszedł do tyłu";
          case RIGHT -> "Zwierzak skręcił w prawo";
          case LEFT -> "Zwierzak skręcił w lewo";
          default -> "";
      };
  }

  public static void main(String[] args) {
    System.out.println("Start");
    run(args);
    System.out.println("Stop");
  }
}
