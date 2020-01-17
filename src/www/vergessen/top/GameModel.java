package www.vergessen.top;

import www.vergessen.top.cor.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);

//    List<Bullet> bullets = new ArrayList<>();
//    List<Tank> tanks = new ArrayList<>();
//    List<Explode> explodes = new ArrayList<>();      //加入调停者，，游戏物体抽象类GameObject
    ColliderChain colliderChain = new ColliderChain();
    private List<GameObject> gameObjects = new ArrayList<>();

    public GameModel(){
        int initTankCount = PropertyMgr.instance().getInt("initTankCount");
        //初始化敌方坦克
        for(int i = 0; i < initTankCount ; i++){
            add(new Tank(30 + i * 70
                    ,100,Dir.DOWN,Group.BAD,this));
        }
    }

    public void add(GameObject gameObject){
        this.gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject){
        this.gameObjects.remove(gameObject);
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("子弹的数量" + bullets.size(),13,48);
//        g.drawString("敌人的数量" + tanks.size(),13,68);
        g.setColor(color);
        myTank.paint(g);
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);
        }

        //互相碰撞
        for(int i = 0; i < gameObjects.size(); i++){
            for (int j = i + 1; j < gameObjects.size(); j++){
                GameObject o1 = gameObjects.get(i);
                GameObject o2 = gameObjects.get(j);
                colliderChain.collide(o1,o2);
            }
        }

//        for(int i = 0; i < bullets.size() ; i++)
//            for(int j = 0; j < tanks.size(); j++)
//                bullets.get(i).collideWith(tanks.get(j));
    }

    public Tank getMainTank() {
        return myTank;
    }
}
