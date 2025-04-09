package main.java.com.focus.Commands;

import main.java.com.focus.Timers.TimerInterface;

public class ResumeCommand implements CommandInterface {
    private TimerInterface currentTimer;


    public ResumeCommand(TimerInterface currentTimer) {
        this.currentTimer = currentTimer;
    }


    @Override
    public void execute() {
        if (currentTimer != null && currentTimer.isRunning() == true && currentTimer.isPaused() == true) {
            currentTimer.resume();
            System.out.println("Timer running again");
        } else {
            System.out.println("No timer is paused");
        }
    }
    


}
