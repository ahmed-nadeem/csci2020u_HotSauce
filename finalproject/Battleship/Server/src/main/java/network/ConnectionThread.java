package network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.Msg;
import protocol.MsgType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

// class for the connection thread
public class ConnectionThread extends Thread {
    private int id;
    private ObjectOutputStream toClient;
    private ObjectInputStream fromClient;
    private Socket clientSocket;
    private ArrayBlockingQueue<Msg> gameMessages;

    private static final Logger logger = LogManager.getLogger("Server");
    
    // connection thread method
    ConnectionThread(int id, Socket clientSocket, ArrayBlockingQueue<Msg> gameMessages) {
        this.id = id;
        this.gameMessages = gameMessages;
        this.clientSocket = clientSocket;

        try {
            toClient = new ObjectOutputStream(clientSocket.getOutputStream());
            fromClient = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    
    // writing to client
    void write(Msg msg) {
        try {
            toClient.writeObject(msg);
        } catch (IOException e) {
            logger.error("Exception during writing to client stream");
        }
    }
    // closing client socket
    void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.info("Client socket closed");
        }
    }
    
    // executing thread
    @Override
    public void run() {
        logger.info("Thread started");

        write(new Msg(MsgType.SET_ID, id));

        try {
            Msg msgFromClient;
            while ((msgFromClient = (Msg) fromClient.readObject()) != null) {
                gameMessages.add(msgFromClient);
            }
        } catch (Exception e) {
            logger.info("Exception occurred during thread execution");
        }
    }
}
