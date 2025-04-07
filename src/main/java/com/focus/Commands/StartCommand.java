package main.java.com.focus.Commands;

import main.java.com.focus.Focus;
import main.java.com.focus.Timers.SessionTimer;
import main.java.com.focus.Timers.TimerCallback;
import main.java.com.focus.Timers.TimerInterface;

public class StartCommand implements CommandInterface {
    protected TimerInterface timer;
    protected int workTime;
    protected int restTime;
    
    public StartCommand(int workTime, int restTime) {
        this.workTime = workTime;
        this.restTime = restTime;
        this.timer = new SessionTimer(workTime, restTime, new TimerCallback() {
            @Override
            public void onTimerComplete() {
                Focus.setCurrentTimer(null);
            }
        });
    }

    public void execute() {
        timer.start();
        Focus.setCurrentTimer(timer);
    }
    
    public TimerInterface getTimer() {
        return timer;
    }

}
