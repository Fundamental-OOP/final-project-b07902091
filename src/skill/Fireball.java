package skill;

import java.awt.*;
import characters.knight.Knight;
import fsm.FiniteStateMachine;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.HealthPointSprite;
import model.SpriteShape;
import static fsm.FiniteStateMachine.Transition.from;
import static utils.ImageStateUtils.imageStatesFromFolder;
import static skill.Fireball.Event.*;

public class Fireball extends HealthPointSprite {
    public static final int FIREBALL_HP = 1;
    private Direction direction;
    private Knight caster;
    private SpriteShape shape;
    private FiniteStateMachine fsm;
    private int damage;

    public enum Event {
        DONE, HIT
    }

    public Fireball(Knight caster) {
        super(FIREBALL_HP);
        this.direction = caster.getFace();
        face = this.direction;
        this.caster = caster;
        this.location = new Point(caster.getLocation().x, caster.getLocation().y);
        this.shape = new SpriteShape(new Dimension(146, 176), new Dimension(86, 60), new Dimension(36, 55));
        this.fsm = new FiniteStateMachine();
        this.damage = 200;

        String filepath = "assets/skill/";
        SkillImageRenderer imageRenderer = new SkillImageRenderer(this);

        State casting = new WaitingPerFrame(3,
                new Casting(this, imageStatesFromFolder(filepath.concat("fireball/casting"), imageRenderer)));
        State flying = new WaitingPerFrame(4,
                new Flying(this, imageStatesFromFolder(filepath.concat("fireball/flying"), imageRenderer)));
        State triggered = new WaitingPerFrame(4,
                new Triggered(this, imageStatesFromFolder(filepath.concat("fireball/trigger"), imageRenderer)));

        fsm.setInitialState(casting);
        fsm.addTransition(from(casting).when(DONE).to(flying));
        fsm.addTransition(from(flying).when(HIT).to(triggered));
    }

    public void done() {
        fsm.trigger(DONE);
    }

    public void hit() {
        fsm.trigger(HIT);
    }

    public void update() {
        fsm.update();
    }

    @Override
    public void render(Graphics g) {
        // super.render(g);
        fsm.render(g);
        // Rectangle range = getRange();
        // g.setColor(Color.RED);
        // g.fillRect(range.x, range.y, (int) getRange().getWidth(), range.height);
        // fsm.render(g);
    }

    public Rectangle getRange() {
        return new Rectangle(location, shape.size);
    }

    public Dimension getBodyOffset() {
        return shape.bodyOffset;
    }

    public Dimension getBodySize() {
        return shape.bodySize;
    }

    public Knight getCaster() {
        return caster;
    }

    public int getDamage() {
        return damage;
    }

    public Direction getDirection() {
        return direction;
    }
}
