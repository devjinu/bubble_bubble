package bubble.test.ex12;

public interface Moveable {
    public abstract void left();
    public abstract void right();
    public abstract void up();
    // 어댑터 패턴 x
    // default를 사용하면 인터페이스도 몸체가 있는 메서드를 만들 수 있다(다중 상속x)
    default public void down(){};
}
