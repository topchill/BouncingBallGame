package cegepst.engine;

import java.awt.*;

public class BouncingBallGame extends Game {

    private Ball ball;
    private int score;

    public BouncingBallGame() {
        ball = new Ball(25);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void conclude() {

    }

    @Override
    public void update() {
        ball.update();
        if (ball.hasTouchedBound()) {
            score += 10;
        }
    }

    @Override
    public void draw(Buffer buffer) {
        ball.draw(buffer);
        buffer.drawText("score: " + score, 10, 20, Color.white);
    }
}
