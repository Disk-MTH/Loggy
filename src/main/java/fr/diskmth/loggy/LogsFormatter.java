package fr.diskmth.loggy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogsFormatter extends Formatter
{
    // logger, timestamp, separators
    public static final List<StringFormat> DEFAULT_HEADER_FORMAT = List.of(StringFormat.PURPLE_BOLD, StringFormat.CYAN, StringFormat.WHITE_BRIGHT);

    //info, warn, error
    public static final List<StringFormat> LEVELS_FORMAT = List.of(StringFormat.WHITE_BRIGHT);

    /*---------------------------------------- Variables and constants ----------------------------------------*/

    public static final LogsFormatter CONSOLE_FORMATTER = new LogsFormatter(false);
    public static final LogsFormatter FILE_FORMATTER = new LogsFormatter(true);

    protected final boolean isForFile;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public LogsFormatter(boolean isForFile)
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
            logColor = StringFormat.RED.getAnsiCode();
        }
        else if (record.getLevel().getName().equals(Level.WARNING.getName()))
        {
            logColor = StringFormat.YELLOW.getAnsiCode();
        }
        else
        {
            logColor = StringFormat.GREEN.getAnsiCode();
        }

        colorize(message, StringFormat.CYAN.getAnsiCode());
        message.append("[");

        colorize(message, StringFormat.PURPLE.getAnsiCode());
        message.append(record.getLoggerName());

        colorize(message, StringFormat.CYAN.getAnsiCode());
        message.append(" | ");

        colorize(message, StringFormat.PURPLE_BRIGHT.getAnsiCode());
        message.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));

        colorize(message, StringFormat.CYAN.getAnsiCode());
        message.append(" | ");

        colorize(message, logColor);
        message.append(record.getLevel().getName());

        colorize(message, StringFormat.CYAN.getAnsiCode());
        message.append("]");

        colorize(message, StringFormat.WHITE.getAnsiCode());
        message.append(": ");

        colorize(message, !record.getLevel().getName().equals(Level.INFO.getName()) ? logColor : "");
        message.append(record.getMessage());

        colorize(message, StringFormat.RESET.getAnsiCode());
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
