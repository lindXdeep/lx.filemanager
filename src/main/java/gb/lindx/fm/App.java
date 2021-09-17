package gb.lindx.fm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  private static int sW = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
  private static int sH = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(this.getClass().getResource("/main.fxml"));

    Parent root = loader.load();
    Scene scene = new Scene(root, sW*0.8, sH*0.5);

    primaryStage.setScene(scene);
    primaryStage.setTitle("lx / File Manager");

    primaryStage.show();
  }
}