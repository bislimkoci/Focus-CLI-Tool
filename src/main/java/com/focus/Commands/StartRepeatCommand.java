package main.java.com.focus.Commands;

import main.java.com.focus.Focus;
import main.java.com.focus.Timers.RepeatingTimer_v2;
import main.java.com.focus.Timers.TimerCallback;

public class StartRepeatCommand extends StartCommand{

    private int repeats;
    
    public StartRepeatCommand(int workTime, int restTime, int repeats) {
        super(workTime, restTime);
        this.repeats = repeats;
        this.timer = new RepeatingTimer_v2(this.workTime, this.restTime, this.repeats,new TimerCallback() {
            @Override
            public void onTimerComplete() {
                Focus.setCurrentTimer(null);
            }
        });
    }

}
