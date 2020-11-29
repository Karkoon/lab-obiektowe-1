package agh.cs.oop;

import agh.cs.oop.engine.IEngine;
import agh.cs.oop.engine.SimulationEngine;
import agh.cs.oop.worldmap.AbstractWorldMap;
import agh.cs.oop.worldmap.GrassField;

public class World {
  public static void main(String[] args) {
    try {
      AbstractWorldMap map = new GrassField(10);
      Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
      MoveDirection[] directions = new OptionsParser().parse(args);
      IEngine engine = new SimulationEngine(directions, map, positions);
      //IEngine engine = new ControllableEngine(map, new Vector2d(-2, -2));
      engine.run();
      System.out.println(map);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
      System.exit(-1);
    }
  }
}
