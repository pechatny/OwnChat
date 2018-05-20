package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("chatWindow.fxml"));
        primaryStage.setTitle("Дикий");
        primaryStage.setScene(new Scene(root, 302, 427));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
