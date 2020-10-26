import agh.cs.oop.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class Vector2dTest {


  private static Vector2d v6_10;
  private static Vector2d v2_4;
  private static Vector2d vn2_7;

  @BeforeEach
  public void setUpTests() {
    v6_10 = new Vector2d(6, 10);
    v2_4 = new Vector2d(2, 4);
    vn2_7 = new Vector2d(-2, 7);
  }

  @Test
  public void testEquals() {
    assertTrue(v6_10.equals(v6_10));
    assertTrue(v6_10.equals(new Vector2d(v6_10.x, v6_10.y)));
    assertFalse(v6_10.equals(v2_4));
    assertFalse(v6_10.equals(new Object()));
    assertFalse(v6_10.equals(null));
  }

  @Test
  public void testToString() {
    assertEquals("(6,10)", v6_10.toString());
    assertEquals("(2,4)", v2_4.toString());
    assertNotEquals("(1,7)", v6_10.toString());
    assertNotEquals("(1,7)", v2_4.toString());
  }

  @Test
  public void precedes() {
    assertFalse(v6_10.precedes(vn2_7));
    assertTrue(v2_4.precedes(v6_10));
    assertTrue(v2_4.precedes(v2_4));
  }

  @Test
  public void follows() {
    assertFalse(vn2_7.follows(v6_10));
    assertTrue(v6_10.follows(v2_4));
    assertTrue(v2_4.follows(v2_4));
  }

  @Test
  public void upperRight() {
    assertEquals(new Vector2d(6, 10), v6_10.upperRight(v2_4));
    assertEquals(new Vector2d(2, 7), v2_4.upperRight(vn2_7));
  }

  @Test
  public void lowerLeft() {
    assertEquals(new Vector2d(2, 4), v6_10.lowerLeft(v2_4));
    assertEquals(new Vector2d(-2, 4), v2_4.lowerLeft(vn2_7));
  }

  @Test
  public void add() {
    assertEquals(new Vector2d(8, 14), v6_10.add(v2_4));
    assertEquals(new Vector2d(0, 11), v2_4.add(vn2_7));
  }

  @Test
  public void subtract() {
    assertEquals(new Vector2d(4, 6), v6_10.subtract(v2_4));
    assertEquals(new Vector2d(4, -3), v2_4.subtract(vn2_7));
  }

  @Test
  public void opposite() {
    assertEquals(new Vector2d(-6, -10), v6_10.opposite());
    assertEquals(new Vector2d(-2, -4), v2_4.opposite());
    assertEquals(new Vector2d(2, -7), vn2_7.opposite());
  }
}
