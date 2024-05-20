package chat.server;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final JPanel panBottom = new JPanel(new GridLayout(1,2));
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Отправить");

    private boolean isLogin = false;

    public ClientGUI(ServerWindow serverWindow, String login){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat client");
        tfLogin.setText(login);

        btnLogin.addActionListener(e -> {
            if(serverWindow.isServerWork) {
                isLogin = true;
                log.setText(STR."\{serverWindow.formatter.format(serverWindow.date)} Вы подключены");
                serverWindow.log.setText(STR."\{serverWindow.log.getText()}\n"+
                        STR."\{serverWindow.formatter.format(serverWindow.date)} \{tfLogin.getText()} подключен");
            }else{
                log.setText("Сервер не работает");
            }
        });

        btnSend.addActionListener(e -> {
            if(isLogin) {
                String message = STR."\{serverWindow.formatter.format(serverWindow.date)}" +
                        STR." \{tfMessage.getText()}";
                log.setText(STR."\{log.getText()}\n\{message}");
                serverWindow.log.setText(STR."\{serverWindow.log.getText()}\n[\{tfLogin.getText()}]: \{message}");
            } else {
                log.setText("Нет соединения");
            }
            tfMessage.setText("");
        });

        serverWindow.btnStart.addActionListener(e -> {log.setText("Сервер подключен");});
        serverWindow.btnStop.addActionListener(e -> {
            log.setText("Сервер отключен");
            isLogin = false;
        });

        serverWindow.log.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if(isLogin){
                    log.setText(serverWindow.log.getText());
                }
            }
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
//        this.log = serverWindow.log;
        log.setEditable(false);
        add(log);
        JScrollPane scrolling = new JScrollPane(log);
        add(scrolling);

        setVisible(true);
    }

}
