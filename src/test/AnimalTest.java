import agh.cs.lab2.MoveDirection;
import agh.cs.lab3.Animal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnimalTest {


  @Test
  public void movePosition() {
    Animal animal = new Animal(); // animal is headed NORTH by default
    assertEquals("Orientacja: Północ, Pozycja: (2,2)", animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.FORWARD);
    assertEquals("Orientacja: Wschód, Pozycja: (3,2)", animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.FORWARD);
    assertEquals("Orientacja: Południe, Pozycja: (3,1)", animal.toString());
    animal.move(MoveDirection.LEFT);
    animal.move(MoveDirection.BACKWARD);
    assertEquals("Orientacja: Wschód, Pozycja: (2,1)", animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.FORWARD);
    assertEquals("Orientacja: Północ, Pozycja: (2,2)", animal.toString());
  }

  @Test
  public void moveOrientation() {
    Animal animal = new Animal(); // animal is headed NORTH by default
    assertEquals("Orientacja: Północ, Pozycja: (2,2)", animal.toString());
    animal.move(MoveDirection.RIGHT);
    assertEquals("Orientacja: Wschód, Pozycja: (2,2)", animal.toString());
    animal.move(MoveDirection.RIGHT);
    assertEquals("Orientacja: Południe, Pozycja: (2,2)", animal.toString());
    animal.move(MoveDirection.LEFT);
    assertEquals("Orientacja: Wschód, Pozycja: (2,2)", animal.toString());
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.RIGHT);
    animal.move(MoveDirection.RIGHT);
    assertEquals("Orientacja: Północ, Pozycja: (2,2)", animal.toString());
  }

  @Test
  public void moveIsOutOfBounds() {
    Animal animal = new Animal();
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    animal.move(MoveDirection.FORWARD);
    assertEquals("Orientacja: Północ, Pozycja: (2,4)", animal.toString());
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    animal.move(MoveDirection.BACKWARD);
    assertEquals("Orientacja: Północ, Pozycja: (2,0)", animal.toString());
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
    assertEquals("Orientacja: Zachód, Pozycja: (0,0)", animal.toString());
  }
}