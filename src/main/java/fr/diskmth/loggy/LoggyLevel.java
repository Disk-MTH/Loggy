package fr.diskmth.loggy;

import java.util.logging.Level;

public enum LoggyLevel
{
    FATAL, ERROR, WARN, INFO, DEBUG;

    public static LoggyLevel fromJavaLevel(Level level)
    {
        return switch (level.getName())
                {
                    case "CONFIG" -> FATAL;
                    case "SEVERE" -> ERROR;
                    case "WARNING" -> WARN;
                    case "INFO" -> INFO;
                    default -> DEBUG;
                };
    }

    public Level toJavaLevel()
    {
        return switch (this)
                {
                    case FATAL -> Level.CONFIG;
                    case ERROR -> Level.SEVERE;
                    case WARN -> Level.WARNING;
                    case INFO -> Level.INFO;
                    default -> Level.FINE;
                };
    }
}
