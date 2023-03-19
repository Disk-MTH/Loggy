package fr.diskmth.loggy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class LoggyFile extends Loggable
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected final Path path;
    protected FileHandler fileHandler;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public LoggyFile(String path, LoggyFormatter formatter)
    {
        super(formatter);
        this.path = Paths.get(path);
    }

    public LoggyFile(String path)
    {
        this(path, null);
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

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

    public Path getPath()
    {
        return path;
    }

    public FileHandler getFileHandler()
    {
        return fileHandler;
    }

    public String getDir()
    {
        return path.getParent().toString();
    }

    public String getFileName()
    {
        return path.getFileName().toString();
    }
}
