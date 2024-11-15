package Tankgame;

import java.util.Vector;

public class Hero extends Tank {
    Vector<Bullet> bullets = new Vector<>();
    Bullet bullet;
    public Hero(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        if (isWall()) return;
        super.move();

    }

    public void shot() {
        bullet = new Bullet(getX(), getY(), getDirection(), 10);
        bullets.add(bullet);
        new Thread(bullet).start();
    }

}
