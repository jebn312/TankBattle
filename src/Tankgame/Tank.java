package Tankgame;

import java.awt.*;

public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed = 1;
    private int lifeBlood = 100;
    boolean isLive = true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
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

    public boolean isWall() {
        //上 下 左 右
        return y <= 0 && direction == 0 || direction == 1 && y + 45 >= 500 || direction == 2 && x - 5 <= 0 || direction == 3 && x + 65 >= 800;
    }

    public void lifeDown() {
        lifeBlood -= (int) (Math.random() * 30) + 3;
    }

    public void reLifeBlood() {
        lifeBlood = 100;
    }

    protected void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;

    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public int getLifeBlood() {
        return lifeBlood;
    }

    /**
     * @param x         坦克x坐标
     * @param y         坦克y坐标
     * @param g         画笔
     * @param direction 0上1下2左3右
     * @param type      坦克颜色
     */
    public static void draw(int x, int y, Graphics g, int direction, int type) {
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
}
