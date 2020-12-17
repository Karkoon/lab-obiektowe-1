package agh.cs.oop.worldmap;

import agh.cs.oop.Vector2d;

public class RectangularMap extends AbstractWorldMap {

  private final IMapBoundary mapBoundary;

  public RectangularMap(int width, int height) {
    mapBoundary = new NondynamicMapBoundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return isInBounds(position) && super.canMoveTo(position);
  }

  @Override
  public IMapBoundary provideMapBoundary() {
    return mapBoundary;
  }

  private boolean isInBounds(Vector2d position) {
    return mapBoundary.getLowerLeftCorner().precedes(position)
      && mapBoundary.getUpperRightCorner().follows(position);
  }
}
