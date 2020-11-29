package agh.cs.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OptionsParserTest {

  @Test
  public void parse() {
    OptionsParser parser = new OptionsParser();
    assertThrows(IllegalArgumentException.class, () ->
      parser.parse(new String[]{"f", "f", "f", "r", "right", "backward", "forward", "nic", "źle", "left"}));
    assertArrayEquals(new MoveDirection[]{
      MoveDirection.FORWARD,
      MoveDirection.FORWARD,
      MoveDirection.FORWARD,
      MoveDirection.RIGHT,
      MoveDirection.RIGHT,
      MoveDirection.BACKWARD,
      MoveDirection.FORWARD,
      MoveDirection.LEFT
    }, parser.parse(new String[]{"f", "f", "f", "r", "right", "backward", "forward", "left"}));
  }
}