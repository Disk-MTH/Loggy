package fr.diskmth.loggy;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class extends Loggable and provides logging functionality through a logger object from the java.util.logging package.
 *
 * @author Disk_MTH
 * @version 1.4
 * @since Loggy 1.0
 */
public class Loggy extends Loggable
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected final Logger logger;

    /*---------------------------------------- Constructors ----------------------------------------*/

    /**
     * Constructs a Loggy object with a specified name and formatter.
     *
     * @param name      the name of the logger
     * @param formatter the formatter used to format log messages
     * @throws IllegalArgumentException if the logger name is null or empty
     */
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

    /**
     * Constructs a Loggy object with a specified name and a default formatter.
     *
     * @param name the name of the logger
     */
    public Loggy(String name)
    {
        this(name, null);
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    /**
     * Adds specified log files to the logger's handlers.
     *
     * @param files the log files to add
     */
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

    /**
     * Removes specified log files from the logger's handlers.
     *
     * @param files the log files to remove
     */
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

    /**
     * Builds a stack trace from a Throwable object.
     *
     * @param thrown the Throwable object
     * @return the stack trace
     */
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

    /**
     * Logs a message with a specified level and log files.
     *
     * @param message the message to log
     * @param thrown  the Throwable object to log
     * @param level   the level of the message
     * @param files   the log files to log the message in
     * @return the Loggy object
     */
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

    /**
     * Logs a message with a specified level and log files.
     *
     * @param message the message to log
     * @param level   the level of the message
     * @param files   the log files to log the message in
     * @return the Loggy object
     */
    public Loggy log(String message, LoggyLevel level, LoggyFile... files)
    {
        return log(message, null, level, files);
    }

    /**
     * Logs a Throwable object with a specified level and log files.
     *
     * @param thrown the Throwable object to log
     * @param level  the level of the message
     * @param files  the log files to log the message in
     * @return the Loggy object
     */
    public Loggy log(Throwable thrown, LoggyLevel level, LoggyFile... files)
    {
        return log(buildStackTrace(thrown), null, level, files);
    }

    /*---------------------------------------- Getters ----------------------------------------*/

    /**
     * Returns the logger's name.
     *
     * @return the logger's name
     */
    public String getName()
    {
        return logger.getName();
    }
}
