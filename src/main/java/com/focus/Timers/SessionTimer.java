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
                sleepWithPause(workTime);
                
                if (!isRunning) {stop();}
    
                System.out.println("Rest period started");
                sleepWithPause(restTime);


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

    private void sleepWithPause(int duration) throws InterruptedException {
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
    }


    public void pause() {
        synchronized (lock) {
            isPaused = true;
        }
    }

    public void resume(){
        synchronized (lock) {
            isPaused = false;
            lock.notifyAll();
        }
    }

}
