package Tankgame;

import java.awt.*;
import java.util.Vector;

public class Enemy extends Tank implements Runnable {

    Vector<Bullet> bullets = new Vector<>();
    Bullet bullet;
    boolean isLive = true;
    int directionTime;

    @Override
    public void run() {
        while (isLive) {
            move();
        }
    }

    public Enemy(int x, int y) {
        super(x, y);
        setDirection((int) (Math.random() * 10) % 4);
        setSpeed(4);
    }

    @Override
    public void move() {
        directionTime = (int) (Math.random() * 20) + 7;
        for (int i = 0; i < directionTime; i++) {
            if (isWall()) {
                int direct;
                switch (getDirection()) {
                    case 0:
                        while ((direct = (int) (Math.random() * 4)) == 0) ;
                        setDirection(direct);
                        break;
                    case 1:
                        while ((direct = (int) (Math.random() * 4)) == 1) ;
                        setDirection(direct);
                        break;
                    case 2:
                        while ((direct = (int) (Math.random() * 4)) == 2) ;
                        setDirection(direct);
                        break;
                    case 3:
                        while ((direct = (int) (Math.random() * 4)) == 3) ;
                        setDirection(direct);
                        break;
                }
            }
            super.move();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void shot() {
        bullet = new Bullet(getX(), getY(), getDirection(), 10);
        bullets.add(bullet);
        new Thread(bullet).start();
    }

    public static void draw(Graphics g, Vector<Enemy> enemies) {
        Enemy e;
        for (int i = 0; i < enemies.size(); i++) {
            e = enemies.get(i);
            if (e.isLive) {
                Tank.draw(e.getX(), e.getY(), g, e.getDirection(), 0);
                if ((int) (Math.random() * 100) == 0) e.shot();
            } else {
                if (e.bullets.size() <= 0) {
                    enemies.remove(e);
                    i--;
                }
            }
        }
    }

}
