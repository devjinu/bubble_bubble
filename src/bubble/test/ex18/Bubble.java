package bubble.test.ex18;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable {

    // 의존성 컴포지션
    private Player player;
    private BackgroundBubbleService backgroundBubbleService;
    private Enemy enemy;
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
        this.enemy = mContext.getEnemy();
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

            if ((Math.abs(x - enemy.getX()) < 10) &&
                    (Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50)) {

                if (enemy.getState() == 0) {
                    attack();
                    break;
                }

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
            if ((Math.abs(x - enemy.getX()) < 10) &&
                    (Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50)) {

                if (enemy.getState() == 0) {
                    attack();
                    break;
                }

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
                if (state == 0) {
                    // 기본 물방울
                    Thread.sleep(1);
                } else {
                    // 적을 가둔 물방울
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (state == 0) {
            // 천장에 버블이 도착하고나서 3초 후에 메모리에서 소멸
            clearBubble();
        }
    }

    @Override
    public void attack() {
        state = 1;
        enemy.setState(1);
        setIcon(bubbled);
        // 메모리에서 사라지게 한다. (가비지 컬렉션 -> 즉시 발동x)
        mContext.remove(enemy);
        // 화면 갱신
        mContext.repaint();
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

    public void clearBubbled() {
        new Thread(() -> {
            System.out.println("clearBubbled");
            try {
                up = false;
                setIcon(bomb);
                Thread.sleep(1000);
                mContext.remove(this);
                mContext.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }


}
