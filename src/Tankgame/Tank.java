package Tankgame;

public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed = 1;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move() {
        //800 * 500
        if(y <= 0 && direction == 0 || direction == 1 && y + 50 >= 500 || direction == 2 && x - 10 <= 0 || direction == 3 && x + 65 >= 800) return;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
