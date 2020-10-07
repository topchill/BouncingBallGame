package cegepst.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameWindow extends JFrame {

    private static final int SLEEP = 25;
    private long before;
    private JPanel panel;
    private int radius = 25;
    private int x;
    private int y;
    private int dx;
    private int dy;
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

        x = getRandom(0 + radius * 2, 800 - radius * 2);
        y = getRandom(0 + radius * 2, 600 - radius * 2);
        dx = getRandom(0, 1) == 0 ? 2 : -4;
        dy = getRandom(0, 1) == 0 ? 2 : -4;


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
        x += dx;
        y += dy;

        if (y <= radius || y >= 600 - radius) {
            dy *= -1;
            score += 10;
        }
        if (x <= radius || x >= 800 - radius) {
            dx *= -1;
            score += 10;
        }
    }

    private void drawOnBuffer() {
        buffer.setPaint(Color.red);
        buffer.fillOval(x, y, radius * 2, radius * 2);

        buffer.setPaint(Color.white);
        buffer.drawString("score: " + score, 10, 20);
    }

    private void drawOnScreen() {
        Graphics2D graphics2D = (Graphics2D) panel.getGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, panel);
        Toolkit.getDefaultToolkit().sync();
        graphics2D.dispose();
    }

    private int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}
