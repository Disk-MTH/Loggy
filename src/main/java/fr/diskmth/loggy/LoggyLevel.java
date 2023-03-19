package fr.diskmth.loggy;

import java.util.logging.Level;

/**
 * LoggyLevel represents the possible logging levels for a log statement.
 * The levels are: FATAL, ERROR, WARN, INFO, and DEBUG.
 *
 * @author Disk_MTH
 * @version 1.4
 * @since Loggy 1.4
 */
public enum LoggyLevel
{
    FATAL, ERROR, WARN, INFO, DEBUG;

    /*---------------------------------------- Static methods ----------------------------------------*/

    /**
     * Returns the corresponding LoggyLevel enum value based on the input Java logging level.
     *
     * @param level the Java logging level to be converted to a LoggyLevel enum value.
     * @return the LoggyLevel enum value that corresponds to the input Java logging level.
     */
    public static LoggyLevel fromJavaLevel(Level level)
    {
        switch (level.getName())
        {
            case "CONFIG":
                return FATAL;
            case "SEVERE":
                return ERROR;
            case "WARNING":
                return WARN;
            case "INFO":
                return INFO;
            default:
                return DEBUG;
        }
    }

    /**
     * Returns the corresponding Java logging level based on this LoggyLevel enum value.
     *
     * @return the Java logging level that corresponds to this LoggyLevel enum value.
     */
    public Level toJavaLevel()
    {
        switch (this)
        {
            case FATAL:
                return Level.CONFIG;
            case ERROR:
                return Level.SEVERE;
            case WARN:
                return Level.WARNING;
            case INFO:
                return Level.INFO;
            default:
                return Level.FINE;
        }
    }
}
