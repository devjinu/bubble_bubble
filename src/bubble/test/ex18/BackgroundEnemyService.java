package bubble.test.ex18;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;


public class BackgroundEnemyService implements Runnable {

    private BufferedImage image;
    private Enemy enemy;


    //플레이어, 버블
    public BackgroundEnemyService(Enemy enemy) {
        this.enemy = enemy;

        try {
            InputStream is;
            image = ImageIO.read(new File("images/backgroundMapService.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (enemy.getState() == 0) {

            // 색상 확인
            Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25));
            Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
            // -2가 나오면 바닥이 흰색
            int bottomColor = image.getRGB(enemy.getX() + 10, enemy.getY() + 50 + 5) // -> 왼쪽 바닥
                    + image.getRGB(enemy.getX() + 40, enemy.getY() + 50 + 5); //-> 오른쪽 바닥

            // 바닥 충돌 확인
            if (bottomColor != -2) {
                // System.out.println("bottomColor : "+ bottomColor);
                // System.out.println("바닥에 있음");
                enemy.setDown(false);
            } else {
                if (!enemy.isUp() && !enemy.isDown()) {
                    enemy.down();
                }
            }

            // 외벽 충돌 확인
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                enemy.setLeft(false);
                if (!enemy.isRight()) {
                    enemy.right();
                }
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                enemy.setRight(false);
                if (!enemy.isLeft()) {
                    enemy.left();
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
