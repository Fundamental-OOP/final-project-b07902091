package characters.knight;

import fsm.FiniteStateMachine;
import model.Direction;
import model.MagicPointSprite;
import model.SpriteShape;
import skill.FireRing.FireRing;
import skill.Fireball.Fireball;
import skill.Lightningball.Lightningball;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static characters.knight.Knight.Event.*;
import static model.Direction.LEFT;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Knight extends MagicPointSprite {
    public static final int KNIGHT_HP = 500;
    public static final int KNIGHT_MP = 200;

    private SpriteShape shape;
    private SpriteShape crouchShape;
    protected FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;
    protected Fireball spell;

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, CRUOCH, JUMP, STOP_CROUCH, SKILL_1, SKILL_2, SKILL_3, KICK
    }

    public Knight(int damage, Point location) {
        super(KNIGHT_HP, KNIGHT_MP);
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

    public void kick() {
        fsm.trigger(KICK);
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

    public void skill(int id) {
        if (id == 1) {
            fsm.trigger(SKILL_1);
            if (fsm.currentState().toString().equals("Skill")) {
                spell = new Fireball(this, 1);
                world.addSprite(spell);
            }
        } else if (id == 2) {
            fsm.trigger(SKILL_2);
            if (fsm.currentState().toString().equals("Skill")) {
                spell = new Lightningball(this, 1);
                world.addSprite(spell);
            }
        } else if (id == 3) {
            fsm.trigger(SKILL_3);
            if (fsm.currentState().toString().equals("Skill")) {
                spell = new FireRing(this, 1);
                world.addSprite(spell);
            }
        }

    }

    public void triggerSpell() {
        spell.done();
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
