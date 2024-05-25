package chat.server;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {
    private static int ID = 0;
    private int IDClient;

    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 450;

    private JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private JTextField tfIP = new JTextField("127.0.0.1");
    private JTextField tfPort = new JTextField("8189");
    private JTextField tfLogin = new JTextField("Login");
    private JPasswordField tfPass = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panBottom = new JPanel(new GridLayout(1, 2));
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Отправить");

    private ServerWindow server;

    private boolean isLogin = false;

    public ClientGUI(ServerWindow serverWindow, String login) {
        setLocationRelativeTo(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat client");
        tfLogin.setText(login);
        this.server = serverWindow;
        this.ID += 1;
        this.IDClient = this.ID;

        createPanel();
    }

    void connected(){
        isLogin = true;
        panelTop.setVisible(false);
    }

    String getLogin(){
        return tfLogin.getText();
    }

    void sendMessage(String message){
        log.append(STR."\n\{server.getFormatter()} \{message}");
    }

    void answer(String message){
        if (!message.equals("")){
            sendMessage(message);
            server.sendMessages(this.IDClient, STR."[\{getLogin()}] \{message}");
        }

    }

    int getID(){
        return this.IDClient;
    }

    void logout(){
        this.isLogin = false;
        panelTop.setVisible(true);
    }

    private void createPanel(){
        createPanelTop();
        createPanelBottom();
        createLog();

        setVisible(true);
    }

    private void createPanelTop(){
        panelTop.add(tfIP);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPass);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);
    }

    private void createPanelBottom(){
        btnLogin.addActionListener(e -> {
            server.connectClient(this);
            if (isLogin) {
                log.setText("Вы подключены");
                String logServer = server.getLog();
                if (logServer != null) sendMessage(logServer);
            } else {
                log.setText("Сервер не работает");
            }
        });

        btnSend.addActionListener(e -> {
            if (isLogin) {
                answer(STR."\{tfMessage.getText()}");
            } else {
                log.setText("Нет соединения");
            }
            tfMessage.setText("");
        });

        panBottom.add(tfMessage);
        panBottom.add(btnSend);
        add(panBottom, BorderLayout.SOUTH);
    }

    private void createLog(){
        log.setEditable(false);
        add(log);
        JScrollPane scrolling = new JScrollPane(log);
        add(scrolling);
    }
}
