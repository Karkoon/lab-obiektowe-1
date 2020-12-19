package agh.cs.oop.mapelement;

import agh.cs.oop.MoveDirection;
import agh.cs.oop.worldmap.RectangularMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AnimalTest {

  private Animal animal;

  @BeforeEach
  void setUp() {
    animal = new Animal(new RectangularMap(5, 5));
  }


}