package agh.cs.oop.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Evolution extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));
    stage.setTitle("Evolution");
    stage.getIcons().add(new Image("hellofx.png"));
    stage.setScene(new Scene(root));
    stage.show();
  }
}
