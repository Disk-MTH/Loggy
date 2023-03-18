package fr.diskmth.loggy;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public abstract class LoggyFormatter
{
    public static final LoggyFormatter DEFAULT = new LoggyFormatter()
    {
        @Override
        public HashMap<LoggyLevel, List<StringFormat>> levelsFormat()
        {
            return new HashMap<>()
            {{
                put(LoggyLevel.FATAL, Arrays.asList(StringFormat.RED_BOLD, StringFormat.RED));
                put(LoggyLevel.ERROR, Arrays.asList(StringFormat.RED_BOLD_BRIGHT, StringFormat.RED_BRIGHT));
                put(LoggyLevel.WARN, Arrays.asList(StringFormat.YELLOW_BOLD, StringFormat.YELLOW));
                put(LoggyLevel.INFO, Arrays.asList(StringFormat.GREEN_BOLD, StringFormat.WHITE));
                put(LoggyLevel.DEBUG, Arrays.asList(StringFormat.CYAN_BOLD, StringFormat.CYAN));
            }};
        }

        @Override
        public String format(LoggyLevel level, String loggy, String message)
        {
            return StringFormat.format("[", StringFormat.CYAN_BOLD) +
                    StringFormat.format(loggy, StringFormat.PURPLE_BRIGHT) +
                    StringFormat.format(" | ", StringFormat.CYAN_BOLD) +
                    StringFormat.format(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), StringFormat.WHITE_BOLD_BRIGHT) +
                    StringFormat.format(" | ", StringFormat.CYAN_BOLD) +
                    StringFormat.format(level.name(), levelsFormat().get(level) != null ? levelsFormat().get(level).get(0) : StringFormat.WHITE_BRIGHT) +
                    StringFormat.format("]", StringFormat.CYAN_BOLD) +
                    StringFormat.format(": ", StringFormat.WHITE_BOLD_BRIGHT) +
                    StringFormat.format(message, levelsFormat().get(level) != null ? levelsFormat().get(level).get(1) : StringFormat.WHITE_BRIGHT);
        }
    };

    public abstract HashMap<LoggyLevel, List<StringFormat>> levelsFormat();

    public abstract String format(LoggyLevel level, String loggy, String message);

    public LoggyFormatter getFileFormatter()
    {
        final LoggyFormatter parent = this;

        return new LoggyFormatter()
        {
            @Override
            public HashMap<LoggyLevel, List<StringFormat>> levelsFormat()
            {
                return parent.levelsFormat();
            }

            @Override
            public String format(LoggyLevel level, String loggy, String message)
            {
                return StringFormat.clearFormat(parent.format(level, loggy, message));
            }
        };
    }
}
