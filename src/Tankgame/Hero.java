package Tankgame;

import java.util.Vector;

public class Hero extends Tank implements Runnable {
    Vector<Bullet> bullets = new Vector<>();
    Bullet bullet;
    boolean isLive = true;


    public Hero(int x, int y) {
        super(x, y);
    }
    public void shot() {
        bullet = new Bullet(getX(), getY(), getDirection(), 10);
        bullets.add(bullet);
        new Thread(bullet).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        reset();
        reLifeBlood();
        isLive = true;
    }

    @Override
    public void move() {
        if(isWall()) return;
        super.move();
    }
    public void reset() {
        setXY(100,100);
    }


}
