package bubble.test.ex03;

import javax.swing.*;

// class Player -> new 가능한 애들 게임에 존재할 수 있음 ( 추상메서드를 가질 수 없다)
public class Player extends JLabel implements Moveable {

    // 위치 상태
    private int x;
    private int y;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private ImageIcon playerR, playerL;

    public Player() {
        initObject();
        initSetting();
    }

    private void initObject() {
        playerR = new ImageIcon("images/playerR.png");
        playerL = new ImageIcon("images/playerL.png");
    }

    private void initSetting() {
        x = 55;
        y = 535;

        left = false;
        right = false;
        up = false;
        down = false;

        setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);
    }

    // 이벤트 핸들러
    @Override
    public void left() {
        setIcon(playerL);
        x = x-10;
        setLocation(x,y);
    }

    @Override
    public void right() {
        try {
            // 이벤트 루프에 모든 task가 완료되어야만 repaint된다
            // right 5번 -> 10초 후 이동
            // 메인스레드만 있으면 2개의 key값을 전달할 수 없다 -> 하나만 전달됨

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setIcon(playerR);
        x = x+10;
        setLocation(x,y);
    }

    @Override
    public void up() {

    }

    @Override
    public void down() {

    }
}
