package agh.cs.oop.graphics;

import agh.cs.oop.Vector2d;
import agh.cs.oop.mapelement.Animal;
import agh.cs.oop.mapelement.Grass;
import agh.cs.oop.worldmap.IMapBoundary;
import agh.cs.oop.worldmap.IWorldMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class MapRenderer {

  private final static int CELL_WIDTH = 7;
  private final static int CELL_HEIGHT = 7;
  private final static int X_ORIGIN = 0;
  private final static int Y_ORIGIN = 0;
  private final IWorldMap map;
  private final GraphicsContext gc;

  public MapRenderer(Canvas canvas, IWorldMap map) {
    this.gc = canvas.getGraphicsContext2D();
    canvas.setWidth(map.provideMapBoundary().getUpperRightCorner().x * CELL_WIDTH + 20);
    canvas.setHeight(map.provideMapBoundary().getUpperRightCorner().y * CELL_HEIGHT + 20);
    this.map = map;
  }

  public void render() {
    IMapBoundary boundary = map.provideMapBoundary();
    Vector2d upperRight = boundary.getUpperRightCorner();
    Vector2d lowerLeft = boundary.getLowerLeftCorner();

    renderBoundary(boundary, Color.GREENYELLOW);
    renderBoundary(map.provideJungleBoundary(), Color.YELLOWGREEN);

    int yOrigin = Y_ORIGIN + CELL_HEIGHT * upperRight.y;
    for (int i = lowerLeft.x; i <= upperRight.x; i++) {
      for (int j = upperRight.y + 1; j >= lowerLeft.y; j--) {
        Vector2d pos = new Vector2d(i, j);
        if (map.isOccupied(pos)) {
          Grass grass = map.grassAt(pos);
          if (grass != null) {
            gc.setFill(Color.GREEN);
            gc.fillRect(X_ORIGIN + CELL_WIDTH * i,
              yOrigin - CELL_HEIGHT * j, CELL_WIDTH, CELL_HEIGHT);
          }
          Animal animal = map.animalsAt(pos).stream().sorted().findFirst().orElse(null);
          if (animal != null) {
            gc.setFill(Color.BROWN);
            gc.fillRect(X_ORIGIN + CELL_WIDTH * i,
              yOrigin - CELL_HEIGHT * j, CELL_WIDTH, CELL_HEIGHT);
            gc.fillText(String.valueOf(animal.getEnergy()), X_ORIGIN + CELL_WIDTH * i,
              yOrigin - CELL_HEIGHT * j);
          }
        }
      }
    }
  }

  private void renderBoundary(IMapBoundary boundary, Paint paint) {
    Vector2d upperRight = boundary.getUpperRightCorner();
    Vector2d lowerLeft = boundary.getLowerLeftCorner();
    for (int i = lowerLeft.x; i <= upperRight.x; i++) {
      for (int j = lowerLeft.y; j <= upperRight.y; j++) {
        gc.setFill(paint);
        gc.fillRect(
          X_ORIGIN + CELL_WIDTH * i, Y_ORIGIN + CELL_HEIGHT * j,
          CELL_WIDTH, CELL_HEIGHT);
      }
    }
  }
}
