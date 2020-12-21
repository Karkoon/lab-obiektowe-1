package agh.cs.oop.app;

import agh.cs.oop.Config;
import agh.cs.oop.util.Vector2d;
import agh.cs.oop.engine.SimulationEngine;
import agh.cs.oop.engine.SimulationStats;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.IWorldMap;
import agh.cs.oop.worldmap.LoopingSpecialAreaMap;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class GameLoop extends AnimationTimer {
  private final IWorldMap map;
  private final SimulationEngine engine;
  private final MapRenderer renderer;
  private boolean stopped = false;

  public GameLoop(Canvas canvas, Config cfg) {
    this.map = new LoopingSpecialAreaMap(cfg.width, cfg.height,
      (int) (cfg.width * cfg.jungleRatio), (int) (cfg.height * cfg.jungleRatio));
    this.renderer = new MapRenderer(canvas, map);
    this.engine = new SimulationEngine(map, 50, 150,
      cfg.startEnergy, cfg.plantEnergy, cfg.moveEnergy);
  }

  @Override
  public void start() {
    super.start();
    stopped = false;
  }

  public boolean isStopped() {
    return stopped;
  }

  public SimulationStats getSimulationStats() {
    return engine.getSimulationStats();
  }

  @Override
  public void stop() {
    super.stop();
    stopped = true;
  }

  @Override
  public void handle(long now) {
    if (engine.notFinished() && !stopped) {
      engine.run();
      renderer.render();
    }
  }

  public Animal getAnimalAt(Vector2d pos) {
    return map.animalsAt(pos).stream().sorted().findFirst().orElse(null);
  }

  public void markDominatingGenotype() {
    renderer.renderDominatingGenotype(getSimulationStats().dominatingGenotypeProperty().get().getFirst());
  }

  public SimulationEngine getEngine() {
    return engine;
  }
}
