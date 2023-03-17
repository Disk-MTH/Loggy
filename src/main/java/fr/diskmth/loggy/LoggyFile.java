package fr.diskmth.loggy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.FileHandler;

public class LoggyFile
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected final Path path;
    protected FileHandler fileHandler;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public LoggyFile(Path path)
    {
        this.path = path;
    }

    public LoggyFile(String path)
    {
        this(Path.of(path));
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    public LoggyFile init()
    {
        if (fileHandler != null)
        {
            System.out.println(StringFormat.format("The \"%name%\" log file cannot be initialized because it is already!".replace("%name%", getPath().toString()), StringFormat.YELLOW));
            return this;
        }

        try
        {
            new File(getDir()).mkdirs();
            fileHandler = new FileHandler(path.toString(), true);
            fileHandler.setFormatter(LogsFormatter.FILE_FORMATTER);
        }
        catch (IOException exception)
        {
            System.out.println(StringFormat.format("Failed to initialize the log file \"%name%\"".replace("%name%", getPath().toString()), StringFormat.RED));
            exception.printStackTrace();
        }

        clear();
        return this;
    }

    public LoggyFile close()
    {
        if (fileHandler == null)
        {
            System.out.println(StringFormat.format("The \"%name%\" log file cannot be closed because it is already!".replace("%name%", getPath().toString()), StringFormat.YELLOW));
            return this;
        }

        fileHandler.close();
        clear();
        return this;
    }

    public void clear()
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
