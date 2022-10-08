package fr.diskmth.loggy;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;

public class LogsFile
{
    public static final String DATE = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
    public static final Path JAR_PATH;

    static
    {
        try
        {
            JAR_PATH = Path.of(Logger.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        }
        catch (URISyntaxException exception)
        {
            throw new RuntimeException(exception);
        }
    }

    protected final Logger logger;
    protected final Path path;
    protected final String fileName;
    protected FileHandler fileHandler;

    public LogsFile(Logger logger, Path path, String fileName)
    {
        this.logger = logger;
        this.path = path;
        this.fileName = fileName;
    }

    public LogsFile(Logger logger)
    {
        this(logger, Path.of(JAR_PATH + "/logs"), DATE + ".log");
    }

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

    public void exit()
    {
        if (fileHandler != null)
        {
            fileHandler.close();
            final File logsFile = new File(path.toUri().getPath() + File.separatorChar + fileName);
            if (logsFile.length() == 0 && logsFile.delete() && logger.verbose())
            {
                logger.log("Empty logs file " + fileName + " has been deleted");
            }
            final File logsDir = new File(path.toUri());
            if (logsDir.length() == 0 && logsDir.delete() && logger.verbose())
            {
                logger.log("Empty logs directory " + path + " has been deleted");
            }
        }
    }

    public FileHandler getFileHandler()
    {
        return fileHandler;
    }
}
