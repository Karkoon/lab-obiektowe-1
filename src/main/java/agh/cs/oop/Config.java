package agh.cs.oop;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {
  private static Config config = null;
  public int width;
  public int height;
  public int startEnergy;
  public int moveEnergy;
  public int plantEnergy;
  public double jungleRatio;

  private Config() {}

  public static Config loadConfig() {
    if (config == null) {
      try {
        config = new Gson().fromJson(new FileReader("src/main/resources/parameters.json"), Config.class);
        return config;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    return config;
  }
}
