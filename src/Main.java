import controller.Game;
import media.AudioPlayer;
import model.Direction;
import model.HealthPointSprite;
import model.World;
import skill.Fireball.Flying;
import skill.IceWall.IceWallFlying;
import skill.Lightning.LightningFlying;
import views.GameView;
import java.awt.event.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import characters.emily.Emily;
import characters.emily.EmilyAttacking;
import characters.gray.Gray;
import characters.gray.GrayAttacking;
import characters.gray.GrayKicking;
import characters.knight.Attacking;
import characters.knight.Knight;
import characters.knight.KnightCollisionHandler;
import characters.knight.Walking;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import views.GameView.Canvas;
import static media.AudioPlayer.addAudioByFilePath;

/**
 * Demo route: Main, GameView, Game, GameLoop, World, Sprite, Knight,
 * FiniteStateMachine
 * 
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Main {
    JPanel cards;
    final static String INTRO_PANEL = "Card with Intro panel";
    final static String MENU_PANEL = "Card with Character Selection panel";
    final static String GAME_PANEL = "Card with GamePlay panel";

    public static void main(String[] args) {
        addAudio();
        Thread musicThread = AudioPlayer.playLoopMusic("assets/music/background.wav");
        GameView view = new GameView(); // view

        // TODO Intro Panel
        JPanel card1 = new JPanel();
        card1.setSize(GameView.WIDTH, GameView.HEIGHT);
        card1.setBackground(Color.white);

        // TODO Knight Selection Panel
        JPanel card2 = new JPanel();
        card2.setSize(GameView.WIDTH, GameView.HEIGHT);
        card2.setBackground(Color.blue);

        // No TODO
        Canvas canvas = new Canvas();

        // Main Card Layout
        JPanel cards = new JPanel(new CardLayout());
        cards.add(card1, INTRO_PANEL);
        cards.add(card2, MENU_PANEL);
        cards.add(canvas, GAME_PANEL);

        view.add(cards, BorderLayout.CENTER);
        view.setVisible(true);

        // noodle code just for change panel start ===========================
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonName = e.getActionCommand();
                if (buttonName.equals("start"))
                    ((CardLayout) cards.getLayout()).next(cards);
                if (buttonName.equals("start2")) {
                    List<Knight> team1 = new ArrayList<>();
                    List<Knight> team2 = new ArrayList<>();

                    startMenu(team1, team2);

                    startGame(view, team1, team2, 0, cards, canvas);
                }

            }
        };

        JButton start = new JButton("start");
        JButton start2 = new JButton("start2");
        start.addActionListener(actionListener);
        start2.addActionListener(actionListener);
        card1.add(start);
        card2.add(start2);
        // end============================================================

        // List<Knight> team1 = new ArrayList<>();
        // List<Knight> team2 = new ArrayList<>();

        // startMenu(team1, team2);

        // startGame(view, team1, team2, 0, cards, canvas);
    }

    // Start Introduction Page
    private static void startIntro() {
        // MainMenu menu = new MainMenu();
    }

    // Start Knights Selection Page
    private static void startMenu(List<Knight> team1, List<Knight> team2) {
        // initialization procedure
        Knight p1 = new Gray(100, new Point(300, 300), Direction.RIGHT);
        Knight p3 = new Emily(100, new Point(300, 300), Direction.RIGHT);
        team1.add(p1);
        team1.add(p3);
        p1.setTeam(1);
        p3.setTeam(1);

        Knight p2 = new Emily(150, new Point(700, 300), Direction.LEFT);
        Knight p4 = new Gray(150, new Point(700, 300), Direction.LEFT);
        team2.add(p2);
        team2.add(p4);
        p2.setTeam(2);
        p4.setTeam(2);
    }

    // Start Game
    private static void startGame(GameView view, List<Knight> team1, List<Knight> team2, int first_rival_id,
            JPanel cards, Canvas canvas) {
        World world = new World(new KnightCollisionHandler(), team1.get(first_rival_id), team2.get(first_rival_id)); // model
        Game game = new Game(world, team1, team2, first_rival_id); // controller
        game.setView(canvas);
        game.start(); // run the game and the game loop
        view.launchGame(game, cards); // launch the GUI

    }

    private static void addAudio() {
        addAudioByFilePath(EmilyAttacking.AUDIO_ATTACK, new File("assets/character/emily/audio/attack.wav"));
        addAudioByFilePath(GrayAttacking.AUDIO_ATTACK, new File("assets/character/gray/audio/attack.wav"));
        addAudioByFilePath(GrayKicking.AUDIO_KICK, new File("assets/character/gray/audio/kick.wav"));
        addAudioByFilePath(Attacking.AUDIO_MISS, new File("assets/character/knight/audio/miss.wav"));
        addAudioByFilePath(Emily.AUDIO_CAST, new File("assets/character/emily/audio/cast.wav"));
        addAudioByFilePath(Gray.AUDIO_CAST, new File("assets/character/gray/audio/cast.wav"));

        addAudioByFilePath(Emily.AUDIO_DEAD, new File("assets/character/emily/audio/dead.wav"));
        addAudioByFilePath(Gray.AUDIO_DEAD, new File("assets/character/gray/audio/dead.wav"));
        addAudioByFilePath(Emily.AUDIO_INJURED, new File("assets/character/emily/audio/injured.wav"));
        addAudioByFilePath(Gray.AUDIO_INJURED, new File("assets/character/gray/audio/injured.wav"));

        addAudioByFilePath(Flying.AUDIO_FIREBALL_HIT, new File("assets/skill/fireball/audio/trigger.wav"));
        addAudioByFilePath(IceWallFlying.AUDIO_ICEWALL_HIT, new File("assets/skill/iceWall/audio/trigger.wav"));
        addAudioByFilePath(LightningFlying.AUDIO_LIGHTNING_FLY, new File("assets/skill/lightning/audio/flying.wav"));

    }
}
