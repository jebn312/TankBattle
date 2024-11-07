package Tankgame;

public class Bullet implements Runnable {
    private int x;
    private int y;
    private int direction;
    private int speed;
    private boolean isLive = true;

    public Bullet(int x, int y, int direction, int speed) {
        this.direction = direction;
        this.speed = speed;
        if(direction == 0 || direction == 1) {
            this.y =y;
            this.x = x + 19;
            if(direction == 1)
                this.y = y + 60;
        }
        else if(direction == 2 || direction == 3) {
            this.x = x - 10;
            this.y = y + 29;
            if(direction == 3) this.x = x + 50;
        }
    }

    public boolean isLive() {
        return isLive;
    }

    @Override
    public void run() {
        while (isLive) {
           move();
            if(!(x >= 0 && x <= 800 && y >= 0 && y <= 550) ) {
                isLive = false;
                break;
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void move() {
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
    public void setLive(boolean live) {
        isLive = live;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
