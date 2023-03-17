package fr.diskmth.loggy;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;

public class Loggy
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected boolean isMuted = false;

    protected final java.util.logging.Logger logger;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public Loggy(String name)
    {
        if (name.trim().isEmpty())
        {
            throw new IllegalArgumentException("The logger name can't be empty!");
        }

        logger = java.util.logging.Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(LogsFormatter.CONSOLE_FORMATTER);
        logger.addHandler(consoleHandler);
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    protected synchronized void addLogsFiles(LoggyFile... files)
    {
        for (LoggyFile file : files)
        {
            if (file != null)
            {
                logger.addHandler(file.getFileHandler());
            }
        }
    }

    protected synchronized void removeLogsFiles(LoggyFile... files)
    {
        for (LoggyFile file : files)
        {
            if (file != null)
            {
                logger.removeHandler(file.getFileHandler());
            }
        }
    }

    protected String buildStackTrace(Throwable thrown)
    {
        final StringBuilder message = new StringBuilder();
        message.append("    \n").append(thrown).append("\n");
        Arrays.asList(thrown.getStackTrace()).forEach(trace -> message.append("\tat ").append(trace).append("\n"));
        return message.delete(0, 4).delete(message.length() - 1, message.length()).toString();
    }

    /*---------------------------------------- Log methods ----------------------------------------*/

    public synchronized Loggy log(String log)
    {
        if (!isMuted)
        {
            logger.info(log);
        }
        return this;
    }

    public synchronized Loggy log(String log, LoggyFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            log(log);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Loggy warn(String warn)
    {
        if (!isMuted)
        {
            logger.warning(warn);
        }
        return this;
    }

    public synchronized Loggy warn(String warn, LoggyFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            warn(warn);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Loggy warn(Throwable thrown)
    {
        if (!isMuted)
        {
            logger.warning(buildStackTrace(thrown));
        }
        return this;
    }

    public synchronized Loggy warn(Throwable thrown, LoggyFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            warn(thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Loggy warn(String warn, Throwable thrown)
    {
        if (!isMuted)
        {
            logger.warning(warn + buildStackTrace(thrown));
        }
        return this;
    }

    public synchronized Loggy warn(String warn, Throwable thrown, LoggyFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            warn(warn, thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Loggy error(String error)
    {
        if (!isMuted)
        {
            logger.severe(error);
        }
        return this;
    }

    public synchronized Loggy error(String error, LoggyFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            error(error);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Loggy error(Throwable thrown)
    {
        if (!isMuted)
        {
            logger.severe(buildStackTrace(thrown));
        }
        return this;
    }

    public synchronized Loggy error(Throwable thrown, LoggyFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            error(thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Loggy error(String error, Throwable thrown)
    {
        if (!isMuted)
        {
            logger.severe(error + (buildStackTrace(thrown)));
        }
        return this;
    }

    public synchronized Loggy error(String error, Throwable thrown, LoggyFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            error(error, thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    /*---------------------------------------- Getters ----------------------------------------*/

    public boolean isMuted()
    {
        return isMuted;
    }

    /*---------------------------------------- Setters ----------------------------------------*/

    public void mute(boolean mute)
    {
        final String status = mute ? "muted" : "activated";

        if (isMuted == mute)
        {
            warn("Logger \"" + logger.getName() + "\" is already " + status);
            return;
        }

        log("Logger \"" + logger.getName() + "\" is now " + status);
        isMuted = mute;
    }
}
