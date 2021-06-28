package characters.gray;

import java.awt.*;

import characters.gray.GrayAttacking;
import characters.knight.Crouch;
import characters.knight.Idle;
import characters.knight.Jumping;
import characters.knight.Knight;
import characters.knight.KnightImageRenderer;
import characters.knight.Walking;
import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.SpriteShape;

import static fsm.FiniteStateMachine.Transition.from;
import static utils.ImageStateUtils.imageStatesFromFolder;

public class Gray extends Knight {

        public Gray(int damage, Point location, Direction face) {
                super(damage, location);
                String filepath = "assets/character/gray/";
                SpriteShape shape = new SpriteShape(new Dimension(146, 176), new Dimension(33, 38),
                                new Dimension(66, 135));
                SpriteShape crouchShape = new SpriteShape(new Dimension(146, 176), new Dimension(33, 88),
                                new Dimension(66, 65));

                FiniteStateMachine fsm = new FiniteStateMachine();

                ImageRenderer imageRenderer = new KnightImageRenderer(this);

                State idle = new WaitingPerFrame(4,
                                new Idle(imageStatesFromFolder(filepath.concat("idle"), imageRenderer)));
                State walking = new WaitingPerFrame(2,
                                new Walking(this, imageStatesFromFolder(filepath.concat("walking"), imageRenderer)));
                State attacking = new WaitingPerFrame(3, new GrayAttacking(this, fsm,
                                imageStatesFromFolder(filepath.concat("attack"), imageRenderer)));
                State jumping = new WaitingPerFrame(4, new Jumping(this, fsm,
                                imageStatesFromFolder(filepath.concat("jumping"), imageRenderer)));
                State crouch = new WaitingPerFrame(4,
                                new Crouch(this, imageStatesFromFolder(filepath.concat("crouch"), imageRenderer)));

                fsm.setInitialState(idle);
                fsm.addTransition(from(idle).when(Event.WALK).to(walking));
                fsm.addTransition(from(walking).when(Event.STOP).to(idle));
                fsm.addTransition(from(idle).when(Event.ATTACK).to(attacking));
                fsm.addTransition(from(walking).when(Event.ATTACK).to(attacking));

                fsm.addTransition(from(walking).when(Event.JUMP).to(jumping));
                fsm.addTransition(from(idle).when(Event.JUMP).to(jumping));

                fsm.addTransition(from(idle).when(Event.CRUOCH).to(crouch));
                fsm.addTransition(from(walking).when(Event.CRUOCH).to(crouch));
                fsm.addTransition(from(crouch).when(Event.STOP_CROUCH).to(idle));

                this.face = face;

                init(shape, crouchShape, fsm);
        }

}
