package agh.cs.oop.graphics;

import agh.cs.oop.engine.SimulationEngine;
import agh.cs.oop.worldmap.IWorldMap;
import agh.cs.oop.worldmap.LoopingSpecialAreaMap;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class GameLoopTimer extends AnimationTimer {
  private final IWorldMap map = new LoopingSpecialAreaMap(100, 100, 30, 30);
  private final SimulationEngine engine = new SimulationEngine(map, 200, 1000);
  private final MapRenderer r;

  private long startTime;

  public GameLoopTimer(Canvas canvas) {
    this.r = new MapRenderer(canvas, map);
  }

  @Override
  public void start() {
    super.start();
    startTime = System.nanoTime();
    System.out.println(startTime);
  }

  @Override
  public void handle(long now) {
    if (now - startTime > 10000000L) {
/*          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }*/
      if (engine.notFinished()) {
        engine.run();
        r.render();
      }
      startTime = now;
    }
  }
}
