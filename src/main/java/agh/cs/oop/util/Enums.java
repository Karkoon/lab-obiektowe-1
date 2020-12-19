package agh.cs.oop.util;

import java.util.concurrent.ThreadLocalRandom;

public class Enums {
  public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz) {
    T[] enums = clazz.getEnumConstants();
    int enumSize = enums.length;
    return enums[ThreadLocalRandom.current().nextInt(enumSize)];
  }
}
