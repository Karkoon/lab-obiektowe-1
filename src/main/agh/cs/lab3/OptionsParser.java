package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;

import java.util.Set;
import java.util.stream.Stream;

public class OptionsParser {
  public MoveDirection[] parse(String[] args) {
    return Stream.of(args)
      .filter(this::isUnderstandable)
      .map(this::toMoveDirection)
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

  private boolean isUnderstandable(String str) {
    Set<String> words = Set.of(
      "f", "forward", "b", "backward",
      "r", "right", "l", "left"
    );
    return words.contains(str);
  }
}
