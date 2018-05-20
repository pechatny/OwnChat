package server;

public interface ConnectionInterface {
    public void onConnectionReady(ConnectionProvider connection);
    public void onConnectionClose(ConnectionProvider connection);
    public void onSend(ConnectionProvider connection);
    public void onReceive(ConnectionProvider connection, String message);
    public void onException(ConnectionProvider connection, String message);
}
