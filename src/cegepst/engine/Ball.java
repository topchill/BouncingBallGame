package cegepst.engine;

import java.awt.*;
import java.util.Random;

public class Ball {

    private final int radius;
    private int x;
    private int y;
    private int velocityX;
    private int velocityY;

    public Ball(int radius) {
        this.radius = radius;
        x = getRandom(0 + radius * 2, 800 - radius * 2);
        y = getRandom(0 + radius * 2, 600 - radius * 2);
        velocityX = getRandom(0, 1) == 0 ? 2 : -4;
        velocityY = getRandom(0, 1) == 0 ? 2 : -4;
    }

    public void update() {
        x += velocityX;
        y += velocityY;

        if (hasTouchedVerticalBound()) {
            velocityY *= -1;
        }
        if (hasTouchedHorizontalBound()) {
            velocityX *= -1;
        }
    }

    public void draw(Buffer buffer) {
        buffer.drawCircle(x, y, radius, Color.red);
    }

    public boolean hasTouchedBound() {
        return hasTouchedHorizontalBound() || hasTouchedVerticalBound();
    }

    private boolean hasTouchedHorizontalBound() {
        return x <= radius || x >= 800 - radius;
    }

    private boolean hasTouchedVerticalBound() {
        return y <= radius || y >= 600 - radius;
    }

    private int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
