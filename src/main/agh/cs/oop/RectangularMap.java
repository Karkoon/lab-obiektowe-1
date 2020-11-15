package agh.cs.oop;

public class RectangularMap extends AbstractWorldMap {

  private final MapBounds mapBounds;

  public RectangularMap(int width, int height) {
    mapBounds = new MapBounds(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return isInBounds(position) && super.canMoveTo(position);
  }

  @Override
  protected MapBounds provideMapBounds() {
    return mapBounds;
  }

  private boolean isInBounds(Vector2d position) {
    return mapBounds.getLowerLeftCorner().precedes(position)
      && mapBounds.getRightUpperCorner().follows(position);
  }
}
