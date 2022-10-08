package fr.diskmth.loggy;

import java.util.logging.ConsoleHandler;

public class Logger
{
    private boolean verbose = true;

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
            LOGGER.addHandler(file.getFileHandler());
        }
    }

    protected void removeLogsFiles(LogsFile... files)
    {
        for (LogsFile file : files)
        {
            LOGGER.removeHandler(file.getFileHandler());
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

    public void log(String log)
    {
        LOGGER.info(log);
    }

    public void log(String log, LogsFile... files)
    {
        addLogsFiles(files);
        log(log);
        removeLogsFiles(files);
    }

    public void warn(String warn)
    {
        LOGGER.warning(warn);
    }

    public void warn(String warn, LogsFile... files)
    {
        addLogsFiles(files);
        warn(warn);
        removeLogsFiles(files);
    }

    public void warn(Throwable thrown)
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

    public void warn(Throwable thrown, LogsFile... files)
    {
        addLogsFiles(files);
        warn(thrown);
        removeLogsFiles(files);
    }

    public void warn(String warn, Throwable thrown)
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

    public void warn(String warn, Throwable thrown, LogsFile... files)
    {
        addLogsFiles(files);
        warn(warn, thrown);
        removeLogsFiles(files);
    }

    public void error(String error)
    {
        LOGGER.severe(error);
    }

    public void error(String error, LogsFile... files)
    {
        addLogsFiles(files);
        error(error);
        removeLogsFiles(files);
    }

    public void error(Throwable thrown)
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

    public void error(Throwable thrown, LogsFile... files)
    {
        addLogsFiles(files);
        error(thrown);
        removeLogsFiles(files);
    }

    public void error(String error, Throwable thrown)
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

    public void error(String error, Throwable thrown, LogsFile... files)
    {
        addLogsFiles(files);
        error(error, thrown);
        removeLogsFiles(files);
    }
}
