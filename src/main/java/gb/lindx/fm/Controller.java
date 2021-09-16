package gb.lindx.fm;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class Controller implements Initializable {

  @FXML
  private TableView filesTable;
  
  public void btnExitAction(ActionEvent event){
      Platform.exit();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {


  }
}
