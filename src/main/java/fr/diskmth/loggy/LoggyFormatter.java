package fr.diskmth.loggy;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public abstract class LoggyFormatter
{
    /*---------------------------------------- Static constants ----------------------------------------*/

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

    public abstract HashMap<LoggyLevel, List<ANSI>> levelsFormat();

    public abstract String format(LoggyLevel level, String loggy, String message);

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
