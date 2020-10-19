import agh.cs.lab2.MoveDirection;
import agh.cs.lab3.OptionsParser;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class OptionsParserTest {

  @Test
  public void parse() {
    OptionsParser parser = new OptionsParser();
    assertArrayEquals(new MoveDirection[]{
      MoveDirection.FORWARD,
      MoveDirection.FORWARD,
      MoveDirection.FORWARD,
      MoveDirection.RIGHT,
      MoveDirection.RIGHT,
      MoveDirection.BACKWARD,
      MoveDirection.FORWARD,
      MoveDirection.LEFT
    }, parser.parse(new String[]{"f", "f", "f", "r", "right", "backward", "forward", "nic", "źle", "left"}));
  }
}