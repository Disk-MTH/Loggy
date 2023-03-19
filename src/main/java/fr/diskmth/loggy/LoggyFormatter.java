package fr.diskmth.loggy;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * An abstract class for formatting log messages using ANSI codes for color and style.
 *
 * @author Disk_MTH
 * @version 1.4
 * @since Loggy 1.0
 */
public abstract class LoggyFormatter
{
    /*---------------------------------------- Static constants ----------------------------------------*/

    /**
     * The default LoggyFormatter with predefined ANSI codes for each log level.
     */
    public static final LoggyFormatter DEFAULT = new LoggyFormatter()
    {
        @Override
        public HashMap<LoggyLevel, List<ANSI>> levelsFormat()
        {
            return new HashMap<LoggyLevel, List<ANSI>>()
            {{
                put(LoggyLevel.FATAL, Arrays.asList
                        (
                                ANSI.multiple(ANSI.BOLD, ANSI.REVERSE, ANSI.RED_BRIGHT),
                                ANSI.RED_BRIGHT
                        ));
                put(LoggyLevel.ERROR, Arrays.asList
                        (
                                ANSI.multiple(ANSI.BOLD, ANSI.RED),
                                ANSI.RED
                        ));
                put(LoggyLevel.WARN, Arrays.asList
                        (
                                ANSI.multiple(ANSI.BOLD, ANSI.YELLOW),
                                ANSI.YELLOW
                        ));
                put(LoggyLevel.INFO, Arrays.asList
                        (
                                ANSI.multiple(ANSI.BOLD, ANSI.GREEN),
                                ANSI.WHITE_BRIGHT
                        ));
                put(LoggyLevel.DEBUG, Arrays.asList
                        (
                                ANSI.multiple(ANSI.BOLD, ANSI.CYAN),
                                ANSI.CYAN
                        ));
            }};
        }

        @Override
        public String format(LoggyLevel level, String loggy, String message)
        {
            return ANSI.format("[", ANSI.BLUE_BRIGHT) +
                    ANSI.format(loggy, ANSI.MAGENTA) +
                    ANSI.format(" | ", ANSI.BLUE_BRIGHT) +
                    ANSI.format(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), ANSI.WHITE) +
                    ANSI.format(" | ", ANSI.BLUE_BRIGHT) +
                    ANSI.format(level.name(), levelsFormat().get(level) != null ? levelsFormat().get(level).get(0) : ANSI.WHITE) +
                    ANSI.format("]", ANSI.BLUE_BRIGHT) +
                    ANSI.format(": ", ANSI.WHITE) +
                    ANSI.format(message, levelsFormat().get(level) != null ? levelsFormat().get(level).get(1) : ANSI.WHITE) + "\n";
        }
    };

    /*---------------------------------------- Static methods ----------------------------------------*/

    /**
     * Converts a LoggyFormatter to a Java Formatter.
     *
     * @param formatter the LoggyFormatter to convert
     * @return a Java Formatter with the same formatting as the given LoggyFormatter
     */
    public static Formatter toJavaFormatter(LoggyFormatter formatter)
    {
        return new Formatter()
        {
            @Override
            public String format(LogRecord record)
            {
                return formatter.format(LoggyLevel.fromJavaLevel(record.getLevel()), record.getLoggerName(), record.getMessage());
            }
        };
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    /**
     * Returns the ANSI codes for each log level.
     *
     * @return a HashMap of LoggyLevel to a List of ANSI codes
     */
    public abstract HashMap<LoggyLevel, List<ANSI>> levelsFormat();

    /**
     * Formats a log message using ANSI codes.
     *
     * @param level   the log level
     * @param loggy   the logger name
     * @param message the log message
     * @return the formatted log message as a String
     */
    public abstract String format(LoggyLevel level, String loggy, String message);

    /**
     * Returns a LoggyFormatter that clears all ANSI codes from the parent LoggyFormatter's output.
     *
     * @return a LoggyFormatter that does not use ANSI codes
     */
    public LoggyFormatter getFileFormatter()
    {
        final LoggyFormatter parent = this;

        return new LoggyFormatter()
        {
            @Override
            public HashMap<LoggyLevel, List<ANSI>> levelsFormat()
            {
                return parent.levelsFormat();
            }

            @Override
            public String format(LoggyLevel level, String loggy, String message)
            {
                return ANSI.clearFormat(parent.format(level, loggy, message));
            }
        };
    }
}
