package Tankgame;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;

public class Bomb implements Runnable {
    static ImageIcon boom1 = new ImageIcon(Objects.requireNonNull(MyPanel.class.getResource("/images/boom1.png")));
    static ImageIcon boom2 = new ImageIcon(Objects.requireNonNull(MyPanel.class.getResource("/images/boom2.png")));
    static ImageIcon boom3 = new ImageIcon(Objects.requireNonNull(MyPanel.class.getResource("/images/boom3.png")));
    final private int x;
    final private int y;
    boolean isLive = true;
    private int liveTime = 9;

    @Override
    public void run() {
        while (isLive) {
            lifeDown();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown() {
        if (liveTime > 0) liveTime--;
        else isLive = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public static void draw(Graphics g, Vector<Bomb> boms) {
        Bomb b;
        for (int i = 0; i < boms.size(); i++) {
            b = boms.get(i);
            if (b.getLiveTime() > 6) g.drawImage(boom1.getImage(), b.getX(), b.getY(), 60, 60, null);
            else if (b.getLiveTime() > 3) g.drawImage(boom2.getImage(), b.getX(), b.getY(), 60, 60, null);
            else g.drawImage(boom3.getImage(), b.getX(), b.getY(), 60, 60, null);
            b.lifeDown();
            if (!b.isLive) {
                boms.remove(b);
                i--;
            }
        }
    }
}