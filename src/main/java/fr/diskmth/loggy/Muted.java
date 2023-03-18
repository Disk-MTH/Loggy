package fr.diskmth.loggy;

public abstract class Muted
{
    protected boolean isMuted = false;

    public boolean isMuted()
    {
        return isMuted;
    }

    public void toggleMute()
    {
        isMuted = !isMuted;
    }
}
