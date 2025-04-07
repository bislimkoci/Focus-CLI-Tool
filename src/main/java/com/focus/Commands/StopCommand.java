package main.java.com.focus.Commands;

import main.java.com.focus.Focus;
import main.java.com.focus.Timers.TimerInterface;

public class StopCommand implements CommandInterface {
    private TimerInterface timer;
    
    public StopCommand(TimerInterface currentTimer) {
        this.timer = currentTimer;
    }

    public void execute() {
        if (timer != null) {
            timer.stop();
            Focus.setCurrentTimer(null);
        } else {
            System.out.println("No timer is currently running");
        }
    }
}