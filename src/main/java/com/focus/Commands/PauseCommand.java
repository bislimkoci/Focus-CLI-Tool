package main.java.com.focus.Commands;

import main.java.com.focus.Timers.TimerInterface;

public class PauseCommand implements CommandInterface {
    private TimerInterface currentTimer;

    public PauseCommand(TimerInterface currentTimer) {
        this.currentTimer = currentTimer;
    }

    @Override
    public void execute() {
        if (currentTimer != null && currentTimer.isRunning() == true && currentTimer.isPaused() == false) {
            currentTimer.pause();
            System.out.println("timer is paused");
        } else {
            System.out.println("No timer is currently running or paused");
        }
    }


    
}
