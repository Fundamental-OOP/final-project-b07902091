package model;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import characters.knight.Knight;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class World {
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final CollisionHandler collisionHandler;

    public World(CollisionHandler collisionHandler, Sprite... sprites) {
        this.collisionHandler = collisionHandler;
        addSprites(sprites);
    }

    public void update() {
        for (Sprite sprite : sprites) {
            correctBoundary(sprite);
            sprite.update();
        }
    }

    public void addSprites(Sprite... sprites) {
        stream(sprites).forEach(this::addSprite);
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
        sprite.setWorld(this);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
        sprite.setWorld(null);
    }

    public void move(Sprite from, Dimension offset) {
        Point originalLocation = new Point(from.getLocation());
        if (checkBoundary(from, offset)) {
            from.getLocation().translate(offset.width, offset.height);

            Rectangle body = from.getBody();
            // collision detection
            for (Sprite to : sprites) {
                if (to != from && body.intersects(to.getBody())) {
                    collisionHandler.handle(originalLocation, from, to);
                }
            }
        }

    }

    public Collection<Sprite> getSprites(Rectangle area) {
        return sprites.stream().filter(s -> area.intersects(s.getBody())).collect(toSet());
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    private boolean checkBoundary(Sprite from, Dimension offset) {
        if (from instanceof Knight) {
            int leftBorder = (int) (from.getLocation().getX() + from.getBodySize().getWidth());
            int rightBorder = (int) (from.getLocation().getX());
            if (leftBorder + offset.width < 0 || rightBorder + offset.width > 394)
                return false;
        }
        return true;
    }

    private void correctBoundary(Sprite sprite) {
        if (sprite.getLocation().getX() + sprite.getBodySize().getWidth() < 0) {
            sprite.setLocation(new Point(0, (int) sprite.getLocation().getY()));
        } else if (sprite.getLocation().getX() > 394) {
            sprite.setLocation(new Point(394, (int) sprite.getLocation().getY()));
        }
    }

    // Actually, directly couple your model with the class "java.awt.Graphics" is
    // not a good design
    // If you want to decouple them, create an interface that encapsulates the
    // variation of the Graphics.
    public void render(Graphics g) {
        for (Sprite sprite : sprites) {
            sprite.render(g);
        }
    }
}
