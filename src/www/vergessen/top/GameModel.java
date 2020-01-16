package www.vergessen.top;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);

    List<Bullet> bullets = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();
    List<Exploder> explodes = new ArrayList<>();

    public GameModel(){
        int initTankCount = PropertyMgr.instance().getInt("initTankCount");
        //初始化敌方坦克
        for(int i = 0; i < initTankCount ; i++){
            tanks.add(new Tank(30 + i * 70
                    ,100,Dir.DOWN,Group.BAD,this));
        }
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量" + bullets.size(),13,48);
        g.drawString("敌人的数量" + tanks.size(),13,68);
        g.setColor(color);
        myTank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }
        for(int i = 0; i < explodes.size(); i++){
            explodes.get(i).paint(g);
        }
        for(int i = 0; i < bullets.size() ; i++)
            for(int j = 0; j < tanks.size(); j++)
                bullets.get(i).collideWith(tanks.get(j));
    }

    public Tank getMainTank() {
        return myTank;
    }
}
