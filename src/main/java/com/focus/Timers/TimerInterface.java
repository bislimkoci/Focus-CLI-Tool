package main.java.com.focus.Timers;

public interface TimerInterface {
    public void start();

    public void stop();

    public void pause();

    public void resume();

    public boolean isRunning();

    public boolean isPaused();
}
