package characters.knight;

import fsm.State;
import fsm.StateMachine;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class Skill_1 extends Attacking {
    List<? extends State> castStates;

    public Skill_1(Knight knight, StateMachine stateMachine, List<? extends State> states,
            List<? extends State> castStates) {
        super(knight, stateMachine, states);
        this.castStates = castStates;
        init();
    }

    private void init() {
        damagingStateNumbers = new HashSet<>(List.of(4));
    }

    @Override
    public void render(Graphics g) {
        castStates.get(currentPosition).render(g);
        super.render(g);
        Rectangle damageArea = damageArea();
        g.setColor(Color.BLUE);
        g.drawRect(damageArea.x, damageArea.y, damageArea.width, damageArea.height);
    }

    @Override
    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(80, 15), // box offset x, y
                new Dimension(1000, 150));// box width, box height
    }

}
