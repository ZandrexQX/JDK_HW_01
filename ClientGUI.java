package chat.server;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {
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
        server.addClient(this);

        btnLogin.addActionListener(e -> {
            server.connectClient(this);
            if (isLogin) {
                log.setText("Вы подключены");
            } else {
                log.setText("Сервер не работает");
            }
        });

        btnSend.addActionListener(e -> {
            if (isLogin) {
                sendMessage(STR."\{tfMessage.getText()}");
                server.sendLog(STR."[\{tfLogin.getText()}] \{tfMessage.getText()}");
            } else {
                log.setText("Нет соединения");
            }
            tfMessage.setText("");
        });


        panelTop.add(tfIP);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPass);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        panBottom.add(tfMessage);
        panBottom.add(btnSend);
        add(panBottom, BorderLayout.SOUTH);
        log.setEditable(false);
        add(log);
        JScrollPane scrolling = new JScrollPane(log);
        add(scrolling);

        setVisible(true);
    }

    void connected(){
        isLogin = true;
        panelTop.setVisible(false);
    }

    String getLogin(){
        return tfLogin.getText();
    }

    void sendMessage(String message){
        log.setText(STR."\{log.getText()}\n\{server.getFormatter()} \{message}");
    }
}
