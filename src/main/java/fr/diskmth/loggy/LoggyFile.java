package fr.diskmth.loggy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggyFile extends Muted
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected final Path path;

    protected LoggyFormatter formatter;
    protected FileHandler fileHandler;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public LoggyFile(String path, LoggyFormatter formatter)
    {
        this.path = Path.of(path);
        this.formatter = Objects.requireNonNullElse(formatter, LoggyFormatter.DEFAULT);
    }

    public LoggyFile(String path)
    {
        this(path, null);
    }

    /*---------------------------------------- Misc methods ----------------------------------------*/

    public LoggyFile init()
    {
        if (fileHandler != null)
        {
            try
            {
                new File(getDir()).mkdirs();
                fileHandler = new FileHandler(path.toString(), true);
                fileHandler.setFormatter(new Formatter()
                {
                    @Override
                    public String format(LogRecord record)
                    {
                        return formatter.format(LoggyLevel.fromJavaLevel(record.getLevel()), record.getMessage(), record.getMessage());
                    }
                });
            }
            catch (IOException exception)
            {
                System.out.println(StringFormat.format("Failed to initialize the log file \"%name%\"".replace("%name%", getPath().toString()), StringFormat.RED));
                exception.printStackTrace();
            }

            clear();
        }

        return this;
    }

    public LoggyFile close()
    {
        if (fileHandler != null)
        {
            fileHandler.close();
            fileHandler = null;
            clear();
        }
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

    public LoggyFormatter getFormatter()
    {
        return formatter;
    }

    public String getDir()
    {
        return path.getParent().toString();
    }

    public String getFileName()
    {
        return path.getFileName().toString();
    }

    /*---------------------------------------- Setters ----------------------------------------*/

    public LoggyFile setFormatter(LoggyFormatter formatter)
    {
        this.formatter = Objects.requireNonNullElse(formatter, LoggyFormatter.DEFAULT);
        return this;
    }
}
