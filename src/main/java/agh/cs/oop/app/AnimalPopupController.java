package agh.cs.oop.app;

import agh.cs.oop.mapelement.Animal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AnimalPopupController {
  @FXML
  public Label genotype;

  @FXML
  public TextField epochInput;
  private Animal animal;
  private GameLoop game;

  public void setAnimal(Animal animal) {
    this.animal = animal;
    genotype.textProperty().setValue(animal.getGenotype().toString());
  }

  public void setGame(GameLoop game) {
    this.game = game;
  }

  public void close(ActionEvent actionEvent) {
    ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
  }

  public void track(ActionEvent actionEvent) {
    try {
      int epochs = Integer.parseInt(epochInput.getText());
      animal.removeChildren();
      game.getEngine().registerEpochObserver(new IEpochObserver() {
        int epochsToWait = epochs;

        @Override
        public void epochPassed() {
          if (epochsToWait == 0) {
            game.getEngine().removeEpochObserver(this);
            try {
              FXMLLoader fxml = new FXMLLoader(getClass().getResource("animalreport.fxml"));
              Parent root = fxml.load();
              AnimalReportController report = fxml.getController();
              report.setAnimal(animal);
              Stage stage = new Stage();
              stage.setTitle("Animal " + animal.getGenotype());
              stage.setScene(new Scene(root));
              stage.show();
            } catch (IOException e) {
              e.printStackTrace();
            }
          } else {
            epochsToWait--;
          }
        }
      });
      ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }


}
