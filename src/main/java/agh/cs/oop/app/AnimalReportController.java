package agh.cs.oop.app;

import agh.cs.oop.mapelement.Animal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AnimalReportController {
  @FXML
  public Label genotype;
  @FXML
  public Label children;
  @FXML
  public Label descendants;
  @FXML
  public Label deathEpoch;

  public void setAnimal(Animal animal) {
    genotype.textProperty().setValue("Genotype: " + animal.getGenotype().toString());
    children.textProperty().setValue("Number of children: " + animal.getNumberOfChildren());
    descendants.textProperty().setValue("Number of descendants:" + animal.getNumberOfDescendants());
    deathEpoch.textProperty().setValue("Death epoch: " + animal.getDeathEpoch());
  }

  public void close(ActionEvent actionEvent) {
    ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
  }
}
