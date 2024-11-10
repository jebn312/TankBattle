package Tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero;
    Vector<Enemy> enemies = new Vector<>();
    Vector<Bomb> boobs = new Vector<>();
    ImageIcon boom1 = new ImageIcon(Objects.requireNonNull(MyPanel.class.getResource("/images/boom1.png")));
    ImageIcon boom2 = new ImageIcon(Objects.requireNonNull(MyPanel.class.getResource("/images/boom2.png")));
    ImageIcon boom3 = new ImageIcon(Objects.requireNonNull(MyPanel.class.getResource("/images/boom3.png")));
    int enemySize = 3;

    public MyPanel() {
        hero = new Hero(100, 100);
        hero.setSpeed(10);
        for (int i = 0; i < enemySize; i++) {
            Enemy e = new Enemy(100 * (i + 1), 0);
            enemies.add(e);
            new Thread(e).start();
        }
        new Thread(this).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 800, 550);
        if(hero.isLive) drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        drawHeroBullet(g);
        drawEnemies(g);
        g.setColor(Color.cyan);
        drawEnemyBullet(g);
        drawBoom(g);
    }

    public void drawEnemies(Graphics g) {
        Enemy e;
        for (int i = 0; i < enemies.size(); i++) {
            e = enemies.get(i);
            if (e.isLive) {
                drawTank(e.getX(), e.getY(), g, e.getDirection(), 0);
                if ((int) (Math.random() * 100) == 0) e.shot();
            } else {
                if (e.bullets.size() <= 0) {
                    enemies.remove(e);
                    i--;
                }
            }
        }
    }

    public void drawEnemyBullet(Graphics g) {
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

    public void drawBoom(Graphics g) {
        Bomb b;
        for (int i = 0; i < boobs.size(); i++) {
            b = boobs.get(i);
            if (b.getLiveTime() > 6) g.drawImage(boom1.getImage(), b.getX(), b.getY(), 60, 60, this);
            else if (b.getLiveTime() > 3) g.drawImage(boom2.getImage(), b.getX(), b.getY(), 60, 60, this);
            else g.drawImage(boom3.getImage(), b.getX(), b.getY(), 60, 60, this);
            b.lifeDown();
            if (!b.isLive) {
                boobs.remove(b);
                i--;
            }
        }
    }

    public void drawHeroBullet(Graphics g) {
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

    /**
     * @param x         坦克x坐标
     * @param y         坦克y坐标
     * @param g         画笔
     * @param direction 0上1下2左3右
     * @param type      坦克颜色
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.CYAN);
                break;
            case 1:
                g.setColor(Color.YELLOW);
                break;
        }
        switch (direction) {
            case 0://UP
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.drawLine(x + 20, y, x + 20, y + 30);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            case 1://DOWN
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            case 2://LEFT
                g.fill3DRect(x - 10, y + 10, 60, 10, false);
                g.fill3DRect(x, y + 20, 40, 20, false);
                g.fill3DRect(x - 10, y + 40, 60, 10, false);
                g.drawLine(x - 10, y + 30, x + 10, y + 30);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            case 3://RIGHT
                g.fill3DRect(x - 10, y + 10, 60, 10, false);
                g.fill3DRect(x, y + 20, 40, 20, false);
                g.fill3DRect(x - 10, y + 40, 60, 10, false);
                g.drawLine(x + 50, y + 30, x + 30, y + 30);
                g.fillOval(x + 10, y + 20, 20, 20);
                break;
            default:
                System.out.println("Not Processed");
        }
    }

    public void hitHero(Bullet b) {
        if (!hero.isLive) return;
        if (hero.getDirection() == 0 || hero.getDirection() == 1) {
            if (hero.getX() <= b.getX() && hero.getX() + 40 >= b.getX() && hero.getY() <= b.getY() && hero.getY() + 60 >= b.getY()) {
                b.setLive(false);
                hero.lifeDown();
                if(hero.getLifeBlood() <= 0) {
                    hero.isLive = false;
                    boobs.add(new Bomb(hero.getX(), hero.getY()));
                    new Thread(hero).start();
                }
            }
        } else {
            if (hero.getX() <= b.getX() && hero.getX() + 60 >= b.getX() && hero.getY() <= b.getY() && hero.getY() + 40 >= b.getY()) {
                b.setLive(false);
                hero.lifeDown();
                if(hero.getLifeBlood() <= 0) {
                    hero.isLive = false;
                    boobs.add(new Bomb(hero.getX(), hero.getY()));
                    new Thread(hero).start();
                }
            }
        }
    }


    public void hitTank(Bullet b, Enemy e) {
        if (!e.isLive) return;
        if (e.getDirection() == 0 || e.getDirection() == 1) {
            if (e.getX() <= b.getX() && e.getX() + 40 >= b.getX() && e.getY() <= b.getY() && e.getY() + 60 >= b.getY()) {
                b.setLive(false);
                e.lifeDown();
                if(e.getLifeBlood() <= 0) {
                    e.isLive = false;
                    boobs.add(new Bomb(e.getX(), e.getY()));
                }
            }
        } else {
            if (e.getX() <= b.getX() && e.getX() + 60 >= b.getX() && e.getY() <= b.getY() && e.getY() + 40 >= b.getY()) {
                b.setLive(false);
                e.lifeDown();
                if(e.getLifeBlood() <= 0) {
                    e.isLive = false;
                    boobs.add(new Bomb(e.getX(), e.getY()));
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < hero.bullets.size(); i++) {
                Bullet b = hero.bullets.get(i);
                //noinspection ForLoopReplaceableByForEach
                for (int j = 0; j < enemies.size(); j++) {
                    hitTank(b, enemies.get(j));
                }
            }

            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < enemies.size(); i++) {
                for (int j = 0; j < enemies.get(i).bullets.size(); j++) {
                    Bullet b = enemies.get(i).bullets.get(j);
                    hitHero(b);
                }
            }

            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) System.out.println(hero.getLifeBlood());
        if(!hero.isLive) return;
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
            if(hero.bullets.size() <= 6) hero.shot();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
