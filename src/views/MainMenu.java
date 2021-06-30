package views;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import characters.emily.Emily;
import characters.gray.Gray;
import characters.knight.Knight;
import media.AudioPlayer;
import model.Direction;

import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {
    private Image backgroundImage;
    private int width;
    private int height;
    private JPanel parent;
    private Canvas canvas;

    public MainMenu(JPanel cards, List<Knight> team1, List<Knight> team2, Canvas canvas) {
        this.canvas = canvas;
        this.parent = cards;
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.blue);

        team1.clear();
        team2.clear();

        JLabel emily1 = getKnightIcon("emily", team1, 1);
        JLabel gray1 = getKnightIcon("gray", team1, 1);

        JLabel emily2 = getKnightIcon("emily", team2, 2);
        JLabel gray2 = getKnightIcon("gray", team2, 2);

        // emily.setBorder(new EmptyBorder(20, 140, 20, 140));
        // gray.setBorder(new EmptyBorder(40, 140, 20, 140));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent evt) {
                // stop();
            }

            @Override
            public void componentShown(ComponentEvent evt) {
                requestFocus();
                team1.clear();
                team2.clear();
                emily1.setEnabled(true);
                emily2.setEnabled(true);
                gray1.setEnabled(true);
                gray2.setEnabled(true);
            }
        });

        JButton start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.startGame(0);
                ((CardLayout) parent.getLayout()).show(parent, "Card with GamePlay panel");
            }
        });

        add(start);

        JLabel background1 = getBackgroundIcon("1");
        JLabel background2 = getBackgroundIcon("2");
        JLabel background3 = getBackgroundIcon("3");
        JLabel background4 = getBackgroundIcon("4");

    }

    private JLabel getBackgroundIcon(String filename) {
        try {
            String iconPath = "assets/background/gamescene/" + filename + ".png";
            Image img = ImageIO.read(new File(iconPath));
            Image dimg = img.getScaledInstance(260, 160, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(dimg));
            label.setSize(160, 260);
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    canvas.setBackgroundImage(iconPath);
                }
            });
            add(label);
            return label;
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private JLabel getKnightIcon(String filepath, List<Knight> team, Integer teamNum) {
        try {
            String iconPath = "assets/character/" + filepath + "/icon/" + teamNum.toString() + ".png";
            String disalbledIconPath = "assets/character/" + filepath + "/icon/" + teamNum.toString() + "-disabled.png";

            JLabel label = new JLabel(new ImageIcon(new File(iconPath).toURI().toURL()));
            label.setDisabledIcon(new ImageIcon(new File(disalbledIconPath).toURI().toURL()));
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    Point knightLocation = (teamNum == 1) ? new Point(300, 300) : new Point(700, 300);
                    Direction direction = (teamNum == 1) ? Direction.RIGHT : Direction.LEFT;

                    System.out.println("Team " + teamNum + " selects: " + filepath);
                    if (filepath.equals("emily")
                            && team.stream().noneMatch(knight -> knight.toString().equals("Emily"))) {
                        Knight emily = new Emily(100, knightLocation, direction);
                        team.add(emily);
                        emily.setTeam(teamNum);
                        e.getComponent().setEnabled(false);
                    } else if (filepath.equals("gray")
                            && team.stream().noneMatch(knight -> knight.toString().equals("Gray"))) {
                        Knight gray = new Gray(100, knightLocation, direction);
                        team.add(gray);
                        gray.setTeam(teamNum);
                        e.getComponent().setEnabled(false);
                    }
                }
            });
            add(label);
            return label;
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return null;

    }

}
