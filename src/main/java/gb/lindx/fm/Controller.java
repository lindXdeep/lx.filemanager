package gb.lindx.fm;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controller implements Initializable {

  @FXML
  private TableView<FileInfo> filesTable;

  public void btnExitAction(ActionEvent event) {
    Platform.exit();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>("Type");
    fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
    fileTypeColumn.setPrefWidth(24);

    TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Name");
    fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileName()));
    fileTypeColumn.setPrefWidth(240);


    filesTable.getColumns().addAll(fileTypeColumn, fileNameColumn);

  }
}
