package Tankgame;

import java.util.Vector;

public class Enemy extends Tank{
    Vector<Bullet> bullets = new Vector<>();
    Bullet bullet;
    boolean isLive = true;
    public Enemy(int x, int y) {
       super(x, y);
//       Random r = new Random();
//        setDirection(r.nextInt(3));
        setDirection((int)(Math.random()*10) % 4);
    }

    public void shot() {
        bullet = new Bullet(getX(), getY(), getDirection(), 10);
        bullets.add(bullet);
        new Thread(bullet).start();
    }
}
