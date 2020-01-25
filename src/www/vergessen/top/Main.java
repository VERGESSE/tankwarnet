package www.vergessen.top;

import www.vergessen.top.net.Client;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = TankFrame.INSTANCE;

        int initTankCount = PropertyMgr.getInt("initTankCount");
//        //初始化敌方坦克
//        for(int i = 0; i < initTankCount ; i++){
//            tf.badTank.add(new Tank(30 + i * 70
//                    ,100,Dir.DOWN,Group.BAD,tf));
//        }

//        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client client = new Client();
        client.connect();
    }
}
