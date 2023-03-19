package fr.diskmth.loggy;

import java.util.logging.Level;

public enum LoggyLevel
{
    FATAL, ERROR, WARN, INFO, DEBUG;

    /*---------------------------------------- Static methods ----------------------------------------*/

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
