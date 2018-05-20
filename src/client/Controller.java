package client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import server.ConnectionInterface;
import server.ConnectionProvider;

import java.io.IOException;

public class Controller implements ConnectionInterface {
    private ConnectionProvider connection;

    @FXML
    TextArea textBox;

    @FXML
    TextField inputVal;

    @FXML
    void buttonPress() {
        connection.send(inputVal.getText());
        inputVal.clear();
        inputVal.requestFocus();
    }

    public Controller() {
        try {
            connection = new ConnectionProvider(this, "localhost", 8089);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onConnectionReady(ConnectionProvider connection) {
        addText("Мы подключились.");
    }

    @Override
    public synchronized void onConnectionClose(ConnectionProvider connection) {
        addText("Мы отключились.");
    }

    @Override
    public synchronized void onSend(ConnectionProvider connection) {
        System.out.println("OnSend on client");
    }

    @Override
    public synchronized void onReceive(ConnectionProvider connection, String message) {
        System.out.println("Client got message " + message);
        System.out.println("From " + connection.socket.getInetAddress() + ":" + connection.socket.getPort());
//        textBox.appendText(message + "\r\n");
        addText(message);
    }

    @Override
    public synchronized void onException(ConnectionProvider connection, String message) {

    }


    private synchronized void addText(String text) {
        try {
            textBox.appendText(text + "\r\n");
            System.out.println(text);
        } catch (NullPointerException e) {
            System.out.println("text box undefined");
        }
    }
}
