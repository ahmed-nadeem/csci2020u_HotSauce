//Controller class for managing Client end interface.


package controller;         //Package for controller class


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Coordinates;
import model.FieldState;
import model.Player;
import network.ConnectionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.Msg;
import protocol.MsgType;

import java.util.HashMap;

public class Controller {
    private static final Logger logger = LogManager.getLogger("Client");            //Method to find Client logger
    private Player player;                                                          //Initiates Player
    private int shipLength;                                                         //Initiates length of battleship    
    private int currentNumOfFieldsTaken;                                            //Initiates number of squares taken in field
    private int numOfShipsPlaced;                                                   //Initiates the total number of ships placed in field(MAX:5)
    private ConnectionHandler connectionHandler;
    private MsgHandler msgHandler;

    //Controller class for creating new player
    public Controller() {                                           
        player = new Player(null);
    }

    //Connection Handler returning Handler name
    ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    @FXML
    private GridPane yourGrid;                  //Initializes Client Grid

    @FXML
    private GridPane enemyGrid;                 //Initializes Enemy Grid

    @FXML
    private Label status;                       //Initializes Status of Connection

    @FXML
    private TextField serverAddress;            //Initializes Server Address

    @FXML
    private TextField serverPort;               //Initializes Server Port

    @FXML
    private Button connectButton;               //Initializes connect button

    @FXML
    private Button finishedButton;              //Initializes finished button

    @FXML
    private MenuButton shipsMenuBar;            //Initializes Ship Menu Bar
    
    
    //Initialization of variables to specific values
    @FXML
    private void initialize() {
        finishedButton.setDisable(true);
        shipsMenuBar.setDisable(true);
        status.setText("Not connected");

        currentNumOfFieldsTaken = 0;
        numOfShipsPlaced = 0;
    }

    //Confirmation of server response. 
    @FXML
    private void handleFinishedButtonFired() {
        Msg answer = new Msg(MsgType.SHIPS_PLACED, player.getPlayerId(), player.getPlayerMap());
        connectionHandler.getMessagesToSend().add(answer);

        status.setText("Wait for server response");
        finishedButton.setDisable(true);
        setGridIsDisable(yourGrid, true);
    }

    //Connection handler between port and Server Address
    @FXML
    private void handleConnectButtonFired() {
        serverAddress.setDisable(true);
        serverPort.setDisable(true);

        String address = serverAddress.getText();
        int port = Integer.parseInt(serverPort.getText());

        connectionHandler = new ConnectionHandler(address, port);

        msgHandler = new MsgHandler(this);
        msgHandler.start();
    }

    ;

    //Place Ships of length according to chosen from menu bar
    @FXML
    private void handleMenuItemSelected(ActionEvent event) {
        status.setText("Place ship");
        shipsMenuBar.setDisable(true);

        setGridIsDisable(yourGrid, false);

        MenuItem menuItem = (MenuItem) event.getSource();
        menuItem.setDisable(true);

        shipLength = getShipLength(menuItem);
    }

  
    @FXML
    private void handleEnemyGridCellButtonFired(ActionEvent event) {
        setGridIsDisable(enemyGrid, true);

        Node node = (Node) event.getSource();

        Coordinates shotCoordinates = new Coordinates(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
        Msg shotMsg = new Msg(MsgType.SHOT_PERFORMED, player.getPlayerId(), shotCoordinates);

        connectionHandler.getMessagesToSend().add(shotMsg);
    }

    @FXML
    private void handleYourGridCellButtonFired(ActionEvent event) {
        Node node = (Node) event.getSource();
        node.setDisable(true);
        node.setStyle("-fx-background-color: deepskyblue");
        ++currentNumOfFieldsTaken;

        logger.info("Curr: " + currentNumOfFieldsTaken + ", numOfFields: " + shipLength);

        player.getPlayerMap().setFieldState(GridPane.getRowIndex(node), GridPane.getColumnIndex(node), FieldState.SHIP);

        if (currentNumOfFieldsTaken == shipLength) {
            currentNumOfFieldsTaken = 0;
            ++numOfShipsPlaced;

            shipsMenuBar.setDisable(false);
            setGridIsDisable(yourGrid, true);

            if (numOfShipsPlaced == 5) {
                shipsMenuBar.setDisable(true);
                finishedButton.setDisable(false);
            }
        }
    }

    void handleSetId(Msg msg) {
        player.setPlayerId(msg.getPlayerID());
        connectionHandler.getMessagesToSend().add(new Msg(MsgType.ID_IS_SET, msg.getPlayerID()));

        Platform.runLater(() -> {
            status.setText("Waiting for game to begin");
            connectButton.setDisable(true);
        });
    }

    void handlePlaceShips() {
        Platform.runLater(() -> {
            shipsMenuBar.setDisable(false);
            status.setText("Choose ship from menu bar");
        });
    }

    void handleMakeMove() {
        Platform.runLater(() -> {
            status.setText("Make move");
            setGridIsDisable(enemyGrid, false);
        });
    }

    void handleWaitForMove() {
        connectionHandler.getMessagesToSend().add(new Msg(MsgType.WAITING, player.getPlayerId()));

        Platform.runLater(() -> {
            status.setText("Wait for move");
        });
    }

    void handleHitMakeMove(Integer row, Integer col) {
        updateGUI("You have been shot! Your turn", yourGrid, enemyGrid, false, "red",
                row, col);
    }

    void handleHitWaitForMove(Integer row, Integer col) {
        connectionHandler.getMessagesToSend().add(new Msg(MsgType.WAITING, player.getPlayerId()));
        updateGUI("You have hit the enemy! Good job", enemyGrid, enemyGrid, true, "red",
                row, col);
    }

    void handleMissMakeMove(Integer row, Integer col) {
        updateGUI("Enemy didn't hit you. Your move", yourGrid, enemyGrid, false, "black",
                row, col);
    }

    void handleMissWaitForMove(Integer row, Integer col) {
        connectionHandler.getMessagesToSend().add(new Msg(MsgType.WAITING, player.getPlayerId()));
        updateGUI("You didn't hit. Wait for move", enemyGrid, enemyGrid, true, "black",
                row, col);
    }

    void handleLose(Integer row, Integer col) {
        updateGUI("You have lost", yourGrid, enemyGrid, true, "red", row, col);
    }

    void handleWin(Integer row, Integer col) {
        updateGUI("You have won", enemyGrid, enemyGrid, true, "red", row, col);
    }

    /**
     statusVal             Text which will appear in status TextField
     gridToUpdate          Grid which will be updated with a shot result
     gridToChangeIsDisable Grid which status will be changed to enable or prevent shooting
     newGridState          A value to which gridToChangeIsDisable will be changed
     color                 Color which will appear in cell of updated grid
     row                   Row of cell to update
     col                   Column of cell to update
     */
    void updateGUI(String statusVal, GridPane gridToUpdate, GridPane gridToChangeIsDisable, boolean newGridState,
                   String color, Integer row, Integer col) {
        Platform.runLater(() -> {
            status.setText(statusVal);

            gridToUpdate.getChildren().get(row * 10 + col).setStyle("-fx-background-color: " + color);
            setGridIsDisable(gridToChangeIsDisable, newGridState);
        });
    }

    private Integer getShipLength(MenuItem menuItem) {
        HashMap<String, Integer> tmp = new HashMap<>();
        tmp.put("Carrier[Size 5]", 5);
        tmp.put("Battleship[Size 4]", 4);
        tmp.put("Cruiser[Size 3]", 3);
        tmp.put("Submarine[Size 3]", 3);
        tmp.put("Destroyer[Size 2]", 2);

        return tmp.get(menuItem.getText());
    }

    private void setGridIsDisable(GridPane gridPane, boolean isGridDisable) {
        for (Node node : gridPane.getChildren()) {
            node.setDisable(isGridDisable);
        }
        ;
    }

    public void close() {
        if (connectionHandler != null) {
            connectionHandler.closeConnection();
        }

        if (msgHandler != null && msgHandler.isAlive()) {
            msgHandler.interrupt();
        }
        logger.info("ConnectionHandler interrupted");
    }
}
