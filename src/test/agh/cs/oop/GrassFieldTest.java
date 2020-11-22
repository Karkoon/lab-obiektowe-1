package agh.cs.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

  private GrassField grassField;
  private final int GRASS_AMOUNT = 10;

  @BeforeEach
  void setUp() {
    grassField = new GrassField(GRASS_AMOUNT);
  }

  @Test
  void testToString() {
    String map = grassField.toString();
    assertEquals(map.length() - GRASS_AMOUNT, map.replace("*", "").length());
  }

  @Test
  void canMoveTo() {
    Animal animal = new Animal(grassField, new Vector2d(2, 2));
    assertFalse(grassField.canMoveTo(animal.getPosition()));
    assertTrue(grassField.canMoveTo(new Vector2d(0, 0)));
  }

  @Test
  void place() {
    Vector2d pos = new Vector2d(-2, -2);

    assertFalse(grassField.isOccupied(pos));
    Animal animal = new Animal(grassField, pos);
    assertTrue(grassField.isOccupied(pos));

    assertEquals(animal, grassField.objectAt(pos));

    String map = grassField.toString();
    assertEquals(map.length() - 1, map.replace("^", "").length());
  }

  @Test
  void isOccupied() {
    Vector2d pos = new Vector2d(2, 2);
    assertFalse(grassField.isOccupied(pos));
    new Animal(grassField, pos);
    assertTrue(grassField.isOccupied(pos));
  }

  @Test
  void objectAt() {
    Animal a = new Animal(grassField, new Vector2d(-2, -2));
    assertEquals(a, grassField.objectAt(new Vector2d(-2, -2)));
    assertNull(grassField.objectAt(new Vector2d(-10, -10)));
  }

  @Test
  void provideMapBounds() {
    Vector2d lowerLeftCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    Vector2d upperRightCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    for (Vector2d position : grassField.elementsMap.keySet()) {
      lowerLeftCorner = position.lowerLeft(lowerLeftCorner);
      upperRightCorner = position.upperRight(upperRightCorner);
    }
    assertEquals(new MapBounds(lowerLeftCorner, upperRightCorner), grassField.provideMapBounds());
  }

  @Test
  void positionChanged() {
    Vector2d pos1 = new Vector2d(-10, -10);
    Vector2d pos2 = pos1.add(MapDirection.NORTH.toUnitVector());
    Animal animal = new Animal(grassField, pos1);
    animal.addObserver(grassField);
    assertTrue(grassField.elementsMap.containsKey(pos1));
    assertFalse(grassField.elementsMap.containsKey(pos2));
    animal.move(MoveDirection.FORWARD);
    assertFalse(grassField.elementsMap.containsKey(pos1));
    assertTrue(grassField.elementsMap.containsKey(pos2));
  }
}
