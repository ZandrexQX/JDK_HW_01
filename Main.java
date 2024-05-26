package chat.server;

import chat.server.client.ClientController;
import chat.server.client.ClientGUI;
import chat.server.server.ServerController;
import chat.server.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        ServerController serverController = new ServerController(serverWindow);
        serverWindow.setServer(serverController);

        ClientGUI client1 = new ClientGUI();
        ClientController clientController1 = new ClientController(client1, serverController);
        client1.setClient(clientController1);

        ClientGUI client2 = new ClientGUI();
        ClientController clientController2 = new ClientController(client2, serverController);
        client2.setClient(clientController2);
    }
}
