package characters.emily;

import fsm.State;
import fsm.StateMachine;

import java.awt.*;
import java.util.List;

import characters.knight.Knight;
import characters.knight.Attacking;
/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class EmilyAttacking extends Attacking {

    public EmilyAttacking(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(knight, stateMachine, states);
    }

    @Override
    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(80, 15), // box offset x, y
                new Dimension(60, 150));// box width, box height
    }
}
