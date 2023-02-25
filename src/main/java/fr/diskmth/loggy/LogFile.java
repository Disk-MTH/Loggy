package fr.diskmth.loggy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;

public class LogFile
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected final Logger logger;
    protected final Path path;
    protected final String fileName;
    protected FileHandler fileHandler;

    protected boolean isInit = false;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public LogFile(Logger logger, Path path, String fileName)
    {
        this.logger = logger;
        this.path = path;
        this.fileName = fileName;
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    public void init()
    {
        if (isInit)
        {
            return;
        }

        try
        {
            final File logDir = new File(path.toUri());
            if (logDir.mkdirs())
            {
                logger.log("Empty logs directory \"" + logDir.getPath() + "\" has been created");
            }

            final boolean fileExist = Files.exists(path.resolve(fileName));

            fileHandler = new FileHandler(path.toString() + File.separatorChar + fileName, true);
            fileHandler.setFormatter(LogsFormatter.FILE_FORMATTER);

            if (!fileExist)
            {
                logger.log("Empty log file \"" + logDir.getName() + "\" has been created");
            }

            isInit = true;
        }
        catch (IOException exception)
        {
            logger.warn("Unable to create the log file handler: " + path + File.separatorChar + fileName, exception);
        }
    }

    public void close()
    {
        if (!isInit || fileHandler == null)
        {
            return;
        }

        fileHandler.close();

        final File logFile = new File(path.toString() + File.separatorChar + fileName);
        if (logFile.length() == 0)
        {
            if (logFile.delete())
            {
                logger.log("Empty log file \"" + logFile.getName() + "\" has been deleted");
            }
            else
            {
                logger.error("Failed to delete empty log file \"" + logFile.getName() + "\"");
            }
        }

        final File logDir = new File(path.toUri());
        if (logDir.list().length == 0)
        {
            if (logDir.delete())
            {
                logger.log("Empty logs directory \"" + logDir.getPath() + "\" has been deleted");
            }
            else
            {
                logger.error("Failed to delete empty logs directory \"" + logDir.getPath() + "\"");
            }
        }

        isInit = false;
    }

    /*---------------------------------------- Getters ----------------------------------------*/

    public Logger getLogger()
    {
        return logger;
    }

    public Path getPath()
    {
        return path;
    }

    public String getFileName()
    {
        return fileName;
    }

    public FileHandler getFileHandler()
    {
        return fileHandler;
    }

    public boolean isInit()
    {
        return isInit;
    }
}
