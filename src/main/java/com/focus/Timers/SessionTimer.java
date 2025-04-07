package main.java.com.focus.Timers;

public class SessionTimer extends BaseTimer{


    public SessionTimer(int workTime, int restTime, TimerCallback callback) {
        super(workTime, restTime, callback);
    }

    @Override
    protected void doWorkRestPeriod() {
        timerThread = new Thread(() -> {
            try {
                System.out.println("Work period started");
                Thread.sleep(workTime);

                if (!isRunning) {stop();}
    
                System.out.println("Rest period started");
                Thread.sleep(restTime);

                if (isRunning && callback != null) {
                    callback.onTimerComplete();
                }

            } catch (InterruptedException e) {
                System.out.println("Timer thread interupted");
                Thread.currentThread().interrupt();
            } finally {
                isRunning = false;
                System.out.println("Timer stopped");
            }
            
        });
    }
}
