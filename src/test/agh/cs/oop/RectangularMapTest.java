package agh.cs.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

  private RectangularMap rectangularMap;

  @BeforeEach
  void setUp() {
    rectangularMap = new RectangularMap(10, 10);
  }

  @Test
  void testToString() {
    new Animal(rectangularMap, new Vector2d(2, 2));
    String map = rectangularMap.toString();
    assertEquals(map.length() - 1, map.replace("^", "").length());
  }

  @Test
  void canMoveTo() {
    Animal animal = new Animal(rectangularMap, new Vector2d(2, 2));
    assertFalse(rectangularMap.canMoveTo(animal.getPosition()));
    assertTrue(rectangularMap.canMoveTo(new Vector2d(0, 0)));
  }

  @Test
  void place() {
    assertThrows(IllegalArgumentException.class, () -> new Animal(rectangularMap, new Vector2d(-2, -2)));
    assertThrows(IllegalArgumentException.class, () -> new Animal(rectangularMap, new Vector2d(10, 10)));

    Vector2d pos = new Vector2d(2, 2);

    assertFalse(rectangularMap.isOccupied(pos));
    Animal animal = new Animal(rectangularMap, pos);
    assertTrue(rectangularMap.isOccupied(pos));

    assertThrows(IllegalArgumentException.class, () -> new Animal(rectangularMap, pos));

    assertEquals(animal, rectangularMap.objectAt(pos));

    String map = rectangularMap.toString();
    assertEquals(map.length() - 1, map.replace("^", "").length());
  }

  @Test
  void isOccupied() {
    Vector2d pos = new Vector2d(2, 2);
    assertFalse(rectangularMap.isOccupied(pos));
    new Animal(rectangularMap, pos);
    assertTrue(rectangularMap.isOccupied(pos));
  }

  @Test
  void objectAt() {
    Animal b = new Animal(rectangularMap, new Vector2d(2, 2));
    assertEquals(b, rectangularMap.objectAt(new Vector2d(2, 2)));

    assertNull(rectangularMap.objectAt(new Vector2d(4, 4)));
    assertNull(rectangularMap.objectAt(new Vector2d(-2, -2)));
  }

  @Test
  void provideMapBounds() {
    assertEquals(new MapBounds(new Vector2d(0, 0), new Vector2d(9, 9)), rectangularMap.provideMapBounds());
  }

  @Test
  void positionChanged() {
    Vector2d pos1 = new Vector2d(5, 5);
    Vector2d pos2 = pos1.add(MapDirection.NORTH.toUnitVector());
    Animal animal = new Animal(rectangularMap, pos1);
    animal.addObserver(rectangularMap);
    assertTrue(rectangularMap.elementsMap.containsKey(pos1));
    assertFalse(rectangularMap.elementsMap.containsKey(pos2));
    animal.move(MoveDirection.FORWARD);
    assertFalse(rectangularMap.elementsMap.containsKey(pos1));
    assertTrue(rectangularMap.elementsMap.containsKey(pos2));
  }
}