package chat.server.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientGUI extends JFrame implements ClientView {
    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 450;

    private JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private JTextField tfIP = new JTextField("127.0.0.1");
    private JTextField tfPort = new JTextField("8189");
    private JTextField tfLogin = new JTextField("Login");
    private JPasswordField tfPass = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");
    private final JPanel panBottom = new JPanel(new BorderLayout());
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Отправить");

    private ClientController client;

    public ClientGUI() {
        settings();
        createPanel();

        setVisible(true);
    }

    public String getLogin() {
        return tfLogin.getText();
    }

    public void setClient(ClientController client) {
        this.client = client;
    }

    private void settings() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat client");
    }

    private void createPanel() {
        createPanelTop();
        createPanelBottom();
        createLog();
    }

    private void createPanelTop() {
        panelTop.add(tfIP);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPass);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);
    }

    private void createPanelBottom() {
        btnLogin.addActionListener(e -> {
            client.connectToServer();
        });

        btnSend.addActionListener(e -> {
            sendMessage(tfMessage.getText());
        });

        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    sendMessage(tfMessage.getText());
                }
            }
        });

        panBottom.add(tfMessage);
        panBottom.add(btnSend, BorderLayout.EAST);
        add(panBottom, BorderLayout.SOUTH);
    }

    private void createLog() {
        log.setEditable(false);
        add(log);
        JScrollPane scrolling = new JScrollPane(log);
        scrolling.setAutoscrolls(true);
        add(scrolling);
    }

    @Override
    public void showMessage(String text) {
        log.append(STR."\n\{text}");
    }

    @Override
    public void disconnectFromServer() {
        panelTop.setVisible(true);
        log.setText("Cервер отключен");
    }

    @Override
    public void connected() {
        panelTop.setVisible(false);
        log.append("Cервер подключен");
        client.setLogin(tfLogin.getText());
        client.isConnected();
        log.append(STR."\n\{client.logServer()}");
    }

    private void sendMessage(String text){
        if (!text.equals("")) {
            log.append(STR."\n\{text}");
            client.messageToServer(text);
            tfMessage.setText("");
        }
    }
}
