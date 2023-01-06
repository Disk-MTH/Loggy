package fr.diskmth.loggy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;

public class LogsFile
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    public static final String DATE = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());

    protected final Logger logger;
    protected final Path path;
    protected final String fileName;
    protected FileHandler fileHandler;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public LogsFile(Logger logger, Path path, String fileName)
    {
        this.logger = logger;
        this.path = path;
        this.fileName = fileName;
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    public void init()
    {
        try
        {
            final File logDir = new File(path.toUri());
            if (logDir.mkdirs() && logger.verbose())
            {
                logger.log("Empty logs directory " + logDir.getPath() + " has been created");
            }
            fileHandler = new FileHandler(path.toString() + File.separatorChar + fileName);
            fileHandler.setFormatter(LogsFormatter.FILE_FORMATTER);
        }
        catch (IOException exception)
        {
            if (logger.verbose())
            {
                logger.error("Unable to create the logs file handler: " + path + File.separatorChar + fileName, exception);
            }
        }

    }

    public void close()
    {
        if (fileHandler == null)
        {
            return;
        }

        fileHandler.close();
        boolean deleted = false;
        final File logsFile = new File(path.toUri().getPath() + File.separatorChar + fileName);

        while (logsFile.exists() && logsFile.length() == 0)
        {
            deleted = logsFile.delete();
        }

        if (deleted && logger.verbose())
        {
            logger.log("Empty logs file " + logsFile.getPath() + " has been deleted");
        }

        deleted = false;
        final File logsDir = new File(path.toUri());

        while (logsDir.exists() && logsDir.length() == 0)
        {
            deleted = logsDir.delete();
        }

        if (deleted && logger.verbose())
        {
            logger.log("Empty logs directory " + logsDir.getPath() + " has been deleted");
        }
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
}
