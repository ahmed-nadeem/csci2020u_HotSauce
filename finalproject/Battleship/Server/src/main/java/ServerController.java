import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import network.GameServer;

import org.apache.logging.log4j.LogManager;         // importing dependencies to handle the logs from client and server
import org.apache.logging.log4j.Logger;

// creating a class that controls the server
public class ServerController {
    private static final Logger logger = LogManager.getLogger("Server");
    private GameServer gs;

    public ServerController() {
    }

    @FXML
    private Text serverStatus;

    @FXML
    private TextField portNumber;

    @FXML
    private Button startServerButton;

    @FXML
    private void initialize() {
    }
    
    // action event for when server button is clicked
    @FXML
    private void handleStartServerButtonClicked(ActionEvent event) {
        startServerButton.setDisable(true);

        String portNumberString = portNumber.getText();
        int port;
        
        // for valid port number
        try {
            port = Integer.parseInt(portNumberString);
        } catch (NumberFormatException e) {
            logger.error("Wrong port number. Using default port number: 4444");
            port = 4444;
        }
        gs = new GameServer(port);
        gs.start();

        serverStatus.setText("Server is running");
        serverStatus.setFill(Paint.valueOf("Green"));
    }
    // for closing game server
    void closeGameServer() {
        try {
            gs.closeServer();
            gs.interrupt();
        } catch (Exception e) {
            logger.warn("Exception occurred during server close");
        }
    }
}
