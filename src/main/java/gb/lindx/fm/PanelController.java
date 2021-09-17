package gb.lindx.fm;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.format.*;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class PanelController implements Initializable {

  String osname = System.getProperty("os.name");
  String username = System.getProperty("user.name");

  @FXML
  private TableView<FileInfo> filesTable;

  @FXML
  private ComboBox<String> disksBox;

  @FXML
  private TextField dirField;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Type file
    TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
    fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getTypeName()));
    fileTypeColumn.setPrefWidth(60);

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

    filesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {

          Path p = Paths.get(dirField.getText())
              .resolve(filesTable.getSelectionModel().getSelectedItem().getFileName());

          if (Files.isDirectory(p)) {
            updateList(p);
          }
        }
      };
    });

    // Combobox discs
    try {
      disksBox.getItems().clear();

      Iterator<Path> i = null;

      if (osname.equals("Linux")) {
        Path root = Paths.get("/media/".concat(username)); // /media/username/
        i = Files.list(root).collect(Collectors.toList()).iterator();
      } else {
        i = FileSystems.getDefault().getRootDirectories().iterator();
      }

      while (i.hasNext())
        disksBox.getItems().add(i.next().getFileName().toString());

      disksBox.getSelectionModel().select(0);

    } catch (IOException e) {
      throw new RuntimeException("Can't read disks");
    }
    disksBox.getSelectionModel().select(0);

    updateList(Paths.get(".", "A"));
  }

  public void updateList(Path path) {

    dirField.setText(path.normalize().toAbsolutePath().toString());

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

  @FXML
  public void btnUpWalking(ActionEvent event) {

    Path upperPath = Paths.get(dirField.getText()).getParent();

    if (upperPath != null) {
      updateList(upperPath);
    }
  }

  @FXML
  public void selectionDisk(ActionEvent event) {

    ComboBox<String> disk = (ComboBox<String>) event.getSource();

    String pathToDisk = disk.getSelectionModel().getSelectedItem();

    Path p = null;

    if (osname.equals("Linux")) {
      p = Paths.get("/media/".concat(username.concat("/")).concat(pathToDisk)); // /media/<username>/<disk>
    } else {
      p = Paths.get(pathToDisk);
    }

    updateList(p);
  }

}
