import agh.cs.oop.Animal;
import agh.cs.oop.MoveDirection;
import agh.cs.oop.OptionsParser;
import org.junit.jupiter.api.Test;

import static agh.cs.oop.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {

  @Test
  public void world() {
    String[] args = "f f r l zaba l l l r test f b".split(" ");
    Animal animal = new Animal();
    assertEquals("Orientacja: Północ, Pozycja: (2,2)", animal.toString());
    OptionsParser parser = new OptionsParser();
    MoveDirection[] directions = parser.parse(args);
    assertArrayEquals(
      new MoveDirection[]{FORWARD, FORWARD, RIGHT, LEFT, LEFT, LEFT, LEFT, RIGHT, FORWARD, BACKWARD},
      directions);
    for (var direction : directions) {
      animal.move(direction);
    }
    assertEquals("Orientacja: Południe, Pozycja: (2,4)", animal.toString());
  }

}
