package characters.emily;

import fsm.State;
import fsm.StateMachine;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

import characters.knight.Knight;
import characters.knight.Attacking;
/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class EmilyKicking extends Attacking {

    public EmilyKicking(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(knight, stateMachine, states);
        init();
    }

    private void init() {
        damagingStateNumbers = new HashSet<>(List.of(2));
    }
    @Override
    public void update() {
        if (knight.isAlive()) {
            super.update();
            if (damagingStateNumbers.contains(currentPosition)) {
                effectDamage();
            }
        }
    }

    @Override
    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(-20, 110), // box offset x, y
                new Dimension(170, 80));// box width, box height
    }
}
