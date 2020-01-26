package www.vergessen.top;

import www.vergessen.top.net.Client;
import www.vergessen.top.net.TankDieMsg;

import java.awt.*;
import java.util.UUID;

public class Bullet {
    private static final int SPEED = 15;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x,y;
    private Dir dir;

    private UUID id = UUID.randomUUID();
    private UUID playerId;

    private boolean living = true;
    private Group group = Group.BAD;
    private TankFrame tankFrame;

    private Rectangle rectangle = new Rectangle();

    public Bullet(UUID playerId, int x, int y, Dir dir, Group group, TankFrame tf) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tf;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

    }

    public void paint(Graphics g){
        if(!living){
            tankFrame.bullets.remove(this);
        }
        switch (dir){
            case UP:g.drawImage(ResourceMgr.bulletU,x,y,null);break;
            case DOWN:g.drawImage(ResourceMgr.bulletD,x,y,null);break;
            case LEFT:g.drawImage(ResourceMgr.bulletL,x,y,null);break;
            case RIGHT:g.drawImage(ResourceMgr.bulletR,x,y,null);break;
            case LEFT_UP:g.drawImage(ResourceMgr.bulletLU,x,y,null);break;
            case RIGHT_UP:g.drawImage(ResourceMgr.bulletRU,x,y,null);break;
            case LEFT_DOWN:g.drawImage(ResourceMgr.bulletLD,x,y,null);break;
            case RIGHT_DOWN:g.drawImage(ResourceMgr.bulletRD,x,y,null);break;
        }

        move();
    }

    private void move() {
        switch (dir){
            case UP: y-=SPEED;break;
            case LEFT: x-=SPEED;break;
            case RIGHT: x+=SPEED;break;
            case DOWN: y+=SPEED;break;
            case LEFT_UP: x-=SPEED/1.414;y-=SPEED/1.414;break;
            case LEFT_DOWN: x-=SPEED/1.414;y+=SPEED/1.414;break;
            case RIGHT_DOWN: x+=SPEED/1.414;y+=SPEED/1.414;break;
            case RIGHT_UP: x+=SPEED/1.414;y-=SPEED/1.414;break;
        }

        //update rectangle
        rectangle.x = this.x;
        rectangle.y = this.y;

        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
            living = false;
    }


    public void collideWith(Tank tank) {
        if(this.playerId.equals(tank.getId())) return;
        //System.out.println("bullet rect:" + this.rect);
        //System.out.println("tank rect:" + tank.rect);
        if(this.living && tank.isLiving() && this.rectangle.intersects(tank.rectangle)) {
            tank.die();
            this.die();
            Client.INSTANCE.send(new TankDieMsg(this.id, tank.getId()));
        }
    }

    public void die() {
        this.living = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int WIDTH) {
        Bullet.WIDTH = WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Bullet.HEIGHT = HEIGHT;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }
}
