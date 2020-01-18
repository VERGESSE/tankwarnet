package www.vergessen.top;

import java.awt.*;

public class Bullet extends GameObject {
    private static final int SPEED = 15;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    private Dir dir;
    private boolean living = true;
    private Group group = Group.BAD;

    public Rectangle rectangle = new Rectangle();

    public Bullet(int x, int y, Dir dir,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        GameModel.getInstance().add(this);
    }

    public void paint(Graphics g){
        if(!living){
            GameModel.getInstance().remove(this);
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

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
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

    public void die() {
        this.living = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
