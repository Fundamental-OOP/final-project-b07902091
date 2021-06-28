package views;

import controller.Game;
import controller.GameLoop;
import model.Direction;
import model.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GameView extends JFrame {
    public static final int HEIGHT = 500;
    public static final int WIDTH = 500;
    public static final int P1 = 1;
    public static final int P2 = 2;

    // detect when the key is pressed for P1
    private static int F_last_pressed = 0;

    // detect when the key is pressed for P2
    private static int K_last_pressed = 0;

    // when crouch is pressed for P1
    private static int S_last_pressed = 0;
    private static boolean S_double_pressed = false;
    // when crouch is pressed for P2
    private static int DOWN_last_pressed = 0;
    private static boolean DOWN_double_pressed = false;

    private final Canvas canvas = new Canvas();
    private final Game game;

    public GameView(Game game) throws HeadlessException {
        this.game = game;
        game.setView(canvas);
    }

    public void launch() {
        // GUI Stuff
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(canvas);
        setSize(WIDTH, HEIGHT);
        setContentPane(canvas);
        setVisible(true);

        // Keyboard listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_W:// player1
                        game.jumpKnight(P1);
                        break;
                    case KeyEvent.VK_S:
                        detect_S_double_pressed();
                        game.crouchKnight(P1);
                        break;
                    case KeyEvent.VK_A:
                        // A_last_pressed = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
                        game.moveKnight(P1, Direction.LEFT);
                        break;
                    case KeyEvent.VK_D:
                        // D_last_pressed = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
                        game.moveKnight(P1, Direction.RIGHT);
                        break;
                    case KeyEvent.VK_F:
                        F_last_pressed = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
                        if (F_last_pressed - S_last_pressed < 1 && S_double_pressed) {
                            System.out.println("First skill of P1 to the left");
                            game.skill_1(P1);
                        }
                        // System.out.println(F_last_pressed);
                        else {
                            game.attack(P1);
                        }
                        break;
                    // kick for P1
                    case KeyEvent.VK_G:
                        game.kick(P1);
                        break;
                    // kick for P1
                    // case KeyEvent.VK_E:
                    // game.change_hero(P1);
                    // break;
                    case KeyEvent.VK_UP:
                        game.jumpKnight(P2);
                        break;
                    case KeyEvent.VK_DOWN:
                        detect_DOWN_double_pressed();
                        game.crouchKnight(P2);
                        break;
                    case KeyEvent.VK_LEFT:
                        game.moveKnight(P2, Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.moveKnight(P2, Direction.RIGHT);
                        break;
                    case KeyEvent.VK_K:
                        K_last_pressed = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
                        if (K_last_pressed - DOWN_last_pressed < 1 && DOWN_double_pressed) {
                            System.out.println("First skill of P2 to the left");
                            game.skill_1(P2);
                        } else {
                            game.attack(P2);
                        }

                        break;
                    // kick for P2
                    case KeyEvent.VK_L:
                        game.kick(P2);
                        break;
                    // kick for P2
                    // case KeyEvent.VK_P:
                    // game.change_hero(P2);
                    // break;
                }
            }

            private void detect_S_double_pressed() {
                int temp = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
                if (temp - S_last_pressed < 1) {
                    S_double_pressed = true;
                } else {
                    S_double_pressed = false;
                }
                S_last_pressed = temp;
            }

            private void detect_DOWN_double_pressed() {
                int temp = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
                if (temp - DOWN_last_pressed < 1) {
                    DOWN_double_pressed = true;
                } else {
                    DOWN_double_pressed = false;
                }
                DOWN_last_pressed = temp;
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_W:
                        game.stopKnight(P1, Direction.UP);
                        break;
                    case KeyEvent.VK_S:
                        game.stopCrouchKnight(P1);
                        break;
                    case KeyEvent.VK_A:
                        game.stopKnight(P1, Direction.LEFT);
                        break;
                    case KeyEvent.VK_D:
                        game.stopKnight(P1, Direction.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        game.stopKnight(P2, Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        game.stopCrouchKnight(P2);
                        break;
                    case KeyEvent.VK_LEFT:
                        game.stopKnight(P2, Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.stopKnight(P2, Direction.RIGHT);
                        break;
                }
            }
        });
    }

    public static class Canvas extends JPanel implements GameLoop.View {
        private World world;

        @Override
        public void render(World world) {
            this.world = world;
            repaint(); // ask the JPanel to repaint, it will invoke paintComponent(g) after a while.
        }

        @Override
        protected void paintComponent(Graphics g /* paintbrush */) {
            super.paintComponent(g);
            // Now, let's paint
            g.setColor(Color.WHITE); // paint background with all white
            g.fillRect(0, 0, GameView.WIDTH, GameView.HEIGHT);

            world.render(g); // ask the world to paint itself and paint the sprites on the canvas
        }
    }
}
