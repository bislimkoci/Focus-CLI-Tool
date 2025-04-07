package main.java.com.focus.Timers;

public abstract class BaseTimer implements TimerInterface{
    protected final int workTime;
    protected final int restTime;
    protected final TimerCallback callback;
    protected final Object lock = new Object();
    protected volatile Boolean isRunning;
    protected Thread timerThread;
    

    public BaseTimer(int workTime, int restTime, TimerCallback callback) {
        this.workTime = workTime * 60 * 1000;
        this.restTime = restTime * 60 * 1000;
        this.callback = callback;
        this.isRunning = false;
        this.timerThread = new Thread();
    }


    @Override
    public void start(){
        synchronized (lock) {
            if (isRunning) {
                System.out.println("This timer is already running");
                return;
            }
            isRunning = true;
        }

        doWorkRestPeriod();

        timerThread.setDaemon(true);
        timerThread.start();


    }

    abstract protected void doWorkRestPeriod();

    @Override
    public void stop() {
        synchronized (lock) {
            if (!isRunning) {
                System.out.println("No timer is currently running");
                return;
            }
            isRunning = false;
            if (timerThread != null) {
                timerThread.interrupt();
            }
        }
    }


    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
