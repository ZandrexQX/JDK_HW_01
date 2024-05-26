package chat.server.server;

import chat.server.client.ClientController;
import chat.server.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ServerWindow extends JFrame implements ServerView {
    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 450;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;
    private static final String TITLE = "Chat server";

    private final JButton btnStart = new JButton("Запуск");
    private final JButton btnStop = new JButton("Остановка");
    private static final JTextArea log = new JTextArea();

    private ServerController server;

    public ServerWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(WINDOW_POSX, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle(TITLE);
        setAlwaysOnTop(true);

        createPanelBottom();
        createLog();

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                try {
                    log.setText(Logger.getLog());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    Logger.saveLog(log.getText().trim());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setServer(ServerController server) {
        this.server = server;
    }

    private void createPanelBottom(){
        btnStop.addActionListener(e -> {
            stopServer();
        });

        btnStart.addActionListener(e -> {
            startServer();
        });

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnStop);
        add(panBottom, BorderLayout.SOUTH);
    }

    private void createLog(){
        log.setEditable(false);
        JScrollPane scrolling = new JScrollPane(log);
        add(scrolling);
    }

    @Override
    public void connectClient(String text) {
        server.setLogServer(log.getText());
    }

    @Override
    public void startServer() {
        server.startServer();
    }

    @Override
    public void stopServer() {
        server.stopServer();
    }

    @Override
    public void showMessage(String text) {
        log.append(STR."\n\{text}");
    }
}
