package chat.server.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    public static class DateNow {
        private Date date = new Date();
        private final SimpleDateFormat formatter = new SimpleDateFormat("[dd MMM YY - hh:mm]");

        public String getFormatter(){
            return formatter.format(date);
        }
    }

    public static String FormatMessage(String message){
        DateNow dateNow = new DateNow();
        return STR."\{dateNow.getFormatter()} - \{message}";
    }
}
