package main.java.com.focus.Timers;

public abstract class BaseTimer implements TimerInterface{
    private int workTime;
    private int restTime;
    private Boolean isRunning;
    private Thread workThread;
    private Thread restThread;
    private TimerCallback callback;

    public BaseTimer(int workTime, int restTime, TimerCallback callback) {
        this.workTime = workTime * 60 * 1000;
        this.restTime = restTime * 60 * 1000;
        this.callback = callback;
        this.isRunning = false;
    }



    @Override
    public void stop() {
        if (!isRunning) {
            System.out.println("No timer is currently running");
            return;
        }

        isRunning = false;
        interruptThread(workThread);
        interruptThread(restThread);
        System.out.println("Timer stopped");
    }

    protected void interruptThread(Thread thread) {
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
