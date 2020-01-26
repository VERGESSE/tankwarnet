package www.vergessen.top;

import www.vergessen.top.net.BulletNewMsg;
import www.vergessen.top.net.Client;
import www.vergessen.top.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

import static www.vergessen.top.Bullet.HEIGHT;
import static www.vergessen.top.Bullet.WIDTH;

public class Tank {
    private int x,y;
    private Dir dir;
    private static final int SPEED = 5;
    private boolean moving = false;
    private TankFrame tankFrame;
    private boolean living = true;
    private Group group = Group.GOOD;
    UUID id ;

    private Random random = new Random();

    public Rectangle rectangle = new Rectangle();

    public static int GOODWIDTH = ResourceMgr.goodTankU.getWidth();
    public static int GOODHEIGHT = ResourceMgr.goodTankU.getHeight();

    public Tank(int x, int y, Dir dir,Group group,TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
        this.id = UUID.randomUUID();

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = GOODWIDTH;
        rectangle.height = GOODHEIGHT;
    }

    public Tank(TankJoinMsg msg) {
        this.x = msg.x;
        this.y = msg.y;
        this.dir = msg.dir;
        this.moving = msg.moving;
        this.group = msg.group;
        this.id = msg.id;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
    }

    public void paint(Graphics g) {
//        if(!living) {
//            tankFrame.tanks.remove(this.id);
//        }
        Color color = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(),this.x,this.y - 10);
        g.setColor(color);
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
        if(this.x < 2 || this.y < 32 || this.x > TankFrame.GAME_WIDTH - GOODWIDTH - 2|| this.y > TankFrame.GAME_HEIGHT - GOODHEIGHT - 2)
            this.dir = Dir.getRandomDir();
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
        if(this.group == Group.GOOD)
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        int bX = this.x + Tank.GOODWIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + Tank.GOODHEIGHT/2 - Bullet.HEIGHT/2;

        Bullet b = new Bullet(this.id, bX, bY, this.dir, this.group, this.tankFrame);

        tankFrame.bullets.add(b);

        Client.INSTANCE.send(new BulletNewMsg(b));
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
        TankFrame.INSTANCE.exploders.add(new Exploder(this.getX() + Tank.GOODWIDTH/2-Exploder.WIDTH/2,this.getY()+Tank.GOODHEIGHT/2-Exploder.HEIGHT/2,TankFrame.INSTANCE));
        if(this.equals(TankFrame.INSTANCE.myTank)){
            System.exit(0);
        }else {
            TankFrame.INSTANCE.tanks.remove(this.id);

        }
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static int getSPEED() {
        return SPEED;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public static int getGOODWIDTH() {
        return GOODWIDTH;
    }

    public static void setGOODWIDTH(int GOODWIDTH) {
        Tank.GOODWIDTH = GOODWIDTH;
    }

    public static int getGOODHEIGHT() {
        return GOODHEIGHT;
    }

    public static void setGOODHEIGHT(int GOODHEIGHT) {
        Tank.GOODHEIGHT = GOODHEIGHT;
    }
}
