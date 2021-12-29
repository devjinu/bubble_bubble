package bubble.test.ex15;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable {

    // 의존성 컴포지션
    private Player player;
    private BackgroundBubbleService backgroundBubbleService;
    private BubbleFrame mContext;

    // 위치 상태
    private int x;
    private int y;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;

    // 적을 맞춘 상태
    private int state; // 0(물방울), 1(적을 가둔 물방울)

    private ImageIcon bubble; // 일반 물방울
    private ImageIcon bubbled; // 적을 가둔 물방울
    private ImageIcon bomb; // 물방울이 터진 상태

    public Bubble(BubbleFrame mContext) {
        this.mContext = mContext;
        this.player = mContext.getPlayer();
        initObject();
        initSetting();
    }

    private void initObject() {
        bubble = new ImageIcon("images/bubble.png");
        bubbled = new ImageIcon("images/bubbled.png");
        bomb = new ImageIcon("images/bomb.png");

        backgroundBubbleService = new BackgroundBubbleService(this);

    }

    private void initSetting() {
        up = false;
        left = false;
        right = false;

        this.x = player.getX();
        this.y = player.getY();

        setIcon(bubble);
        setSize(50, 50);

        state = 0;

    }

    @Override
    public void left() {
        left = true;
        for (int i = 0; i < 400; i++) {
            x--;
            setLocation(x, y);
            if (backgroundBubbleService.leftWall()) {
                left = false;
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        up();
    }

    @Override
    public void right() {
        right = true;
        for (int i = 0; i < 400; i++) {
            x++;
            setLocation(x, y);
            if (backgroundBubbleService.rightWall()) {
                right = false;
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        up();
    }

    @Override
    public void up() {
        up = true;
        while (up) {
            y--;
            setLocation(x, y);
            if (backgroundBubbleService.topWall()) {
                up = false;
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 천장에 버블이 도착하고나서 3초 후에 메모리에서 소멸
        clearBubble();
    }

    private void clearBubble() {
        try {
            Thread.sleep(3000);
            setIcon(bomb);
            Thread.sleep(500);
            // BubbleFrame의 bubble이 메모리에서 소멸된다.
            mContext.remove(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
