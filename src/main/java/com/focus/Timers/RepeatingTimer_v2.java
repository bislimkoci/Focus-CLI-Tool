package main.java.com.focus.Timers;

public class RepeatingTimer_v2 implements TimerInterface{
    private final int workTime;
    private final int restTime;
    private final TimerCallback callback;
    private final Object lock = new Object();
    private volatile boolean isRunning;
    private Thread timerThread;
    private int repeats;



    public RepeatingTimer_v2(int workTime, int restTime, int repeats, TimerCallback callback) {
        this.workTime = workTime * 60 * 1000;
        this.restTime = restTime * 60 * 1000;
        this.repeats = repeats;
        this.callback = callback;
        this.isRunning = false; // May be deleting this later
    }
    
    
    @Override
    public void start() {
        synchronized (lock) {
            if (isRunning) {
                System.out.println("This timer is already running");
                return;
            }
            isRunning = true;
        }

        timerThread = new Thread( () -> {
            try {
                while (isRunning && repeats > 0) {
                    //Working period
                    System.out.printf("Work period started (%d remaining)%n", repeats);
                    Thread.sleep(workTime);

                    //Check if there has been a stop
                    if (!isRunning) break;

                    System.out.println("Rest period started");
                    Thread.sleep(restTime);
                    
                    if (!isRunning) break;

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

        timerThread.setDaemon(true);
        timerThread.start();
    }

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
    public boolean isRunning() {return isRunning;}
    
}
