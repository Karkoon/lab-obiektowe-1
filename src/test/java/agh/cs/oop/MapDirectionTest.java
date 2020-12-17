package agh.cs.oop;

import org.junit.jupiter.api.Test;

import static agh.cs.oop.MapDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

  @Test
  public void next() {
    assertEquals(EAST, NORTH.next());
    assertEquals(SOUTH, EAST.next());
    assertEquals(WEST, SOUTH.next());
    assertEquals(NORTH, WEST.next());
  }

  @Test
  public void previous() {
    assertEquals(WEST, NORTH.previous());
    assertEquals(SOUTH, WEST.previous());
    assertEquals(EAST, SOUTH.previous());
    assertEquals(NORTH, EAST.previous());
  }
}
