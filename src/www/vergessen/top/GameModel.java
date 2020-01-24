package www.vergessen.top;

import www.vergessen.top.cor.ColliderChain;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private static final GameModel INSTANCE = new GameModel();
    static {
        INSTANCE.init();
    }
    Tank myTank;

    ColliderChain colliderChain = new ColliderChain();
    private List<GameObject> gameObjects = new ArrayList<>();

    public static GameModel getInstance() {
        return INSTANCE;
    }

    private GameModel(){}

    private void init(){
        myTank = new Tank(200,400,Dir.DOWN,Group.GOOD);
//        add(myTank);
        int initTankCount = PropertyMgr.instance().getInt("initTankCount");
        //初始化敌方坦克
        for(int i = 0; i < initTankCount ; i++){
            add(new Tank(30 + i * 70,100,Dir.DOWN,Group.BAD));
        }

        //初始化墙
        add(new Wall(200,250,200,50));
        add(new Wall(650,250,200,50));
        add(new Wall(350,400,50,200));
        add(new Wall(650,400,50,200));
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
            colliderChain.collide(gameObjects.get(i),myTank);
        }
    }

    public Tank getMainTank() {
        return myTank;
    }

    public void save() {
        File f = new File("E:\\myProject\\tank\\src\\tank.data");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(myTank);
            oos.writeObject(gameObjects);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        File f = new File("E:\\myProject\\tank\\src\\tank.data");
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(f));
            myTank = (Tank) ois.readObject();
            gameObjects = (List<GameObject>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
