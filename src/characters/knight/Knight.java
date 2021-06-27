package characters.knight;

import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.ImageState;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.HealthPointSprite;
import model.SpriteShape;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static characters.knight.Knight.Event.*;
import static fsm.FiniteStateMachine.Transition.from;
import static model.Direction.LEFT;
import static utils.ImageStateUtils.imageStatesFromFolder;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Knight extends HealthPointSprite {
    public static final int KNIGHT_HP = 500;
    private SpriteShape shape;
    private SpriteShape crouchShape;
    private FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, CRUOCH, JUMP, STOP_CROUCH
    }

    public Knight(int damage, Point location) {
        super(KNIGHT_HP);
        this.damage = damage;
        this.location = location;
    }

    public void init(SpriteShape shape, SpriteShape crouchShape, FiniteStateMachine fsm) {
        this.shape = shape;
        this.fsm = fsm;
        this.crouchShape = crouchShape;
    }

    public void attack() {
        fsm.trigger(ATTACK);
    }

    public int getDamage() {
        return damage;
    }

    public void move(Direction direction) {
        if (direction == LEFT || direction == Direction.RIGHT) {
            face = direction;
        }
        if (!directions.contains(direction)) {
            this.directions.add(direction);
            fsm.trigger(WALK);
        }
    }

    public void triggerWalk() {
        if (!directions.isEmpty()) {
            fsm.trigger(WALK);
        }
    }

    public void jump() {
        fsm.trigger(JUMP);
    }

    public void crouch() {
        fsm.trigger(CRUOCH);
    }

    public void stopCrouch() {
        fsm.trigger(STOP_CROUCH);
        triggerWalk();
    }

    public void stop(Direction direction) {
        directions.remove(direction);
        if (directions.isEmpty()) {
            fsm.trigger(STOP);
        }
    }

    public void update() {
        fsm.update();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        fsm.render(g);
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public Rectangle getRange() {
        if (!fsm.currentState().toString().equals("Crouch"))
            return new Rectangle(location, shape.size);
        else
            return new Rectangle(location, crouchShape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        if (!fsm.currentState().toString().equals("Crouch"))
            return shape.bodyOffset;
        else
            return crouchShape.bodyOffset;
    }

    @Override
    public Dimension getBodySize() {
        if (!fsm.currentState().toString().equals("Crouch"))
            return shape.bodySize;
        else
            return crouchShape.bodySize;
    }
}
