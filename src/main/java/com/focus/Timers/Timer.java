package main.java.com.focus.Timers;

public class Timer implements TimerInterface{
    private Thread workThread;
    private Thread restThread;
    private Boolean isRunning;
    private int workTime;
    private int restTime;
    private TimerCallback callback;

    public Timer(int workTime, int restTime, TimerCallback callback){
        this.workTime = workTime * 60 * 1000;
        this.restTime = restTime * 60 * 1000;
        this.callback = callback;
        this.isRunning = false;
    }


    public void start() {
        //If running already we don't do anything
        if (isRunning) {
            System.out.println("This timer is already running");
            return;
        }
        
        isRunning = true;
        
        workThread = new Thread(() -> {
            try {
                System.out.println("Work timer started for " + (workTime/60000) + " minutes");
                Thread.sleep(workTime);
                if (isRunning) {
                    System.out.println("Work time complete! Starting resting timer.");
                    startRestTimer();
                }
            } catch (InterruptedException e) { //If shit went down
                System.out.println("Work timer stopped"); 
                Thread.currentThread().interrupt(); //Interrupt it
            }
            
        });  
        workThread.setDaemon(true);
        workThread.start();
    }

    private void startRestTimer(){
        restThread = new Thread(() -> {
            try {
                System.out.println("Rest timer started for " + (restTime/60000) + " minutes");
                Thread.sleep(restTime);
                if (isRunning) {
                    System.out.println("Rest time complete! Ready for next session.");
                    if (callback != null) {
                        callback.onTimerComplete();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Rest timer stopped");
            } finally {
                isRunning = false;
            }
        });
        restThread.setDaemon(true);
        restThread.start();
    }

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

    public boolean isRunning() {
        return isRunning;
    }

}

