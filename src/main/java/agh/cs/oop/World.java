package agh.cs.oop;

import agh.cs.oop.engine.ControllableEngine;
import agh.cs.oop.engine.IEngine;
import agh.cs.oop.worldmap.IWorldMap;
import agh.cs.oop.worldmap.LoopingSpecialAreaMap;

public class World {
  public static void main(String[] args) {
    try {
      // wartości wejściowe:
      // width, heigth
      // startEnergy
      // moveEnergy
      // plantEnergy <- ilość energii uzyskanej po zjedzeniu rośliny
      // jungleRatio <- proporcja dżungli do sawanny
      // dane znajdują się w pliku parameters.json, które są w katalogu uruchomieniowym
      IWorldMap map = new LoopingSpecialAreaMap(20, 20, 10, 10);
      new OptionsParser().parse(args);
      //IEngine engine = new SimulationEngine(map);
      IEngine engine = new ControllableEngine(map, new Vector2d(2, 2));
      engine.run();
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
      System.exit(-1);
    }
  }
}
