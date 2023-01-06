package fr.diskmth.loggy;

import java.util.logging.ConsoleHandler;

public class Logger
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    private boolean verbose = true;
    private boolean mute = false;

    protected final java.util.logging.Logger LOGGER;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public Logger(String name)
    {
        if (name.trim().isEmpty())
        {
            throw new IllegalArgumentException("The logger name can't be empty!");
        }

        LOGGER = java.util.logging.Logger.getLogger(name.trim());
        LOGGER.setUseParentHandlers(false);

        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(LogsFormatter.CONSOLE_FORMATTER);
        LOGGER.addHandler(consoleHandler);
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    public synchronized Logger log(String log)
    {
        if (!mute)
        {
            LOGGER.info(log);
        }
        return this;
    }

    public synchronized Logger log(String log, LogsFile... files)
    {
        if (!mute)
        {
            addLogsFiles(files);
            log(log);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger warn(String warn)
    {
        if (!mute)
        {
            LOGGER.warning(warn);
        }
        return this;
    }

    public synchronized Logger warn(String warn, LogsFile... files)
    {
        if (!mute)
        {
            addLogsFiles(files);
            warn(warn);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger warn(Throwable thrown)
    {
        if (!mute)
        {
            LOGGER.warning(() ->
            {
                final StringBuilder message = new StringBuilder();
                for (StackTraceElement trace : thrown.getStackTrace())
                {
                    message.append("    ").append(trace).append("\n");
                }
                message.delete(0, 4);
                message.delete(message.length() - 1, message.length());
                return message.toString();
            });
        }
        return this;
    }

    public synchronized Logger warn(Throwable thrown, LogsFile... files)
    {
        if (!mute)
        {
            addLogsFiles(files);
            warn(thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger warn(String warn, Throwable thrown)
    {
        if (!mute)
        {
            LOGGER.warning(() ->
            {
                final StringBuilder message = new StringBuilder();
                message.append(warn).append("\n");
                for (StackTraceElement trace : thrown.getStackTrace())
                {
                    message.append("    ").append(trace).append("\n");
                }
                message.delete(message.length() - 1, message.length());
                return message.toString();
            });
        }
        return this;
    }

    public synchronized Logger warn(String warn, Throwable thrown, LogsFile... files)
    {
        if (!mute)
        {
            addLogsFiles(files);
            warn(warn, thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger error(String error)
    {
        if (!mute)
        {
            LOGGER.severe(error);
        }
        return this;
    }

    public synchronized Logger error(String error, LogsFile... files)
    {
        if (!mute)
        {
            addLogsFiles(files);
            error(error);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger error(Throwable thrown)
    {
        if (!mute)
        {
            LOGGER.severe(() ->
            {
                final StringBuilder message = new StringBuilder();
                for (StackTraceElement trace : thrown.getStackTrace())
                {
                    message.append("    ").append(trace).append("\n");
                }
                message.delete(0, 4);
                message.delete(message.length() - 1, message.length());
                return message.toString();
            });
        }
        return this;
    }

    public synchronized Logger error(Throwable thrown, LogsFile... files)
    {
        if (!mute)
        {
            addLogsFiles(files);
            error(thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    public synchronized Logger error(String error, Throwable thrown)
    {
        if (!mute)
        {
            LOGGER.severe(() ->
            {
                final StringBuilder message = new StringBuilder();
                message.append(error).append("\n");
                for (StackTraceElement trace : thrown.getStackTrace())
                {
                    message.append("    ").append(trace).append("\n");
                }
                message.delete(message.length() - 1, message.length());
                return message.toString();
            });
        }
        return this;
    }

    public synchronized Logger error(String error, Throwable thrown, LogsFile... files)
    {
        if (!mute)
        {
            addLogsFiles(files);
            error(error, thrown);
            removeLogsFiles(files);
        }
        return this;
    }

    /*---------------------------------------- Setters ----------------------------------------*/

    public boolean verbose()
    {
        return verbose;
    }

    /*---------------------------------------- Setters ----------------------------------------*/

    protected void addLogsFiles(LogsFile... files)
    {
        for (LogsFile file : files)
        {
            if (file != null)
            {
                LOGGER.addHandler(file.getFileHandler());
            }
        }
    }

    protected void removeLogsFiles(LogsFile... files)
    {
        for (LogsFile file : files)
        {
            if (file != null)
            {
                LOGGER.removeHandler(file.getFileHandler());
            }
        }
    }

    public void verbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    public void mute(boolean mute)
    {
        if (mute && this.mute)
        {
            if (verbose)
            {
                log("Logger is already muted");
            }
        }
        else if (!mute && !this.mute)
        {
            if (verbose)
            {
                log("Logger is already activated");
            }
        }
        else
        {
            this.mute = mute;
            if (mute)
            {
                if (verbose)
                {
                    log("Logger muted");
                }
            }
            else
            {
                if (verbose)
                {
                    log("Logger activated");
                }
            }
        }
    }
}
