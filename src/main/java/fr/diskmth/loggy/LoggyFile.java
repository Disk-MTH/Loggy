package fr.diskmth.loggy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * LoggyFile is a class that extends the java.util.logging.Handler class and is used to handle logging messages to a file.
 *
 * @author Disk_MTH
 * @version 1.4
 * @since Loggy 1.0
 */
public class LoggyFile extends Loggable
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected final Path path;
    protected FileHandler fileHandler;

    /*---------------------------------------- Constructors ----------------------------------------*/

    /**
     * Constructs a new LoggyFile object with the specified path and formatter.
     *
     * @param path      the path to the log file
     * @param formatter the formatter to use for formatting log messages
     */
    public LoggyFile(String path, LoggyFormatter formatter)
    {
        super(formatter);
        this.path = Paths.get(path);
    }

    /**
     * Constructs a new LoggyFile object with the specified path and the default formatter.
     *
     * @param path the path to the log file
     */
    public LoggyFile(String path)
    {
        this(path, null);
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    /**
     * Initializes the log file by creating the directory if it doesn't exist,
     * setting up the file handler, and clearing any existing logs in the directory.
     *
     * @return this LoggyFile object
     */
    public LoggyFile init()
    {
        if (fileHandler == null)
        {
            try
            {
                new File(getDir()).mkdirs();
                fileHandler = new FileHandler(path.toString(), true);
                fileHandler.setFormatter(LoggyFormatter.toJavaFormatter(formatter.getFileFormatter()));
                fileHandler.setLevel(Level.ALL);
                clearLogDir();
            }
            catch (IOException exception)
            {
                System.out.println(ANSI.format("Failed to initialize the log file \"%name%\"".replace("%name%", getPath().toString()), ANSI.RED));
                exception.printStackTrace();
            }
        }

        return this;
    }

    /**
     * Closes the file handler and clears any existing logs in the directory.
     *
     * @return this LoggyFile object
     */
    public LoggyFile close()
    {
        if (fileHandler != null)
        {
            fileHandler.close();
            fileHandler = null;
            clearLogDir();
        }
        return this;
    }

    /**
     * Clears any empty log files in the log directory.
     */
    public void clearLogDir()
    {
        final File logDir = new File(getDir());
        for (File emptyFile : Objects.requireNonNull(logDir.listFiles()))
        {
            if (emptyFile.length() == 0)
            {
                emptyFile.delete();
            }
        }
        logDir.delete();
    }

    /*---------------------------------------- Getters ----------------------------------------*/

    /**
     * Returns the path to the log file.
     *
     * @return the path to the log file
     */
    public Path getPath()
    {
        return path;
    }

    /**
     * Returns the file handler for this LoggyFile object.
     *
     * @return the file handler for this LoggyFile object
     */
    public FileHandler getFileHandler()
    {
        return fileHandler;
    }

    /**
     * Returns the directory containing the log file.
     *
     * @return the directory containing the log file
     */
    public String getDir()
    {
        return path.getParent().toString();
    }

    /**
     * Returns the filename of the log file.
     *
     * @return the filename of the log file
     */
    public String getFileName()
    {
        return path.getFileName().toString();
    }
}
