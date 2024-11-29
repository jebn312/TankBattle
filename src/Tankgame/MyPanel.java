package Tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero;
    public static Vector<Enemy> enemies;
    Vector<Bomb> bombs = new Vector<>();
    int enemySize = 9;
    @Override
    public void run() {
        while (true) {
            synchronized (MyPanel.this) {
                if (hero != null) {
                    for (int i = 0; i < hero.bullets.size(); i++) {
                        Bullet b = hero.bullets.get(i);
                        //noinspection ForLoopReplaceableByForEach
                        for (int j = 0; j < enemies.size(); j++) {
                            hitTank(enemies.get(j), b, bombs);
                        }
                    }

                    //noinspection ForLoopReplaceableByForEach
                    for (int i = 0; i < enemies.size(); i++) {
                        for (int j = 0; j < enemies.get(i).bullets.size(); j++) {
                            Bullet b = enemies.get(i).bullets.get(j);
                            if (hero.isLive) hitTank(hero, b, bombs);
                        }
                    }
                }
            }
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public MyPanel(int flag) {
        this();
        if(flag == 0) {
            for (int i = 0; i < enemySize; i++) {
                Enemy e = new Enemy(100 * (i + 1), 0);
                enemies.add(e);
                e.setEnemies(enemies);
                new Thread(e).start();
            }
        }
        else {
            for (int i = 0; i < enemies.size(); i++) {
                new Thread(enemies.get(i)).start();
                Bullet b;
                for (int j = 0; j < enemies.get(i).bullets.size(); j++) {
                    b = enemies.get(i).bullets.get(j);
                    if(b.isLive()) new Thread(b).start();
                }
            }
        }
        new Thread(this).start();
        new Audio().play();
    }
    public MyPanel() {
        hero = new Hero(100, 100);
        hero.setSpeed(10);
        Recording.setEnemies(enemies);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 800, 550);
        if (hero != null) {
            if (hero.isLive) Tank.draw(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
            g.setColor(Color.yellow);
            Bullet.drawHeroBullet(g, hero);
        }
        Enemy.draw(g, enemies);
        g.setColor(Color.cyan);
        Bullet.drawEnemyBullet(g, enemies);
        Bomb.draw(g, bombs);
        Recording.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!(hero != null && hero.isLive)) return;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) System.out.println(hero.getLifeBlood());
        if (!hero.isLive) return;
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirection(0);
            hero.move();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(1);
            hero.move();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(2);
            hero.move();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(3);
            hero.move();
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            if (hero.bullets.size() <= 6) hero.shot();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void hitTank(Tank tank, Bullet bullet, Vector<Bomb> bombs) {
        if (!tank.isLive) return;
        if (tank.getDirection() == 0 || tank.getDirection() == 1) {
            if (tank.getX() <= bullet.getX() && bullet.getX() <= tank.getX() + 40 && tank.getY() <= bullet.getY() && bullet.getY() <= tank.getY() + 60) {
                bullet.setLive(false);
                tank.lifeDown();
                if (tank.getLifeBlood() <= 0) {
                    tank.isLive = false;
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                    if (tank instanceof Enemy) Recording.addScore();
                    if (tank instanceof Hero) {
                        new Thread(() -> {
                            int i = 1000;
                            while (!hero.bullets.isEmpty()) ;
                            synchronized (MyPanel.class) {
                                hero = null;
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            hero = new Hero(100, 100);
                            hero.setSpeed(10);
                        }).start();
                    }
                }
            }
        } else {
            if (tank.getX() - 10 <= bullet.getX() && bullet.getX() <= tank.getX() + 50 && tank.getY() + 10 <= bullet.getY() && bullet.getY() <= tank.getY() + 50) {
                bullet.setLive(false);
                tank.lifeDown();
                if (tank.getLifeBlood() <= 0) {
                    tank.isLive = false;
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                    if (tank instanceof Enemy) Recording.addScore();
                    if (tank instanceof Hero) {
                        new Thread(() -> {
                            int i = 1000;
                            while (!hero.bullets.isEmpty()) ;
                            synchronized (MyPanel.class) {
                                hero = null;
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            hero = new Hero(100, 100);
                            hero.setSpeed(10);
                        }).start();
                    }
                }
            }
        }
    }
}
