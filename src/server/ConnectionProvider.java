package server;

import java.io.*;
import java.net.Socket;

public class ConnectionProvider {
    public Socket socket;
    private DataInputStream reader;
    private DataOutputStream writer;
    private Thread connectionThread;
    private ConnectionInterface listener;

    public ConnectionProvider(ConnectionInterface connectionListener, String host, Integer port) throws IOException {
        this(connectionListener, new Socket(host, port));
    }

    public ConnectionProvider(ConnectionInterface connectionListener, Socket socket) {
        this.socket = socket;
        this.listener = connectionListener;
        try {
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionThread = new Thread(() -> {
            listener.onConnectionReady(ConnectionProvider.this);
            while (true){
                try {
                    String text = reader.readUTF();
                    listener.onReceive(ConnectionProvider.this, text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        connectionThread.start();
    }

    public synchronized void send(String line){
        System.out.println("Writer line: " + line);
        try {
            writer.writeUTF(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.onSend(ConnectionProvider.this);
    }

    public synchronized void disconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            listener.onConnectionClose(this);
        }
    }
}
