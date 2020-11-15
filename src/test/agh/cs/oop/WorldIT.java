package agh.cs.oop;

import org.junit.jupiter.api.Test;

import static agh.cs.oop.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.*;

public class WorldIT {

  @Test
  public void normalArguments() {
    String[] args = "f b r l f f r r f f f f f f f f".split(" ");
    OptionsParser parser = new OptionsParser();
    MoveDirection[] directions = parser.parse(args);
    assertArrayEquals(
      new MoveDirection[]{FORWARD, BACKWARD, RIGHT, LEFT, FORWARD, FORWARD, RIGHT, RIGHT,
        FORWARD, FORWARD, FORWARD, FORWARD, FORWARD, FORWARD, FORWARD, FORWARD,
      },
      directions); // "czy dane wejściowe podane jako tablica łańcuchów znaków są poprawnie interpretowane."

    Vector2d[] initialAnimalPositions = {new Vector2d(2, 2), new Vector2d(3, 4)};
    RectangularMap map = new RectangularMap(10, 5);
    IEngine engine = new SimulationEngine(directions, map, initialAnimalPositions);
    engine.run();

    // czy zwierze przemieszcza się na właściwe pozycje
    // czy zwierze wychodzi poza mapę
    Vector2d[] expectedFinalPositions = {new Vector2d(2, 0), new Vector2d(3, 4)};
    for (Vector2d expectedFinalPosition : expectedFinalPositions) {
      assertNotNull(map.objectAt(expectedFinalPosition));
    }

    // czy zwierze ma właściwą orientację
    MapDirection[] expectedFinalDirections = {MapDirection.SOUTH, MapDirection.NORTH};
    for (int i = 0; i < expectedFinalPositions.length; i++) {
      Vector2d position = expectedFinalPositions[i];
      MapDirection expectedFinalDirection = expectedFinalDirections[i];
      assertEquals(expectedFinalDirection.toString(), map.objectAt(position).toString());
    }
  }
}
