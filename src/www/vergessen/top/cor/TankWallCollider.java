package www.vergessen.top.cor;

import www.vergessen.top.*;

public class TankWallCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Tank && o2 instanceof Wall){
            Tank t = (Tank)o1;
            Wall w = (Wall)o2;
            if(t.getRectangle().intersects(w.rectangle)){
                if(t.getGroup() == Group.BAD){
                    t.setX(t.getOldX());
                    t.setY(t.getOldY());
                    t.setDir(Dir.getOppositeDir(t.getDir()));
                }
                if(t.getGroup() == Group.GOOD){
                    t.setMoving(false);
                }
            }
        }else if(o2 instanceof Tank && o1 instanceof Wall){
            return collide(o2, o1);
        }
        return true;
    }
}
