package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

public class MainServer implements ConnectionInterface{
    private static Vector<ConnectionProvider> connections = new Vector<>();

    public static void main(String[] args) {
        new MainServer();
    }

    private MainServer(){
        try {
            ServerSocket server = new ServerSocket(8089);
            while (true) {
                new ConnectionProvider(MainServer.this, server.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onConnectionReady(ConnectionProvider connection) {
        connections.add(connection);
        broadCastMessage("You connected");
        System.out.println("Connected");
    }

    @Override
    public synchronized void onConnectionClose(ConnectionProvider connection) {

    }

    @Override
    public synchronized void onSend(ConnectionProvider connection) {
        System.out.println("OnSend on server");
    }

    @Override
    public synchronized void onReceive(ConnectionProvider connection, String message) {
        broadCastMessage(message);
    }

    @Override
    public synchronized void onException(ConnectionProvider connection, String message) {

    }

    private synchronized void broadCastMessage(String message){
        System.out.println("broadcast started " + message);
        for (ConnectionProvider connection : connections){
            System.out.println("suka " + message);
            connection.send(message);
        }
    }
}
