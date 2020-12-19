package agh.cs.oop.mapelement;

import agh.cs.oop.IPositionChangeObserver;
import agh.cs.oop.Vector2d;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapElement implements IMapElement {

  private final List<IPositionChangeObserver> positionChangeObserverList = new ArrayList<>();
  protected Vector2d position;

  public AbstractMapElement(Vector2d position) {
    this.position = position;
  }

  public void addObserver(IPositionChangeObserver observer) {
    positionChangeObserverList.add(observer);
  }

  public void removeObserver(IPositionChangeObserver observer) {
    positionChangeObserverList.remove(observer);
  }

  public void notifyObservers(Vector2d oldPosition) {
    positionChangeObserverList.forEach(a -> a.positionChanged(oldPosition, position, this));
  }

  @Override
  public Vector2d getPosition() {
    return position;
  }


}
