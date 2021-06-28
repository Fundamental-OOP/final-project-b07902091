package characters.knight;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class Skill_1 extends Sequence {
    private final Knight knight;
    private final StateMachine stateMachine;

    public Skill_1(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.knight = knight;
        this.stateMachine = stateMachine;
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
        knight.triggerWalk();
        knight.triggerSpell();
    }

}
