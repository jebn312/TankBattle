package Tankgame;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

public class Bullet implements Runnable, Serializable {
    private int x;
    private int y;
    private final int direction;
    private final int speed;
    private boolean isLive = true;

    @Override
    public void run() {
        while (isLive) {
            move();
            if (!(x >= 0 && x <= 800 && y >= 0 && y <= 550)) {
                isLive = false;
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Bullet(int x, int y, int direction, int speed) {
        this.direction = direction;
        this.speed = speed;
        if (direction == 0 || direction == 1) {
            this.y = y;
            this.x = x + 19;
            if (direction == 1)
                this.y = y + 60;
        } else if (direction == 2 || direction == 3) {
            this.x = x - 10;
            this.y = y + 29;
            if (direction == 3) this.x = x + 50;
        }
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    private void move() {
        switch (direction) {
            case 0:
                y -= speed;
                break;
            case 1:
                y += speed;
                break;
            case 2:
                x -= speed;
                break;
            case 3:
                x += speed;
                break;
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void drawEnemyBullet(Graphics g, Vector<Enemy> enemies) {
        Bullet bullet;
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < enemies.size(); i++) {

            for (int j = 0; j < enemies.get(i).bullets.size(); j++) {
                bullet = enemies.get(i).bullets.get(j);
                if (bullet.isLive()) g.fill3DRect(bullet.getX(), bullet.getY(), 3, 3, false);
                else {
                    enemies.get(i).bullets.remove(bullet);
                    j--;
                }
            }
        }
    }
    public static void drawHeroBullet(Graphics g, Hero hero) {
        Bullet bullet;
        for (int i = 0; i < hero.bullets.size(); i++) {
            bullet = hero.bullets.get(i);
            if (bullet.isLive()) g.fill3DRect(bullet.getX(), bullet.getY(), 3, 3, false);
            else {
                hero.bullets.remove(bullet);
                i--;
            }
        }
    }

}
