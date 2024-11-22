package Tankgame;

import java.awt.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Timer;
import java.util.Vector;

public class Enemy extends Tank implements Runnable {

    Vector<Bullet> bullets = new Vector<>();
    Vector<Enemy> enemies = new Vector<>();
    Bullet bullet;
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
        directionTime = (int) (Math.random() * 50) + 200;
        for (int i = 0; i < directionTime; i++) {
            if (isWall() || isTouchEnemy()) {
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
        setDirection((int) (Math.random() * 10) % 4);

    }

    public boolean isTouchEnemy() {
        if (getDirection() == 0) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if (e != this) {
                    if (e.getDirection() == 1 || e.getDirection() == 0)
                        if(e.getX() <= getX() && getX() <= e.getX() + 40 && e.getY() <= getY() && getY() <= e.getY() + 60 || e.getX() <= getX() + 40 && getX() + 40 <= e.getX() + 40 && e.getY() <= getY() && getY() <= e.getY() + 60)
                            return true;
                    if (e.getDirection() == 2 || e.getDirection() == 3)
                        if(e.getX() - 10 <= getX() && getX() <= e.getX() + 50 && e.getY() + 10 <= getY() && getY() <= e.getY() + 50 || e.getX() - 10 <= getX() + 40 && getX() + 40 <= e.getX() + 50 && e.getY() + 10<= getY() && getY() <= e.getY() + 50)
                            return true;
                }
            }
        } else if(getDirection() == 1) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if(e != this) {
                    if(e.getDirection() == 1 || e.getDirection() == 0)
                        if(e.getX() <= getX() && getX() <= e.getX() + 40
                                && e.getY() <= getY() + 60 && getY() + 60 <= e.getY() + 60
                                || e.getX() <= getX() + 40 && getX() + 40 <= e.getX() + 40
                                && e.getY() <= getY() + 60 && getY() + 60 <= e.getY() + 60)
                            return true;
                    if(e.getDirection() == 2 || e.getDirection() == 3) {
                        if(e.getX() - 10 <= getX() && getX() <= e.getX() + 50
                                && e.getY() + 10 <= getY() + 60 && getY() + 60 <= e.getY() + 50
                                || e.getX() - 10 <= getX() + 40 && getX() + 40 <= e.getX() + 50
                                && e.getY() + 10<= getY() + 60 && getY() + 60 <= e.getY() + 50)
                            return true;
                    }
                }
            }
        } else if(getDirection() == 2) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if(e != this) {
                    if(e.getDirection() == 0 || e.getDirection() == 1)
                        if(e.getX() <= getX() - 10 && getX() - 10 <= e.getX() + 40
                                && e.getY() <= getY() + 10 && getY() + 10 <= e.getY() + 60
                                || e.getX() <= getX() - 10 && getX() - 10 <= e.getX() + 40
                                && e.getY() <= getY() + 50 && getY() + 50 <= e.getY() + 60)
                            return true;
                    if(e.getDirection() == 2 || e.getDirection() == 3)
                        if(e.getX() - 10 <= getX() - 10 && getX() - 10 <= e.getX() + 50
                                && e.getY() + 10 <= getY() + 10 && getY() + 10 <= e.getY() + 50
                                || e.getX() - 10 <= getX() - 10 && getX() - 10 <= e.getX() + 50
                                && e.getY() + 10 <= getY() + 50 && getY() + 50 <= e.getY() + 50)
                            return true;
                }
            }
        } else if(getDirection() == 3) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if (e != this) {
                    if (e.getDirection() == 0 || e.getDirection() == 1)
                        if (e.getX() <= getX() + 50 && getX() + 50 <= e.getX() + 40
                                && e.getY() <= getY() + 10 && getY() + 10 <= e.getY() + 60
                                || e.getX() <= getX() + 50 && getX() + 50 <= e.getX() + 40
                                && e.getY() <= getY() + 50 && getY() + 50 <= e.getY() + 60)
                            return true;
                    if (e.getDirection() == 2 || e.getDirection() == 3)
                        if (e.getX() - 10 <=  getX() + 50 && getX() + 50 <= e.getX() + 50
                                && e.getY() + 10 <= getY() + 10 && getY() + 10 <= e.getY() + 50
                                || e.getX() - 10 <= getX() + 50 && getX() + 50 <= e.getX() + 50
                                && e.getY() + 10 <= getY() + 50 && getY() + 50 <= e.getY() + 50)
                            return true;
                }
            }
        }
        return false;
    }

    public void setEnemies(Vector<Enemy> e) {
        this.enemies = e;
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
