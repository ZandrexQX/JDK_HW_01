package chat.server;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ServerWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 450;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;

    final JButton btnStart = new JButton("Запуск");
    final JButton btnStop = new JButton("Остановка");
    public static final JTextArea log = new JTextArea();
    boolean isServerWork;
    Date date = new Date();
    final SimpleDateFormat formatter = new SimpleDateFormat("[dd MMM YY - hh:mm]");

    public ServerWindow(){
        isServerWork = false;
        log.setEditable(false);

        log.setText(STR."\{formatter.format(date)} Chat running");
        btnStop.addActionListener(e -> {
            isServerWork = false;
            log.setText(STR."\{log.getText()}\n\{formatter.format(date)} Server stopped");
        });

        btnStart.addActionListener(e -> {
            isServerWork = true;
            log.setText(STR."\{log.getText()}\n\{formatter.format(date)} Server started");
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(WINDOW_POSX, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnStop);

        JScrollPane scrolling = new JScrollPane(log);
        add(scrolling);
        add(panBottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}
