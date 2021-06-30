package characters.knight;


import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class UltimatePointBar extends HealthPointBar {
    private int up_to_add = 50;
    public UltimatePointBar(int up) {
        super(up);
        this.hp = 0;
    }

    public void setUp(int up) {
        setHp(up);
    }

    public void addUp() {
        if(hp+up_to_add<maxHp){
            setHp(hp+up_to_add);
        }
        else{
            setHp(maxHp);
        }
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        int width = (int) (hp * owner.getRange().getWidth() / maxHp);
        g.setColor(Color.RED);
        g.fillRect(range.x, range.y, (int) owner.getRange().getWidth(), range.height);
        g.setColor(Color.ORANGE);
        g.fillRect(range.x, range.y, width, range.height);
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(owner.getX(), owner.getY() - 10, (int) owner.getRange().getWidth(), 10);
    }
}
