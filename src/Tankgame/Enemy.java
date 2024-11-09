package Tankgame;

import java.util.Vector;

public class Enemy extends Tank implements Runnable {
    Vector<Bullet> bullets = new Vector<>();
    Bullet bullet;
    boolean isLive = true;
    int directionTime;

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
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void run() {
        while (isLive) {
            move();
        }
    }

    public void shot() {
        bullet = new Bullet(getX(), getY(), getDirection(), 10);
        bullets.add(bullet);
        new Thread(bullet).start();
    }
}
