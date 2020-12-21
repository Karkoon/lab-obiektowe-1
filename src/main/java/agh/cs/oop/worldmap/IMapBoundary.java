package agh.cs.oop.worldmap;

import agh.cs.oop.util.Vector2d;

public interface IMapBoundary {

  Vector2d getLowerLeft();

  Vector2d getUpperRight();

  Vector2d randomPosition();

  int size();
}
