package fr.diskmth.loggy;

import java.util.Objects;

public abstract class Loggable
{
    protected boolean isMuted = false;

    protected LoggyFormatter formatter = LoggyFormatter.DEFAULT;

    public Loggable(LoggyFormatter formatter)
    {
        setFormatter(formatter);
    }

    public boolean isMuted()
    {
        return isMuted;
    }

    public LoggyFormatter getFormatter()
    {
        return formatter;
    }

    public void toggleMute()
    {
        isMuted = !isMuted;
    }

    public void setFormatter(LoggyFormatter formatter)
    {
        this.formatter = Objects.requireNonNullElse(formatter, LoggyFormatter.DEFAULT);
    }
}
