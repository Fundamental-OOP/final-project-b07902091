package characters.knight;

import fsm.ImageRenderer;
import fsm.ImageState;
import model.Direction;

import java.awt.*;
import java.util.List;

public class SkillImageRenderer implements ImageRenderer {
    protected Knight knight;

    public SkillImageRenderer(Knight knight) {
        this.knight = knight;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = knight.getFace();
        Rectangle range = knight.getRange();
        Rectangle body = knight.getBody();
        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
        g.setColor(Color.RED);
        g.drawRect(body.x, body.y, body.width, body.height);
    }
}
