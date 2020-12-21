package agh.cs.oop.app;

import agh.cs.oop.engine.SimulationStats;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveToFileController {

  @FXML
  public TextField epochInput;
  public TextField pathInput;

  private GameLoop game;
  private final Gson gson = new Gson();

  void setGame(GameLoop game) {
    this.game = game;
  }

  public void close(ActionEvent actionEvent) {
    ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
  }

  public void trackEpochs(ActionEvent actionEvent) {
    try {
      int epochs = Integer.parseInt(epochInput.getText());
      String path = pathInput.getText().isEmpty() ? "simulation.json" : pathInput.getText();
      game.getSimulationStats().resetHistoricSimulationStat();
      game.getEngine().registerEpochObserver(new IEpochObserver() {
        int epochsToWait = epochs;

        @Override
        public void epochPassed() {
          if (epochsToWait == 0 || game.isStopped()) {
            game.getEngine().removeEpochObserver(this);
            try {
              PrintWriter fileWriter = new PrintWriter(new FileOutputStream(path, false));
              fileWriter.println(gson.toJson(new SimulationStats.SummarySimulationStats(game.getSimulationStats().getHistoricSimulationStat())));
              fileWriter.close();
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
