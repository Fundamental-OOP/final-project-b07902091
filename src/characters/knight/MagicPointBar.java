package characters.knight;

import model.Sprite;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class MagicPointBar extends Sprite {
    private final int maxMp;
    private Sprite owner;
    private int mp;

    public MagicPointBar(int mp) {
        this.maxMp = this.mp = mp;
    }

    public void setOwner(Sprite owner) {
        this.owner = owner;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        int width = (int) (mp * owner.getRange().getWidth() / maxMp);
        g.setColor(Color.RED);
        g.fillRect(range.x, range.y, (int) owner.getRange().getWidth(), range.height);
        g.setColor(Color.BLUE);
        g.fillRect(range.x, range.y, width, range.height);
    }

    @Override
    public void onDamaged(Rectangle damageArea, int damage) {
        this.mp = Math.max(mp - damage, 0);
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(owner.getX(), owner.getY() - 15, (int) owner.getRange().getWidth(), 10);
    }

    @Override
    public Dimension getBodyOffset() {
        return new Dimension();
    }

    @Override
    public Dimension getBodySize() {
        return new Dimension();
    }

    public boolean isDead() {
        return mp <= 0;
    }
}
