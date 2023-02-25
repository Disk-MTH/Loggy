package fr.diskmth.loggy;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;

public class Logger
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    private boolean isMuted = false;

    protected final java.util.logging.Logger logger;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public Logger(String name)
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

    private String buildStackTrace(Throwable thrown)
    {
        final StringBuilder message = new StringBuilder();
        message.append("    \n").append(thrown).append("\n");
        for (StackTraceElement trace : thrown.getStackTrace())
        {
            message.append("\tat ").append(trace).append("\n");
        }
        message.delete(0, 4);
        message.delete(message.length() - 1, message.length());
        return message.toString();
    }

    public synchronized Logger log(String log)
    {
        if (!isMuted)
        {
            logger.info(log);
        }
        return this;
    }

    public synchronized Logger log(String log, LogFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            log(log);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger warn(String warn)
    {
        if (!isMuted)
        {
            logger.warning(warn);
        }
        return this;
    }

    public synchronized Logger warn(String warn, LogFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            warn(warn);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger warn(Throwable thrown)
    {
        if (!isMuted)
        {
            logger.warning(buildStackTrace(thrown));
        }
        return this;
    }

    public synchronized Logger warn(Throwable thrown, LogFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            warn(thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger warn(String warn, Throwable thrown)
    {
        if (!isMuted)
        {
            logger.warning(warn + buildStackTrace(thrown));
        }
        return this;
    }

    public synchronized Logger warn(String warn, Throwable thrown, LogFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            warn(warn, thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger error(String error)
    {
        if (!isMuted)
        {
            logger.severe(error);
        }
        return this;
    }

    public synchronized Logger error(String error, LogFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            error(error);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger error(Throwable thrown)
    {
        if (!isMuted)
        {
            logger.severe(buildStackTrace(thrown));
        }
        return this;
    }

    public synchronized Logger error(Throwable thrown, LogFile... files)
    {
        if (!isMuted)
        {
            addLogsFiles(files);
            error(thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger error(String error, Throwable thrown)
    {
        if (!isMuted)
        {
            logger.severe(error + (buildStackTrace(thrown)));
        }
        return this;
    }

    public synchronized Logger error(String error, Throwable thrown, LogFile... files)
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

    protected void addLogsFiles(LogFile... files)
    {
        for (LogFile file : files)
        {
            if (file != null)
            {
                logger.addHandler(file.getFileHandler());
            }
        }
    }

    protected void removeLogsFiles(LogFile... files)
    {
        for (LogFile file : files)
        {
            if (file != null)
            {
                logger.removeHandler(file.getFileHandler());
            }
        }
    }

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
