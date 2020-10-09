package cegepst.engine;

import java.util.concurrent.TimeUnit;

public class GameTime {

    private static final int FPS_TARGET = 60;
    private static int currentFps;
    private static int fpsCount;
    private static long fpsDelta;
    private static long gameStartTime;
    private long syncTime;

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static int getCurrentFps() {
        return(currentFps > 0) ? currentFps : fpsCount;
    }

    public static long getElapsedTime() {
        return getCurrentTime() - gameStartTime;
    }

    public static String getElapsedFormattedTime() {
        long time = getCurrentTime() - gameStartTime;
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        //time -= TimeUnit.SECONDS.toMillis(seconds);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public GameTime() {
        updateSyncTime();
        gameStartTime = getCurrentTime();
        fpsDelta = 0;
        currentFps = 0;
    }

    private void update() {
        fpsCount++;
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(getElapsedTime());
        if (fpsDelta != currentSecond) {
            currentFps = fpsCount;
            fpsCount = 0;
        }
        fpsDelta = currentSecond;
    }

    public void synchronize() {
        update();
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateSyncTime();
    }

    private long getSleepTime() {
        long targetTime = 1000L / FPS_TARGET;
        long sleep = targetTime - (getCurrentTime() - syncTime);
        if (sleep < 0) {
            sleep = 4;
        }
        return sleep;
    }

    private void updateSyncTime() {
        syncTime = getCurrentTime();
    }
}
