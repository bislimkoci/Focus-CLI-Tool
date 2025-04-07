package main.java.com.focus.Timers;

public class RepeatingTimer implements TimerInterface {
    private int workTime;
    private int restTime;
    private int repeats;
    private Boolean isRunning;
    private Thread workThread;
    private Thread restThread;
    private TimerCallback callback;

    public RepeatingTimer(int workTime, int restTime, int repeats, TimerCallback callback) {
        this.workTime = 3000;//workTime * 60 * 1000;
        this.restTime = 3000;//restTime * 60 * 1000;
        this.repeats = repeats;
        this.callback = callback;
        this.isRunning = false;
    }


    @Override
    public void start() {
        if (isRunning) {
            System.out.println("This timer is already running");
            return;
        }

        isRunning = true;
        startWorkTimer();
    }

    private void startWorkTimer() {
        workThread = new Thread(() -> {
            try {
                System.out.printf("Work period started (%d remaining)%n", repeats);
                Thread.sleep(workTime);
                if (isRunning) {
                    startRestTimer();
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        });
        workThread.setDaemon(true);
        workThread.start();
    }


    private void startRestTimer() {
        restThread = new Thread(() -> {
            try {
                System.out.println("Rest period started");
                Thread.sleep(restTime);
                if (isRunning) {
                    if (repeats == 1) {
                        stop();
                        if (callback != null) {
                            callback.onTimerComplete();
                        } 
                    }
                    if (repeats > 1) {
                        repeats--;
                        startWorkTimer();
                    }
                }     
            } catch (Exception e) {
                System.out.println("Rest timer stopped");
                Thread.currentThread().interrupt();
            }  
        });
        restThread.setDaemon(true);
        restThread.start();
    }


    @Override
    public void stop() {
        if (!isRunning) {
            System.out.println("No timer is currently running");
            return;
        }

        isRunning = false;
        if (workThread != null) {
            workThread.interrupt();
        }
        if (restThread != null) {
            restThread.interrupt();
        }
        System.out.println("Timer stopped");
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
    

}
