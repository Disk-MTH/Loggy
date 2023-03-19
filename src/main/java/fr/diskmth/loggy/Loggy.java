package fr.diskmth.loggy;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loggy extends Loggable
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected final Logger logger;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public Loggy(String name, LoggyFormatter formatter)
    {
        super(formatter);
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("The logger name can't be empty or null!");
        }

        logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(LoggyFormatter.toJavaFormatter(getFormatter()));
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
    }

    public Loggy(String name)
    {
        this(name, null);
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    protected void addLogsFiles(LoggyFile... files)
    {
        if (files == null)
        {
            return;
        }

        for (LoggyFile file : files)
        {
            if (file != null)
            {
                if (file.getFileHandler() == null)
                {
                    System.out.println(ANSI.format("Impossible to add the log file \"%name%\" because it is not initialized!".replace("%name%", file.getPath().toString()), ANSI.YELLOW));
                    return;
                }
                logger.addHandler(file.getFileHandler());
            }
        }
    }

    protected void removeLogsFiles(LoggyFile... files)
    {
        if (files == null)
        {
            return;
        }

        for (LoggyFile file : files)
        {
            if (file != null)
            {
                if (file.getFileHandler() == null)
                {
                    System.out.println(ANSI.format("Impossible to remove the log file \"%name%\" because it is not initialized!".replace("%name%", file.getPath().toString()), ANSI.YELLOW));
                    return;
                }
                logger.removeHandler(file.getFileHandler());
            }
        }
    }

    protected String buildStackTrace(Throwable thrown)
    {
        if (thrown == null)
        {
            return "";
        }

        final StringBuilder message = new StringBuilder();
        message.append("    \n").append(thrown).append("\n");
        Arrays.asList(thrown.getStackTrace()).forEach(trace -> message.append("\tat ").append(trace).append("\n"));
        return message.delete(0, 4).delete(message.length() - 1, message.length()).toString();
    }

    /*---------------------------------------- Log methods ----------------------------------------*/

    public Loggy log(String message, Throwable thrown, LoggyLevel level, LoggyFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            logger.log(level.toJavaLevel(), message + buildStackTrace(thrown));
            removeLogsFiles(files);
        }
        return this;
    }

    public Loggy log(String message, LoggyLevel level, LoggyFile... files)
    {
        return log(message, null, level, files);
    }

    public Loggy log(Throwable thrown, LoggyLevel level, LoggyFile... files)
    {
        return log(buildStackTrace(thrown), null, level, files);
    }

    /*---------------------------------------- Getters ----------------------------------------*/

    public String getName()
    {
        return logger.getName();
    }
}
