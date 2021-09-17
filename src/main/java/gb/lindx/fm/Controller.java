package gb.lindx.fm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class Controller {

  @FXML
  private VBox leftPanel, rightPanel;

  public void btnExitAction(ActionEvent event) {
    Platform.exit();
  }

  public void copyBtnAction(ActionEvent event) {
    PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
    PanelController rghtPC = (PanelController) rightPanel.getProperties().get("ctrl");

    if (leftPC.getSelectedFileName() == null && rghtPC.getSelectedFileName() == null) {
      Alert alert = new Alert(AlertType.ERROR, "No one selected element", ButtonType.OK);
      alert.show();
      return;
    }

    PanelController srcPC = null, dstPC = null;

    if (leftPC.getSelectedFileName() != null) {
      srcPC = leftPC;
      dstPC = rghtPC;
    }

    if (rghtPC.getSelectedFileName() != null) {
      srcPC = rghtPC;
      dstPC = leftPC;
    }

    Path srcPath = Paths.get(srcPC.getCurentPath(), srcPC.getSelectedFileName());
    Path dstPath = Paths.get(dstPC.getCurentPath()).resolve(srcPath.getFileName().toString());

    try {
      Files.copy(srcPath, dstPath);
      dstPC.updateList(Paths.get(dstPC.getCurentPath()));
    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR, "Can't copy this file", ButtonType.OK);
      alert.show();
    }
  }
}
