package chat.server.client;

import chat.server.server.ServerController;

public class ClientController {

    private ClientInfo info;
    private ClientView view;
    private ServerController server;

    private boolean isLogin = false;

    public ClientController(ClientView view, ServerController server) {
        this.view = view;
        this.server = server;
        this.info = new ClientInfo();
    }

    public void setLogin(String name) {
        info.setLogin(name);
    }

    public String getLogin(){
        return info.getLogin();
    }

    public void disconnect() {
        this.isLogin = false;
        view.disconnectFromServer();
    }

    public String logServer(){
        return server.getLogServer();
    }

    public void connectToServer(){
        if(server.connectClient(this)) {
            isLogin = true;
            view.connected();
        }else{
            view.disconnectFromServer();
        }
    }
    public void isConnected(){
        server.clientIsConnect(this);
    }

    public void messageToServer(String text){
        if (isLogin) {
            if (!text.equals("")) {
                server.sendMessagesFromUser(this, text);
            }
        } else {
            view.showMessage("Нет соединения");
        }
    }

    public void messageFromServer(String text){
        view.showMessage(text);
    }
}
