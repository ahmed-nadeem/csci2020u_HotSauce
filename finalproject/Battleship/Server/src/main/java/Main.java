import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private AnchorPane layout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Server");

        initLayout();
    }

    private void initLayout() {
        try {
            // loading FXML object
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ServerController.class.getResource("Server.fxml"));
            layout = loader.load();

            Scene scene = new Scene(layout);
            primaryStage.setScene(scene);
            primaryStage.show();
            // creating instance of the ServerController
            ServerController controller = loader.getController();
            // for closing the server
            scene.getWindow().setOnCloseRequest((WindowEvent event) -> {
                controller.closeGameServer();
                Platform.exit();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
