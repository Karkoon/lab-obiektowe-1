package agh.cs.oop;

import java.util.Objects;

public abstract class AbstractMapElement implements IMapElement {

  protected Vector2d position;

  public AbstractMapElement(Vector2d position) {
    this.position = position;
  }

  @Override
  public Vector2d getPosition() {
    return position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractMapElement that = (AbstractMapElement) o;
    return Objects.equals(position, that.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position);
  }
}
