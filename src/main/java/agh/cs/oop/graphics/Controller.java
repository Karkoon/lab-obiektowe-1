package agh.cs.oop.graphics;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

  @FXML
  Canvas leftCanvas;
  @FXML
  Canvas rightCanvas;

  public void exit() {
    System.exit(0);
  }

  @FXML
  ScrollPane leftScrollPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    new GameLoopTimer(leftCanvas).start();
    new GameLoopTimer(rightCanvas).start();
  }
}

