package characters.gray;

import java.awt.*;

import characters.gray.GrayAttacking;
import characters.knight.Crouch;
import characters.knight.Idle;
import characters.knight.Jumping;
import characters.knight.Knight;
import characters.knight.KnightImageRenderer;
import characters.knight.Skill;
import characters.knight.Walking;
import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.SpriteShape;
import skill.Fire.Fire;
import skill.FireRing.FireRing;
import skill.Fireball.Fireball;
import skill.Fireball.SkillImageRenderer;
import skill.IceWall.IceWall;
import skill.Lightning.Lightning;
import skill.Lightningball.Lightningball;

import static fsm.FiniteStateMachine.Transition.from;
import static utils.ImageStateUtils.imageStatesFromFolder;
import static characters.knight.Knight.Event.*;

public class Gray extends Knight {

        public Gray(int damage, Point location, Direction face) {
                super(damage, location);
                String filepath = "assets/character/gray/";
                String skillpath = "assets/skill/";
                SpriteShape shape = new SpriteShape(new Dimension(146, 176), new Dimension(33, 38),
                                new Dimension(66, 135));
                SpriteShape crouchShape = new SpriteShape(new Dimension(146, 176), new Dimension(33, 88),
                                new Dimension(66, 65));

                FiniteStateMachine fsm = new FiniteStateMachine();

                ImageRenderer imageRenderer = new KnightImageRenderer(this);
                ImageRenderer skillRenderer = new SkillImageRenderer(this);

                State idle = new WaitingPerFrame(4,
                                new Idle(imageStatesFromFolder(filepath.concat("idle"), imageRenderer)));
                State walking = new WaitingPerFrame(2,
                                new Walking(this, imageStatesFromFolder(filepath.concat("walking"), imageRenderer)));
                State attacking = new WaitingPerFrame(10, new GrayAttacking(this, fsm,
                                imageStatesFromFolder(filepath.concat("attack"), imageRenderer)));
                State jumping = new WaitingPerFrame(4, new Jumping(this, fsm,
                                imageStatesFromFolder(filepath.concat("jumping"), imageRenderer)));
                State crouch = new WaitingPerFrame(4,
                                new Crouch(this, imageStatesFromFolder(filepath.concat("crouch"), imageRenderer)));
                State skill_1 = new WaitingPerFrame(7,
                                new Skill(this, fsm, imageStatesFromFolder(filepath.concat("cast"), imageRenderer)));
                State kicking = new WaitingPerFrame(3, new GrayKicking(this, fsm,
                                imageStatesFromFolder(filepath.concat("kick"), imageRenderer)));

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

                fsm.addTransition(from(idle).when(Event.SKILL_1).to(skill_1));
                fsm.addTransition(from(walking).when(Event.SKILL_1).to(skill_1));
                fsm.addTransition(from(crouch).when(Event.SKILL_1).to(skill_1));

                fsm.addTransition(from(idle).when(Event.KICK).to(kicking));
                fsm.addTransition(from(walking).when(Event.KICK).to(kicking));

                this.face = face;

                init(shape, crouchShape, fsm);
        }

        @Override
        public void skill(int id) {
                if (id == 1) {
                        fsm.trigger(SKILL_1);
                        if (fsm.currentState().toString().equals("Skill")) {
                                spell = new Fire(this, 1000);
                                world.addSprite(spell);
                        }
                } else if (id == 2) {
                        fsm.trigger(SKILL_2);
                        if (fsm.currentState().toString().equals("Skill")) {
                                spell = new Lightning(this, 1000);
                                world.addSprite(spell);
                        }
                } else if (id == 3) {
                        fsm.trigger(SKILL_3);
                        if (fsm.currentState().toString().equals("Skill")) {
                                spell = new IceWall(this, 1000);
                                world.addSprite(spell);
                        }
                }
        }
}
