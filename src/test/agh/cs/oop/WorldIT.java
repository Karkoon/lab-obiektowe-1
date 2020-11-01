package agh.cs.oop;

import org.junit.jupiter.api.Test;

import static agh.cs.oop.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldIT {

  @Test
  public void normalArguments() {
    String[] args = "f f f f f r l zaba l l l r test f b".split(" ");
    Animal animal = new Animal();
    assertEquals("Orientacja: Północ, Pozycja: (2,2)", animal.toString());
    OptionsParser parser = new OptionsParser();
    MoveDirection[] directions = parser.parse(args);
    assertArrayEquals(
      new MoveDirection[]{FORWARD, FORWARD, FORWARD, FORWARD, FORWARD, RIGHT, LEFT, LEFT, LEFT, LEFT, RIGHT, FORWARD, BACKWARD},
      directions); // "czy dane wejściowe podane jako tablica łańcuchów znaków są poprawnie interpretowane."
    String[] positions = { // "czy zwierzę ma właściwą orientację", "czy zwierzę przemieszcza się na właściwe pozycje"
      "Orientacja: Północ, Pozycja: (2,3)",
      "Orientacja: Północ, Pozycja: (2,4)",
      "Orientacja: Północ, Pozycja: (2,4)",//
      "Orientacja: Północ, Pozycja: (2,4)",// "czy zwierzę nie wychodzi poza mapę"
      "Orientacja: Północ, Pozycja: (2,4)",//
      "Orientacja: Wschód, Pozycja: (2,4)",
      "Orientacja: Północ, Pozycja: (2,4)",
      "Orientacja: Zachód, Pozycja: (2,4)",
      "Orientacja: Południe, Pozycja: (2,4)",
      "Orientacja: Wschód, Pozycja: (2,4)",
      "Orientacja: Południe, Pozycja: (2,4)",
      "Orientacja: Południe, Pozycja: (2,3)",
      "Orientacja: Południe, Pozycja: (2,4)"
    };
    for (int i = 0; i < directions.length; i++) {
      animal.move(directions[i]);
      assertEquals(positions[i], animal.toString());
    }
  }

  @Test
  public void illegalArguments() {
    String[] args = "a c d e g h i j k".split(" ");
    Animal animal = new Animal();
    assertEquals("Orientacja: Północ, Pozycja: (2,2)", animal.toString());
    OptionsParser parser = new OptionsParser();
    MoveDirection[] directions = parser.parse(args);
    assertArrayEquals(
      new MoveDirection[]{},
      directions);
    for (MoveDirection direction : directions) {
      animal.move(direction);
    }
    assertEquals("Orientacja: Północ, Pozycja: (2,2)", animal.toString());
  }
}
