package agh.cs.oop.mapelement;

import agh.cs.oop.Vector2d;

public interface IMapElement {

  Vector2d getPosition();

  /**
   * Z-index can be used to determine the order of subclasses in a
   * sorted list because there is no natural way to order types.
   */
  int zIndex();
}
