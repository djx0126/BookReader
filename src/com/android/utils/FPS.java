package com.android.utils;

public class FPS {
    private static final short MAX_FRAMES = 10000;
    private int frames;
    private long startTime;
    private float fps = 0.0f;
    private int sleepTime = 20;

    public FPS() {
        frames = MAX_FRAMES;
    }

    public void reset() {
        frames = MAX_FRAMES;
    }

    public void tick() {
        frames++;
        if (frames > MAX_FRAMES) {
            frames = 0;
            startTime = System.currentTimeMillis();
            ;
        } else {
            long now = System.currentTimeMillis();
            long deltaTime = now - startTime;
            float timeperframes = (deltaTime) / 1000.0f / frames;
            fps = Math.round(1000.0f / timeperframes) / 1000.0f;
        }
    }

    public void tick(float targetFPS) {
        tick();
        if (targetFPS - fps > 1) {
            if (sleepTime > 1) {
                sleepTime -= 1;
            }
        } else if (fps - targetFPS > 1) {
            sleepTime += 1;
        }
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public float getFPS() {
        return fps;
    }
}
