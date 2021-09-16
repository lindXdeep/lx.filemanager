package gb.lindx.fm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(this.getClass().getResource("/main.fxml"));

    Parent root = loader.load();
    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}