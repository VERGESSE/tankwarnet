package www.vergessen.top;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
    private static final ResourceMgr resourceMgr = new ResourceMgr();
    public static BufferedImage goodTankL, goodTankU, goodTankR, goodTankD, goodTankLU,goodTankLD,goodTankRD,goodTankRU;
    public static BufferedImage badTankL, badTankU, badTankR, badTankD,badTankLD, badTankLU, badTankRD, badTankRU;
    public static BufferedImage bulletL, bulletU, bulletR, bulletD,bulletLU, bulletRU, bulletRD, bulletLD;
    public static BufferedImage[] explodes = new BufferedImage[16];

    private ResourceMgr(){}

    public static ResourceMgr getInstance(){
        return resourceMgr;
    }

    static {
        try {
            goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodTankL = ImageUtil.rotateImage(goodTankU, -90);
            goodTankR = ImageUtil.rotateImage(goodTankU, 90);
            goodTankD = ImageUtil.rotateImage(goodTankU, 180);
            goodTankLU = ImageUtil.rotateImage(goodTankU, -45);
            goodTankRU = ImageUtil.rotateImage(goodTankU, 45);
            goodTankLD = ImageUtil.rotateImage(goodTankU, -135);
            goodTankRD = ImageUtil.rotateImage(goodTankU, 135);

            badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            badTankL = ImageUtil.rotateImage(badTankU, -90);
            badTankR = ImageUtil.rotateImage(badTankU, 90);
            badTankD = ImageUtil.rotateImage(badTankU, 180);
            badTankLU = ImageUtil.rotateImage(badTankU, -45);
            badTankRU = ImageUtil.rotateImage(badTankU, 45);
            badTankLD = ImageUtil.rotateImage(badTankU, -135);
            badTankRD = ImageUtil.rotateImage(badTankU, 135);

            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletL = ImageUtil.rotateImage(bulletU, -90);
            bulletR = ImageUtil.rotateImage(bulletU, 90);
            bulletD = ImageUtil.rotateImage(bulletU, 180);
            bulletLU = ImageUtil.rotateImage(bulletU, -45);
            bulletRU = ImageUtil.rotateImage(bulletU, 45);
            bulletLD = ImageUtil.rotateImage(bulletU, -135);
            bulletRD = ImageUtil.rotateImage(bulletU, 135);

            for(int i=0; i<16; i++)
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i+1) + ".gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0;i < 100;i++){
            new Thread(()->{
                System.out.println(ResourceMgr.badTankU.hashCode());
            }).start();
        }
    }
}
