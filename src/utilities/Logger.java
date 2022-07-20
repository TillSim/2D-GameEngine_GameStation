package utilities;


import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Logger is a helper class for printing log messages with timestamp to stream and/or file.
 */
public abstract class Logger {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final String LOG_FILE = "log/GameStation.log";


    /**
     * adds timestamp(<i>HH:mm:ss</i>) to String
     * @param logText String
     * @return String
     */
    private static String createLogMSG(String logText) {
        Date date = new Date();
        String timestamp = DATE_FORMAT.format(date.getTime());

        String[] lines = logText.split("\n");
        StringBuilder logMSG = new StringBuilder("[" + timestamp + "]\t" + lines[0]);

        if (lines.length > 1) for (int i = 1 ; i < lines.length ; i++) logMSG.append("\n\t\t\t").append(lines[i]);

        return logMSG.toString();
    }

    /**
     * prints log message with timestamp(HH:mm:ss) to stream
     * @param logText String
     */
    public static void toStream(String logText) {System.out.println(createLogMSG(logText));}

    /**
     * prints log message with timestamp(HH:mm:ss) to file
     * @param logText String
     */
    public static void toFile(String logText) {FileHandler.write(LOG_FILE, createLogMSG(logText) + "\n", true);}

    /**
     * prints log message with timestamp(HH:mm:ss) to stream and file
     * @param logText String
     */
    public static void toAll(String logText) {
        toStream(logText);
        toFile(logText);
    }

    /**
     * deletes log file
     */
    public static void clearLog() {
        FileHandler.delete(LOG_FILE);
    }

}
