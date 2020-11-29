package agh.cs.oop.worldmap;

import agh.cs.oop.Vector2d;

public interface IMapBoundary {

  Vector2d getLowerLeftCorner();

  Vector2d getUpperRightCorner();

}
