package fr.diskmth.loggy;

/**
 * The Loggable class is an abstract class that provides basic logging functionality.
 * It provides methods to get and set a formatter and mute the log output.
 *
 * @author Disk_MTH
 * @version 1.4
 * @since Loggy 1.4
 */
public abstract class Loggable
{
    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected boolean isMuted = false;

    protected LoggyFormatter formatter = LoggyFormatter.DEFAULT;

    /*---------------------------------------- Constructors ----------------------------------------*/

    /**
     * Creates a new Loggable object with the given formatter.
     *
     * @param formatter The formatter to use for formatting log messages.
     */
    public Loggable(LoggyFormatter formatter)
    {
        setFormatter(formatter);
    }

    /*---------------------------------------- Getters ----------------------------------------*/

    /**
     * Gets whether or not the log output is muted.
     *
     * @return True if the log output is muted, false otherwise.
     */
    public boolean isMuted()
    {
        return isMuted;
    }

    /**
     * Gets the formatter used to format log messages.
     *
     * @return The formatter used to format log messages.
     */
    public LoggyFormatter getFormatter()
    {
        return formatter;
    }

    /*---------------------------------------- Setters ----------------------------------------*/

    /**
     * Toggles whether or not the log output is muted.
     */
    public void toggleMute()
    {
        isMuted = !isMuted;
    }

    /**
     * Sets the formatter used to format log messages.
     *
     * @param formatter The formatter to use for formatting log messages.
     */
    public void setFormatter(LoggyFormatter formatter)
    {
        this.formatter = formatter != null ? formatter : LoggyFormatter.DEFAULT;
    }
}
