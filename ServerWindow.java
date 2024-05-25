package chat.server;

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


public class ServerWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 450;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;

    private final JButton btnStart = new JButton("Запуск");
    private final JButton btnStop = new JButton("Остановка");
    private static final JTextArea log = new JTextArea();
    private boolean isServerWork = false;
    private Date date = new Date();
    private final SimpleDateFormat formatter = new SimpleDateFormat("[dd MMM YY - hh:mm]");

    private List<ClientGUI> clients = new ArrayList<ClientGUI>();

    public ServerWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(WINDOW_POSX, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        sendLog("Chat running");

        createPanelBottom();
        createLog();

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                try {
                    String logText = Files.lines(Paths.get("log.txt")).reduce("", (a,b) -> STR."\{a}\n\{b}");
                    log.setText(logText.trim());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try (FileWriter fw = new FileWriter("log.txt", false)){
                    fw.write(log.getText().trim());
                    fw.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    void connectClient(ClientGUI client){
        if (isServerWork) {
            addClient(client);
            client.connected();
            sendMessages(0, STR."\{client.getLogin()} подключен");
        }
    }

    private void addClient(ClientGUI client){
        if (client != null) {
            System.out.println(client.getID());
            this.clients.add(client);
        }
    }

    private void sendLog(String message){
        log.append(STR."\n\{formatter.format(date)} \{message}");
    }

    String getFormatter(){
        return formatter.format(date);
    }

    void sendMessages(int ID, String message){
        sendLog(message);
        for (ClientGUI client : clients){
            if (client.getID() != ID){
                client.sendMessage(STR."\{message}");
            }
        }
    }

    String getLog(){
      return log.getText().trim();
    };

    private void createPanelBottom(){
        btnStop.addActionListener(e -> {
            isServerWork = false;
            sendMessages(0, "Server stopped");
            for (ClientGUI client : clients){
                client.logout();
            }
            clients.clear();
        });

        btnStart.addActionListener(e -> {
            isServerWork = true;
            sendMessages(0, "Server started");
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
}
