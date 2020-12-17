package agh.cs.oop;

import agh.cs.oop.mapelement.IMapElement;

public interface IPositionChangeObserver {

  void positionChanged(Vector2d oldPosition, Vector2d newPosition, IMapElement changedElement);

}
