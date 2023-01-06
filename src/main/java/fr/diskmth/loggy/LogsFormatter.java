package fr.diskmth.loggy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogsFormatter extends Formatter
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    public static final LogsFormatter CONSOLE_FORMATTER = new LogsFormatter(false);
    public static final LogsFormatter FILE_FORMATTER = new LogsFormatter(true);

    protected final boolean isForFile;

    /*---------------------------------------- Constructors ----------------------------------------*/

    protected LogsFormatter(boolean isForFile)
    {
        this.isForFile = isForFile;
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    @Override
    public String format(LogRecord record)
    {
        final StringBuilder message = new StringBuilder();
        final String logColor;

        if (record.getLevel().getName().equals(Level.SEVERE.getName()))
        {
            logColor = Colors.RED;
        }
        else if (record.getLevel().getName().equals(Level.WARNING.getName()))
        {
            logColor = Colors.YELLOW;
        }
        else
        {
            logColor = Colors.GREEN;
        }

        colorize(message, Colors.CYAN);
        message.append("[");

        colorize(message, Colors.PURPLE);
        message.append(record.getLoggerName());

        colorize(message, Colors.CYAN);
        message.append(" | ");

        colorize(message, Colors.WHITE);
        message.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));

        colorize(message, Colors.CYAN);
        message.append(" | ");

        colorize(message, logColor);
        message.append(record.getLevel().getName());

        colorize(message, Colors.CYAN);
        message.append("]");

        colorize(message, Colors.WHITE);
        message.append(": ");

        colorize(message, !record.getLevel().getName().equals(Level.INFO.getName()) ? logColor : "");
        message.append(record.getMessage());

        colorize(message, Colors.RESET);
        message.append("\n");

        return message.toString();
    }

    private void colorize(StringBuilder message, String color)
    {
        if (!this.isForFile)
        {
            message.append(color);
        }
    }
}
