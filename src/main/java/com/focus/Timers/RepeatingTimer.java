package main.java.com.focus.Timers;

public class RepeatingTimer extends BaseTimer{
    private int repeats;

    public RepeatingTimer(int workTime, int restTime, int repeats, TimerCallback callback) {
        super(workTime, restTime, callback);
        this.repeats = repeats;
        
    }
    
    @Override
    protected void doWorkRestPeriod() {
        timerThread = new Thread( () -> {
            try {
                while (isRunning && repeats > 0) {
                    //Working period
                    System.out.printf("Work period started (%d remaining)%n", repeats);
                    sleepWithPause(workTime);

                    //Check if there has been a stop
                    if (!isRunning) {stop();}

                    System.out.println("Rest period started");
                    sleepWithPause(restTime);
                    
                    if (!isRunning) {stop();}

                    repeats--;
                }

                if (isRunning && callback != null) {
                    callback.onTimerComplete();
                }

            } catch (InterruptedException e) {
                System.out.println("Timer thread interrupted");
                Thread.currentThread().interrupt();
            } finally {
                isRunning = false;
                System.out.println("Timer stopped");
            }
        });
    }
    
}
