package gb.lindx.fm;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class Controller{

  public void btnExitAction(ActionEvent event) {
    Platform.exit();
  }
}
