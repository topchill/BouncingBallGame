package cegepst.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameWindow extends JFrame {

    private static final int SLEEP = 25;
    private long before;
    private JPanel panel;
    private Ball ball;
    private int score;
    private boolean playing = true;
    private BufferedImage bufferedImage;
    private Graphics2D buffer;


    public GameWindow() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("BouncingBallGame");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        super.add(panel);


        ball = new Ball(25);
        //affichage  setVisible(true);
        //enlever barre en haut setUndecorated(true);
    }

    public void start() {
        setVisible(true);
        before = System.currentTimeMillis();
        while (playing) {
            bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            buffer = bufferedImage.createGraphics();
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            buffer.setRenderingHints(rh);

            update();
            drawOnBuffer();
            drawOnScreen();

            long sleep = SLEEP - (System.currentTimeMillis() - before);
            if (sleep < 0) {
                sleep = 4;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            before = System.currentTimeMillis();
        }
    }

    private void update() {
        ball.update();
        if (ball.hasTouchedBound()) {
            score += 10;
        }
    }

    private void drawOnBuffer() {
        ball.draw(buffer);
        buffer.setPaint(Color.white);
        buffer.drawString("score: " + score, 10, 20);
    }

    private void drawOnScreen() {
        Graphics2D graphics2D = (Graphics2D) panel.getGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, panel);
        Toolkit.getDefaultToolkit().sync();
        graphics2D.dispose();
    }
}