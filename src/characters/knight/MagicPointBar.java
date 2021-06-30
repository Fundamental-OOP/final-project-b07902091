package characters.knight;


import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class MagicPointBar extends HealthPointBar {
    private int mp_to_add = 5;
    public MagicPointBar(int mp) {
        super(mp);
        
    }

    public void setMp(int mp) {
        setHp(mp);
    }

    public void addMp() {
        if(hp+mp_to_add<maxHp){
            setHp(hp+mp_to_add);
        }
        else{
            setHp(maxHp);
        }
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        int width = (int) (hp * range.width / maxHp);
        g.setColor(Color.RED);
        g.fillRect(range.x, range.y, range.width, range.height);
        g.setColor(Color.BLUE);
        g.fillRect(range.x, range.y, width, range.height);
    }

    @Override
    public Rectangle getRange() {
        //return new Rectangle(owner.getX(), owner.getY() - 30, (int) owner.getRange().getWidth(), 10);
        if(owner.getTeam() == 1){
            return new Rectangle(10, 20, 400, 10);
        }
        else{
            return new Rectangle(890, 20, 400, 10);   
        }
        
    }
}
