package bubble.test.ex10;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Bubble extends JLabel {

    // 의존성 컴포지션
    private Player player;

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

    public Bubble(Player player){
        this.player = player;
        initObject();
        initSetting();
    }

    private void initObject(){
        bubble = new ImageIcon("images/bubble.png");
        bubbled = new ImageIcon("images/bubbled.png");
        bomb = new ImageIcon("images/bomb.png");
    }
    private void initSetting(){
        up = false;
        left= false;
        right = false;

        this.x = player.getX();
        this.y = player.getY();

        setIcon(bubble);
        setSize(50,50);

        state = 0;

    }

}
