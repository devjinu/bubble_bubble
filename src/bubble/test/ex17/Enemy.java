package bubble.test.ex17;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

// class Player -> new 가능한 애들 게임에 존재할 수 있음 ( 추상메서드를 가질 수 없다)
@Getter
@Setter
public class Enemy extends JLabel implements Moveable {

    private BubbleFrame mContext;

    // 위치 상태
    private int x;
    private int y;

    // 플레이어의 방향
    private EnemyDirection enemyDirection;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    // 0 -> 살아 있는 상태, 1 -> 물방울에 갇힌 상태
    private int state;

    // 적군 속도 상태
    private final int SPEED = 3;
    private final int JUMPSPEED = 1; // up, down

    private ImageIcon enemyR, enemyL;

    public Enemy(BubbleFrame mContext) {
        this.mContext = mContext;
        initObject();
        initSetting();
      //  initBackgroundEnemyService();
    }

    private void initObject() {
        enemyR = new ImageIcon("images/enemyR.png");
        enemyL = new ImageIcon("images/enemyL.png");
    }

    private void initSetting() {
        x = 480;
        y = 178;

        left = false;
        right = false;
        up = false;
        down = false;

        state =0;

        enemyDirection = enemyDirection.RIGHT;

        setIcon(enemyR);
        setSize(50, 50);
        setLocation(x, y);
    }

    private void initBackgroundEnemyService() {
        // new Thread(new BackgroundEnemyService(this)).start();
    }

    // 이벤트 핸들러
    @Override
    public void left() {
        // System.out.println("left");
        enemyDirection = EnemyDirection.LEFT;
        left = true;
        new Thread(() -> {
            while (left) {
                setIcon(enemyL);
                x = x - SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void right() {
        // System.out.println("right");
        enemyDirection = enemyDirection.RIGHT;
        right = true;
        new Thread(() -> {
            while (right) {
                setIcon(enemyR);
                x = x + SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // left + up, right + up
    @Override
    public void up() {
        //  System.out.println("up");
        up = true;
        new Thread(() -> {
            for (int i = 0; i < 130 / JUMPSPEED; i++) {
                y = y - JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            up = false;
            down();
        }).start();
    }

    @Override
    public void down() {
        //  System.out.println("down");
        down = true;
        new Thread(() -> {
            while (down) {
                y = y + JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            down = false;
        }).start();
    }
}
