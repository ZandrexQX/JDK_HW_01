package chat.server.server;

public interface ServerView {
    void connectClient(String text);
    void startServer();
    void stopServer();
    void showMessage(String text);
}
