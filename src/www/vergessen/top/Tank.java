package www.vergessen.top;

import java.awt.*;
import java.util.Random;

public class Tank extends GameObject {
    int x,y;
    Dir dir;
    private static final int SPEED = 6;
    private boolean moving = false;
    private boolean living = true;
    Group group = Group.BAD;
    private Random random = new Random();
    FireStrategy fs;
    private int oldX,oldY;

    private Rectangle rectangle = new Rectangle();

    public static int GOODWIDTH = ResourceMgr.goodTankU.getWidth();
    public static int GOODHEIGHT = ResourceMgr.goodTankU.getHeight();

    public Tank(int x, int y, Dir dir,Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = GOODWIDTH;
        rectangle.height = GOODHEIGHT;
        if(group == Group.GOOD) {
            try {
                fs = (FireStrategy) Class.forName(PropertyMgr.instance().getString("goodFs")).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                fs = (FireStrategy) Class.forName(PropertyMgr.instance().getString("badFs")).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GameModel.getInstance().add(this);
    }

    public void paint(Graphics g) {
        if(!living) {
            GameModel.getInstance().remove(this);
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
        if(this.group == Group.BAD && random.nextInt(40) > 38) this.dir = Dir.getRandomDir();
        oldX = x;oldY = y;
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
        //边界判断
        if(this.group == Group.BAD) boundCheckBAD();
        else boundCheckGOOD();

        //update rectangle
        rectangle.x = this.x;
        rectangle.y = this.y;
    }

    private void boundCheckBAD(){
        if(this.x < 2 || this.y < 32 || this.x > TankFrame.GAME_WIDTH - GOODWIDTH - 2|| this.y > TankFrame.GAME_HEIGHT - GOODHEIGHT - 2) {
            this.x = this.oldX;
            this.y = this.oldY;
            this.dir = Dir.getRandomDir();
        }
//        if(this.x < 0) this.x = 2;
//        if(this.y <30) this.y = 32;
//        if(this.x > TankFrame.GAME_WIDTH - GOODWIDTH - 2) this.x = TankFrame.GAME_WIDTH - GOODWIDTH - 3;
//        if(this.y > TankFrame.GAME_HEIGHT - GOODHEIGHT - 2) this.y = TankFrame.GAME_HEIGHT - GOODHEIGHT - 3;
    }
    private void boundCheckGOOD(){
        if(this.x < 2)
            this.x = 2;
        if(this.y < 32)
            this.y = 32;
        if(this.x > TankFrame.GAME_WIDTH - GOODWIDTH - 2)
            this.x = TankFrame.GAME_WIDTH - GOODWIDTH - 2;
        if(this.y > TankFrame.GAME_HEIGHT - GOODHEIGHT - 2)
            this.y = TankFrame.GAME_HEIGHT - GOODHEIGHT - 2;
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
        fs.fire(this);
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }
}
