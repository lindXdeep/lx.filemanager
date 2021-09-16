package gb.lindx.fm;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

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

    updateList(Paths.get(".", "A"));
  }

  public void updateList(Path path) {
   
    try { // Files.list - может выбросить IOException
 
      filesTable.getItems().clear(); // очищаем элементы в таблице
      filesTable.getItems().addAll(
  
          Files.list(path) // читаем директорию в в иде потока
              .map(FileInfo::new) // преобразуем в fileInfo
              .collect(Collectors.toList()) // затем преобразуем в list
      );
      filesTable.sort(); // Таблицу сортируем по умоляанию

    } catch (IOException e) {
      Alert alert = new Alert(AlertType.WARNING, "Can't read files", ButtonType.OK);
      alert.show();
    }
  }
}
