package agh.cs.oop.app;

import agh.cs.oop.Config;
import agh.cs.oop.util.Vector2d;
import agh.cs.oop.engine.SimulationStats;
import agh.cs.oop.mapelement.Animal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {

  @FXML
  public Label allGrasses;
  @FXML
  public Label domGenotype;
  @FXML
  public Label averageEnergy;
  @FXML
  public Label averageLifespan;
  @FXML
  public Label averageChildren;
  @FXML
  Canvas canvas;
  @FXML
  Label allAnimals;
  private GameLoop game;

  public void pause() {
    if (game.isStopped()) game.start();
    else game.stop();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    game = new GameLoop(canvas, Config.loadConfig());
    bindProperties();
    game.start();
  }

  private void bindProperties() {
    SimulationStats simulationStats = game.getSimulationStats();
    allGrasses.textProperty().bind(simulationStats.allGrassesProperty().asString("Number of grass: %s"));
    allAnimals.textProperty().bind(simulationStats.allAnimalsProperty().asString("Number of animals: %s"));
    averageChildren.textProperty().bind(simulationStats.averageNumberOfChildrenProperty().asString("Average number of children: %s"));
    averageEnergy.textProperty().bind(simulationStats.averageEnergyPerAnimalProperty().asString("Average energy: %s"));
    averageLifespan.textProperty().bind(simulationStats.averageLifespanProperty().asString("Average lifespan: %s"));
    domGenotype.textProperty().bind(simulationStats.dominatingGenotypeProperty().asString("Dominating genotype: %s times."));
  }

  public void trySelectingAnimal(MouseEvent mouseEvent) {
    if (game.isStopped()) {
      Vector2d pos = new Vector2d(
        (int) (mouseEvent.getX() / MapRenderer.CELL_WIDTH),
        (int) ((canvas.getHeight() - mouseEvent.getY()) / MapRenderer.CELL_HEIGHT));
      Animal animal = game.getAnimalAt(pos);
      if (animal != null) {
        try {
          FXMLLoader fxml = new FXMLLoader(getClass().getResource("AnimalPopup.fxml"));
          Parent root = fxml.load();
          AnimalPopupController popup = fxml.getController();
          popup.setAnimal(animal);
          popup.setGame(game);
          Stage stage = new Stage();
          stage.setTitle("Animal " + animal.getGenotype());
          stage.setScene(new Scene(root));
          stage.show();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void markDominatingGenotype() {
    game.markDominatingGenotype();
  }

  public void saveSimulationToFile() {
    if(game.isStopped()) {
      try {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("SaveToFile.fxml"));
        Parent root = fxml.load();
        SaveToFileController popup = fxml.getController();
        popup.setGame(game);
        Stage stage = new Stage();
        stage.setTitle("Save");
        stage.setScene(new Scene(root));
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
