package agh.cs.oop.mapelement;

import agh.cs.oop.util.Vector2d;

public interface IMapElement {

  Vector2d getPosition();

  int zIndex();

  int getEnergy();
}
