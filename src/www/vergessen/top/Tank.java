package www.vergessen.top;

import java.awt.*;
import java.util.Random;

public class Tank {
    private int x,y;
    private Dir dir;
    private static final int SPEED = 5;
    private boolean moving = false;
    private TankFrame tankFrame;
    private boolean living = true;
    private Group group = Group.BAD;
    private Random random = new Random();

    public static int GOODWIDTH = ResourceMgr.goodTankU.getWidth();
    public static int GOODHEIGHT = ResourceMgr.goodTankU.getHeight();

    public Tank(int x, int y, Dir dir,Group group,TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        if(!living) {
            tankFrame.badTank.remove(this);
        }
        if(this.group == Group.GOOD) {
            switch (dir) {
                case UP:
                    g.drawImage(ResourceMgr.goodTankU, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.goodTankD, x, y, null);
                    break;
                case LEFT:
                    g.drawImage(ResourceMgr.goodTankL, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.goodTankR, x, y, null);
                    break;
                case LEFT_UP:
                    g.drawImage(ResourceMgr.goodTankLU, x, y, null);
                    break;
                case RIGHT_UP:
                    g.drawImage(ResourceMgr.goodTankRU, x, y, null);
                    break;
                case LEFT_DOWN:
                    g.drawImage(ResourceMgr.goodTankLD, x, y, null);
                    break;
                case RIGHT_DOWN:
                    g.drawImage(ResourceMgr.goodTankRD, x, y, null);
                    break;
            }
        }
        if(this.group == Group.BAD) {
            switch (dir) {
                case UP:
                    g.drawImage(ResourceMgr.badTankU, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.badTankD, x, y, null);
                    break;
                case LEFT:
                    g.drawImage(ResourceMgr.badTankL, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.badTankR, x, y, null);
                    break;
                case LEFT_UP:
                    g.drawImage(ResourceMgr.badTankLU, x, y, null);
                    break;
                case RIGHT_UP:
                    g.drawImage(ResourceMgr.badTankRU, x, y, null);
                    break;
                case LEFT_DOWN:
                    g.drawImage(ResourceMgr.badTankLD, x, y, null);
                    break;
                case RIGHT_DOWN:
                    g.drawImage(ResourceMgr.badTankRD, x, y, null);
                    break;
            }
        }
        if(this.group == Group.BAD)
            this.moving = true;
        move();
    }

    private void move() {
        if(!moving)
            return;
        if(random.nextInt(40) > 38) this.dir = Dir.getRandomDir();
        switch (dir){
            case LEFT: x-=SPEED;break;
            case UP: y-=SPEED;break;
            case RIGHT: x+=SPEED;break;
            case DOWN: y+=SPEED;break;
            case LEFT_UP: x-=SPEED/1.414;y-=SPEED/1.414;break;
            case LEFT_DOWN: x-=SPEED/1.414;y+=SPEED/1.414;break;
            case RIGHT_DOWN: x+=SPEED/1.414;y+=SPEED/1.414;break;
            case RIGHT_UP: x+=SPEED/1.414;y-=SPEED/1.414;break;
        }
        if(this.group == Group.BAD && random.nextInt(20) > 18) this.fire();
        if(this.x < 0 || this.y < 0 || this.x > TankFrame.GAME_WIDTH - GOODWIDTH|| this.y > TankFrame.GAME_HEIGHT - GOODHEIGHT)
            this.dir = Dir.getRandomDir();
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void fire() {
        if(this.group == Group.GOOD)
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        int bx = this.x + Tank.GOODWIDTH / 2 - Bullet.WIDTH / 2;
        int by = this.y + Tank.GOODHEIGHT / 2 - Bullet.HEIGHT /2;
        tankFrame.bullets.add(new Bullet(bx, by, this.dir,this.group,this.tankFrame));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void die() {
        living = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
