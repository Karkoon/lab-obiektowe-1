package agh.cs.oop;

import java.util.Objects;
import java.util.stream.Stream;

public class OptionsParser {
  public MoveDirection[] parse(String[] args) {
    return Stream.of(args)
      .map(this::toMoveDirection)
      .filter(Objects::nonNull)
      .toArray(MoveDirection[]::new);
  }

  private MoveDirection toMoveDirection(String str) {
    return switch (str) {
      case "f", "forward" -> MoveDirection.FORWARD;
      case "b", "backward" -> MoveDirection.BACKWARD;
      case "r", "right" -> MoveDirection.RIGHT;
      case "l", "left" -> MoveDirection.LEFT;
      default -> null;
    };
  }
}
