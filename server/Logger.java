package chat.server.server;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {
    private static String LOG_PATH = "log.txt";

    static String getLog() throws IOException{
        try {
            String logText = Files.lines(Paths.get(LOG_PATH)).reduce("", (a, b) -> STR."\{a}\n\{b}");
            return logText.trim();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static void saveLog(String text) throws IOException{
        try (FileWriter fw = new FileWriter("log.txt", false)){
            fw.write(text.trim());
            fw.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
