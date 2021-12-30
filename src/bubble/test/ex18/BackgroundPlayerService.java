package bubble.test.ex18;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;

// 메인스레드 -> 키보드 이벤트를 처리
// 백그라운드에서 계속 관찰 -> 새로운 스레드
public class BackgroundPlayerService implements Runnable {

    private BufferedImage image;
    private Player player;
    private List<Bubble> bubbleList;

    //플레이어, 버블
    public BackgroundPlayerService(Player player) {
        this.player = player;
        this.bubbleList = player.getBubbleList();

        try {
            InputStream is;
            image = ImageIO.read(new File("images/backgroundMapService.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {

            // 1. 버블 충돌 체크
            for (int i = 0; i < bubbleList.size(); i++) {
                Bubble bubble = bubbleList.get(i);
                if (bubble.getState() == 1) {
                    if ((Math.abs(player.getX() - bubble.getX()) < 10) &&
                            (Math.abs(player.getY() - bubble.getY()) > 0 &&
                                    Math.abs(player.getY() - bubble.getY()) < 50)) {
                        System.out.println("적군 터치");
                        new Thread(()->{
                            bubble.clearBubbled();
                        }).start();
                        break;
                    }
                }
            }

            // 2. 벽 충돌 체크

            // 색상 확인
            Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
            Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
            // -2가 나오면 바닥이 흰색
            int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5) // -> 왼쪽 바닥
                    + image.getRGB(player.getX() + 40, player.getY() + 50 + 5); //-> 오른쪽 바닥

            // 바닥 충돌 확인
            if (bottomColor != -2) {
                // System.out.println("bottomColor : "+ bottomColor);
                // System.out.println("바닥에 있음");
                player.setDown(false);
            } else {
                if (!player.isUp() && !player.isDown()) {
                    player.down();
                }
            }

            // 외벽 충돌 확인
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                // System.out.println("왼쪽 벽에 충돌");
                player.setLeftWallCrash(true);
                player.setLeft(false);
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                // System.out.println("오른쪽 벽에 충돌");
                player.setRightWallCrash(true);
                player.setRight(false);
            } else {
                player.setLeftWallCrash(false);
                player.setRightWallCrash(false);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
