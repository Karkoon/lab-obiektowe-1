package agh.cs.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AnimalTest {


  private Animal animal;

  @BeforeEach
  void setUp() {
    animal = new Animal(new RectangularMap(5, 5));
  }

  @Test
  public void movePosition() {
    assertEquals("^", animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.FORWARD);
    assertEquals(">", animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.FORWARD);
    assertEquals("v", animal.toString());
    animal.move(MoveDirection.LEFT);
    animal.move(MoveDirection.BACKWARD);
    assertEquals(">", animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.FORWARD);
    assertEquals("^", animal.toString());
  }

  @Test
  public void moveOrientation() {
    assertEquals("^", animal.toString());
    animal.move(MoveDirection.RIGHT);
    assertEquals(">", animal.toString());
    animal.move(MoveDirection.RIGHT);
    assertEquals("v", animal.toString());
    animal.move(MoveDirection.LEFT);
    assertEquals(">", animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.RIGHT);
    assertEquals("^", animal.toString());
  }

  @Test
  public void moveIsOutOfBounds() {
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    assertEquals("^", animal.toString());
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    assertEquals("^", animal.toString());
    animal.move(MoveDirection.LEFT);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    assertEquals("<", animal.toString());
  }
}