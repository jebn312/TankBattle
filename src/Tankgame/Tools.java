package Tankgame;

import java.util.Vector;

public class Tools {
    public static void hitHero(Bullet b, Hero hero, Vector<Bomb> bombs) {
        if (!hero.isLive) return;
        if (hero.getDirection() == 0 || hero.getDirection() == 1) {
            if (hero.getX() <= b.getX() && hero.getX() + 50 >= b.getX() && hero.getY() <= b.getY() && hero.getY() + 60 >= b.getY()) {
                b.setLive(false);
                hero.lifeDown();
                if (hero.getLifeBlood() <= 0) {
                    hero.isLive = false;
                    bombs.add(new Bomb(hero.getX(), hero.getY()));
                    new Thread(hero).start();
                }
            }
        } else {
            if (hero.getX() <= b.getX() && hero.getX() + 60 >= b.getX() && hero.getY() <= b.getY() && hero.getY() + 50 >= b.getY()) {
                b.setLive(false);
                hero.lifeDown();
                if (hero.getLifeBlood() <= 0) {
                    hero.isLive = false;
                    bombs.add(new Bomb(hero.getX(), hero.getY()));
                    new Thread(hero).start();
                }
            }
        }
    }

    public static void hitTank(Bullet b, Enemy e, Vector<Bomb> bombs) {
        if (!e.isLive) return;
        if (e.getDirection() == 0 || e.getDirection() == 1) {
            if (e.getX() <= b.getX() && e.getX() + 50 >= b.getX() && e.getY() <= b.getY() && e.getY() + 60 >= b.getY()) {
                b.setLive(false);
                e.lifeDown();
                if (e.getLifeBlood() <= 0) {
                    e.isLive = false;
                    bombs.add(new Bomb(e.getX(), e.getY()));
                }
            }
        } else {
            if (e.getX() <= b.getX() && e.getX() + 60 >= b.getX() && e.getY() <= b.getY() && e.getY() + 50 >= b.getY()) {
                b.setLive(false);
                e.lifeDown();
                if (e.getLifeBlood() <= 0) {
                    e.isLive = false;
                    bombs.add(new Bomb(e.getX(), e.getY()));
                }
            }
        }
    }
}
