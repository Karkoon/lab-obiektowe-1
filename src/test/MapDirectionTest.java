import org.junit.Test;

import static agh.cs.lab2.MapDirection.*;
import static org.junit.Assert.assertEquals;

public class MapDirectionTest {

  @Test
  public void testNext() {
    assertEquals(EAST, NORTH.next());
    assertEquals(SOUTH, EAST.next());
    assertEquals(WEST, SOUTH.next());
    assertEquals(NORTH, WEST.next());
  }

  @Test
  public void testPrevious() {
    assertEquals(WEST, NORTH.previous());
    assertEquals(SOUTH, WEST.previous());
    assertEquals(EAST, SOUTH.previous());
    assertEquals(NORTH, EAST.previous());
  }

}
