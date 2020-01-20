package www.vergessen.top.cor;

import www.vergessen.top.*;

public class BulletTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Bullet && o2 instanceof Tank){
            Bullet b = (Bullet)o1;
            Tank t = (Tank)o2;

            // TODO 英雄机无敌挂(测试时)
            if(t.getGroup() == Group.GOOD)
                return true;

            //不打友军
            if(b.getGroup() == t.getGroup()) return true;
            if (b.rectangle.intersects(t.getRectangle())){
                t.die();
                b.die();
                return false;
            }
        }else if(o2 instanceof Bullet && o1 instanceof Tank){
            return collide(o2, o1);
        }
        return true;
    }

    //之前在Bullet中的碰撞检测代码
//    public boolean collideWith(Tank tank) {
//        if(this.group == tank.getGroup())return false;
//
//        Rectangle rectangle1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
//        Rectangle rectangle2 = new Rectangle(tank.getX(), tank.getY(), Tank.GOODWIDTH, Tank.GOODHEIGHT);
//        if (rectangle1.intersects(rectangle2)) {
//            this.die();
//            tank.die();
//            gameModel.add(new Explode(tank.getX() + Tank.GOODWIDTH/2- Explode.WIDTH/2,tank.getY()+Tank.GOODHEIGHT/2- Explode.HEIGHT/2,gameModel));
//            return true;
//        }
//        return false;
//    }
}
