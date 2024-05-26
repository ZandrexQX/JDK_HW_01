package chat.server.server;

import chat.server.client.ClientController;

import java.util.ArrayList;
import java.util.List;

import static chat.server.server.Message.FormatMessage;

public class ServerController {
    private ServerView view;

    private boolean isServerWork = false;
    private String logServer;

    private List<ClientController> clients = new ArrayList<>();

    public ServerController(ServerView view) {
        this.view = view;
    }

    private void addClient(ClientController client){
        if (client != null) {
            this.clients.add(client);
            System.out.println(client);
        }
    }

    public void sendMessagesFromUser(ClientController clientController, String message){
        String text = message;
        if (clientController != null) {
            text = FormatMessage(STR."[\{clientController.getLogin()}] \{message}");
        }
        view.showMessage(text);
        for (ClientController client : clients){
            if (client != clientController){
                client.messageFromServer(text);
            }
        }
    }

    public void sendMessagesFromServer(String message){
        view.showMessage(message);
        for (ClientController client : clients){
            client.messageFromServer(message);
        }
    }

    void startServer(){
        if(!isServerWork) {
            isServerWork = true;
            view.showMessage(FormatMessage("Server started"));
        }
    }

    void stopServer(){
        isServerWork = false;
        sendMessagesFromServer(FormatMessage("Server stopped"));
        for (ClientController client : clients){
            client.disconnect();
        }
        clients.clear();
    }

    public boolean connectClient(ClientController client){
        if (isServerWork) {
            addClient(client);
            return true;
        }
        return false;
    }

    public void clientIsConnect(ClientController client){
        String text = FormatMessage(STR."\{client.getLogin()} подключен");
        sendMessagesFromServer(text);
        view.connectClient(text);
    }

    public String getLogServer() {
        return logServer;
    }

    public void setLogServer(String logServer) {
        this.logServer = logServer;
    }
}
