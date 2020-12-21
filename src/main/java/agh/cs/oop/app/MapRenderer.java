package agh.cs.oop.app;

import agh.cs.oop.mapelement.Genotype;
import agh.cs.oop.util.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.worldmap.IMapBoundary;
import agh.cs.oop.worldmap.IWorldMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Comparator;
import java.util.function.Predicate;

public class MapRenderer {

  public final static int CELL_WIDTH = 10;
  public final static int CELL_HEIGHT = 10;
  private final static int X_ORIGIN = 0;
  private final static int Y_ORIGIN = 0;
  private final IWorldMap map;
  private final GraphicsContext gc;
  private Predicate<Animal> animalPredicate = null;

  public MapRenderer(Canvas canvas, IWorldMap map) {
    this.gc = canvas.getGraphicsContext2D();
    canvas.setWidth(map.getMapBoundary().getUpperRight().x * CELL_WIDTH);
    canvas.setHeight(map.getMapBoundary().getUpperRight().y * CELL_HEIGHT);
    this.map = map;
  }

  public void render() {
    IMapBoundary boundary = map.getMapBoundary();
    Vector2d upperRight = boundary.getUpperRight();
    Vector2d lowerLeft = boundary.getLowerLeft();
    renderBoundary(boundary, Color.GREENYELLOW);
    renderBoundary(map.getJungleBoundary(), Color.YELLOWGREEN);
    int yOrigin = Y_ORIGIN + CELL_HEIGHT * upperRight.y - CELL_HEIGHT;

    for (int i = lowerLeft.x; i < upperRight.x; i++) {
      for (int j = upperRight.y - 1; j >= lowerLeft.y; j--) {
        Vector2d pos = new Vector2d(i, j);
        if (map.isOccupied(pos)) {
          Animal animal = determineAnimalToRenderAt(pos);
          if (animal != null) {
            renderElement(i, j, yOrigin, Color.BROWN);
            gc.fillText(
              String.valueOf(animal.getEnergy()),
              X_ORIGIN + CELL_WIDTH * i,
              yOrigin - CELL_HEIGHT * j);
          } else {
            renderElement(i, j, yOrigin, Color.GREEN); // because map.isOccupied() can only react to
            // grass or animals, if animal isn't on the position then it can only be grass
          }
        }
      }
    }
  }

  private void renderElement(int x, int y, int yOrigin, Paint paint) {
    gc.setFill(paint);
    gc.fillRect(
      X_ORIGIN + CELL_WIDTH * x,
      yOrigin - CELL_HEIGHT * y,
      CELL_WIDTH, CELL_HEIGHT);
  }

  private void renderBoundary(IMapBoundary boundary, Paint paint) {
    Vector2d upperRight = boundary.getUpperRight();
    Vector2d lowerLeft = boundary.getLowerLeft();
    gc.setFill(paint);
    gc.fillRect(
      X_ORIGIN + (lowerLeft.x) * CELL_WIDTH, Y_ORIGIN + (lowerLeft.y) * CELL_HEIGHT,
      CELL_WIDTH * (upperRight.x - lowerLeft.x), CELL_HEIGHT * (upperRight.y - lowerLeft.y));
  }

  private Animal determineAnimalToRenderAt(Vector2d pos) {
    if (animalPredicate == null) {
      return map.animalsAt(pos).stream()
        .max(Comparator.comparingInt(Animal::zIndex))
        .orElse(null);
    } else {
      return map.animalsAt(pos).stream()
        .filter(animalPredicate)
        .max(Comparator.comparingInt(Animal::zIndex))
        .orElse(null);
    }
  }

  public void renderDominatingGenotype(Genotype genotype) {
    animalPredicate = a -> a.getGenotype().equals(genotype);
    render();
    animalPredicate = null;
  }
}
