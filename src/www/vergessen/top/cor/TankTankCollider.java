package www.vergessen.top.cor;

import www.vergessen.top.Dir;
import www.vergessen.top.GameObject;
import www.vergessen.top.Group;
import www.vergessen.top.Tank;

public class TankTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Tank && o2 instanceof Tank) {
            Tank t1 = (Tank) o1;
            Tank t2 = (Tank) o2;
            if (t1.getRectangle().intersects(t2.getRectangle())) {
                if (t1.getGroup() == t2.getGroup() && t1.getGroup() == Group.BAD) {
                    t1.setX(t1.getOldX());
                    t1.setY(t1.getOldY());
                    t2.setX(t2.getOldX());
                    t2.setY(t2.getOldY());
                    Dir dir = t1.getDir();
                    t1.setDir(t2.getDir());
                    t2.setDir(dir);
                }
                if(t1.getGroup() != t2.getGroup()){
                    // TODO 英雄机无敌(测试时)
                    if(t1.getGroup() != Group.GOOD)
                        t1.die();
                    if(t2.getGroup() != Group.GOOD)
                        t2.die();
                    return false;
                }
            }
        }
        return true;
    }
}
