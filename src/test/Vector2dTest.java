import agh.cs.lab2.Vector2d;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2dTest {


  private Vector2d a;
  private Vector2d b;
  private Vector2d c;

  @Before
  public void setUpTests() {
    this.a = new Vector2d(6, 10);
    this.b = new Vector2d(2, 4);
    this.c = new Vector2d(-2, 7);
  }

  @Test
  public void testEquals() {
    assertTrue(a.equals(a));
    assertTrue(a.equals(new Vector2d(a.x, a.y)));
    assertFalse(a.equals(b));
    assertFalse(a.equals(new Object()));
    assertFalse(a.equals(null));
  }

  @Test
  public void testToString() {
    assertEquals("(6, 10)", a.toString());
    assertEquals("(2, 4)", b.toString());
    assertNotEquals("(1, 7)", a.toString());
    assertNotEquals("(1, 7)", b.toString());
  }

  @Test
  public void testPrecedes() {
    assertFalse(a.precedes(c));
    assertTrue(b.precedes(a));
    assertTrue(b.precedes(b));
  }

  @Test
  public void testFollows() {
    assertFalse(c.follows(a));
    assertTrue(a.follows(b));
    assertTrue(b.follows(b));
  }

  @Test
  public void testUpperRight() {
    assertEquals(new Vector2d(6, 10), a.upperRight(b));
    assertEquals(new Vector2d(2, 7), b.upperRight(c));
  }

  @Test
  public void testLowerLeft() {
    assertEquals(new Vector2d(2, 4), a.lowerLeft(b));
    assertEquals(new Vector2d(-2, 4), b.lowerLeft(c));
  }

  @Test
  public void testAdd() {
    assertEquals(new Vector2d(8, 14), a.add(b));
    assertEquals(new Vector2d(0, 11), b.add(c));
  }

  @Test
  public void testSubtract() {
    assertEquals(new Vector2d(4, 6), a.subtract(b));
    assertEquals(new Vector2d(4, -3), b.subtract(c));
  }

  @Test
  public void testOpposite() {
    assertEquals(new Vector2d(-6, -10), a.opposite());
    assertEquals(new Vector2d(-2, -4), b.opposite());
    assertEquals(new Vector2d(2, -7), c.opposite());
  }
}
