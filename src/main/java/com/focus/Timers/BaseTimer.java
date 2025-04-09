package main.java.com.focus.Timers;

public abstract class BaseTimer implements TimerInterface{
    protected final int workTime;
    protected final int restTime;
    protected final TimerCallback callback;
    protected final Object lock = new Object();
    protected volatile boolean isRunning;
    protected Thread timerThread;
    protected volatile boolean isPaused;
    

    public BaseTimer(int workTime, int restTime, TimerCallback callback) {
        this.workTime = workTime * 60 * 1000;
        this.restTime = restTime * 60 * 1000;
        this.callback = callback;
        this.isRunning = false;
        this.isPaused = false;
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

    protected void sleepWithPause(int duration) throws InterruptedException {
        int interval = 100;
        int elapsed = 0;

        while (elapsed < duration) {
            synchronized (lock) {
                while (isPaused) {
                    lock.wait();
                }
            }
            Thread.sleep(interval);
            elapsed += interval;
        }
    };


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
    public void pause() {
        synchronized (lock) {
            isPaused = true;
        }
    }

    @Override
    public void resume() {
        synchronized (lock) {
            isPaused = false;
            lock.notifyAll();
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }

}
