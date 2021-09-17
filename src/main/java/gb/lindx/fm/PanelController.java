package gb.lindx.fm;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.format.*;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class PanelController implements Initializable {

  @FXML
  private TableView<FileInfo> filesTable;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Type file
    TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
    fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getTypeName()));
    fileTypeColumn.setPrefWidth(25);

    // Name file
    TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Name");
    fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFileName()));
    fileNameColumn.setPrefWidth(240);

    // Size file
    TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn<>("Size");
    fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
    fileSizeColumn.setPrefWidth(100);

    fileSizeColumn.setCellFactory(column -> {
      return new TableCell<FileInfo, Long>() {
        @Override
        protected void updateItem(Long item, boolean empty) {
          super.updateItem(item, empty);
          if (item == null || empty) {
            setText(null);
            setStyle("");
          } else {
            String text = String.format("%,d bytes", item);
            if (item == -1L) {
              text = "[DIR]";
            }
            setText(text);
          }
        }
      };
    });

    // Data Modified file
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    TableColumn<FileInfo, String> fileDateColumn = new TableColumn<>("Modified");
    fileDateColumn
        .setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLasModified().format(dtf)));
    fileDateColumn.setPrefWidth(150);

    filesTable.getColumns().addAll(fileTypeColumn, fileNameColumn, fileSizeColumn, fileDateColumn);

    filesTable.getSortOrder().add(fileTypeColumn);

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
      alert.showAndWait();
    }
  }

}
