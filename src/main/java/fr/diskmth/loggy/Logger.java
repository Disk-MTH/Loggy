package fr.diskmth.loggy;

import java.util.logging.ConsoleHandler;

public class Logger
{
    private boolean verbose = true;
    private boolean mute = false;

    protected final java.util.logging.Logger LOGGER;

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

    public final boolean verbose()
    {
        return verbose;
    }

    public final void verbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    public final void mute(boolean mute)
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

    public void log(String log)
    {
        if (mute)
        {
            return;
        }
        LOGGER.info(log);
    }

    public void log(String log, LogsFile... files)
    {
        if (mute)
        {
            return;
        }
        addLogsFiles(files);
        log(log);
        removeLogsFiles(files);
    }

    public void warn(String warn)
    {
        if (mute)
        {
            return;
        }
        LOGGER.warning(warn);
    }

    public void warn(String warn, LogsFile... files)
    {
        if (mute)
        {
            return;
        }
        addLogsFiles(files);
        warn(warn);
        removeLogsFiles(files);
    }

    public void warn(Throwable thrown)
    {
        if (mute)
        {
            return;
        }
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

    public void warn(Throwable thrown, LogsFile... files)
    {
        if (mute)
        {
            return;
        }
        addLogsFiles(files);
        warn(thrown);
        removeLogsFiles(files);
    }

    public void warn(String warn, Throwable thrown)
    {
        if (mute)
        {
            return;
        }
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

    public void warn(String warn, Throwable thrown, LogsFile... files)
    {
        if (mute)
        {
            return;
        }
        addLogsFiles(files);
        warn(warn, thrown);
        removeLogsFiles(files);
    }

    public void error(String error)
    {
        if (mute)
        {
            return;
        }
        LOGGER.severe(error);
    }

    public void error(String error, LogsFile... files)
    {
        if (mute)
        {
            return;
        }
        addLogsFiles(files);
        error(error);
        removeLogsFiles(files);
    }

    public void error(Throwable thrown)
    {
        if (mute)
        {
            return;
        }
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

    public void error(Throwable thrown, LogsFile... files)
    {
        if (mute)
        {
            return;
        }
        addLogsFiles(files);
        error(thrown);
        removeLogsFiles(files);
    }

    public void error(String error, Throwable thrown)
    {
        if (mute)
        {
            return;
        }
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

    public void error(String error, Throwable thrown, LogsFile... files)
    {
        if (mute)
        {
            return;
        }
        addLogsFiles(files);
        error(error, thrown);
        removeLogsFiles(files);
    }
}
