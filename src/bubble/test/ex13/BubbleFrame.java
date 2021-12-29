package bubble.test.ex13;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;

@Getter
@Setter
public class BubbleFrame extends JFrame {

    private BubbleFrame mContext = this;
    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame() {
        initObject();
        initSetting();
        initListner();
        setVisible(true);
    }

    private void initObject() {
        backgroundMap = new JLabel(new ImageIcon("images/backGroundMap.png"));
        setContentPane(backgroundMap);

        player = new Player();
        add(player);

    }

    private void initSetting() {
        setSize(1000, 640);
        getContentPane().setLayout(null); // absolute layout(자유롭게 그림을 그릴 수 있다)

        setLocationRelativeTo(null); //JFrame 가운데 배치
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌 때 JVM도 같이 종료
    }

    private void initListner() {
        // 키보드 클릭 이벤트 핸들러
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // System.out.println(e.getKeyCode());
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (!player.isLeft() && !player.isLeftWallCrash()) {
                            player.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!player.isRight() && !player.isRightWallCrash()) {
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (!player.isUp() && !player.isDown()) {
                            player.up();
                        }
                        break;
                    // 아래 방향키로 캐릭터가 이동할 일은 없음
                    /* case KeyEvent.VK_DOWN:
                        player.down();
                        break; */
                    case KeyEvent.VK_SPACE:
                        Bubble bubble = new Bubble(mContext);
                        add(bubble);
                        break;
                }
            }

            // 키보드 릴리즈 이벤트 핸들러

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        player.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.setRight(false);
                        break;
                }
            }
        });
    }

    public static void main(String[] args) {
        new BubbleFrame();
    }
}
