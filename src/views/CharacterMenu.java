package views;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import characters.emily.Emily;
import characters.gray.Gray;
import characters.knight.Knight;
import model.Direction;

import java.awt.event.*;

public class CharacterMenu extends JPanel {
    private JPanel parent;

    public CharacterMenu(JPanel cards, List<Knight> team1, List<Knight> team2) {
        this.parent = cards;
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.blue);

        JLabel title = new JLabel("Choose Your Hero"); // title
        title.setForeground(Color.gray);
        title.setFont(new Font("Times New Roman", Font.BOLD, 52));

        add(title);

        team1.clear();
        team2.clear();
        JLabel emily1 = getKnightIcon("emily", team1, 1);
        JLabel gray1 = getKnightIcon("gray", team1, 1);

        JLabel emily2 = getKnightIcon("emily", team2, 2);
        JLabel gray2 = getKnightIcon("gray", team2, 2);

        add(emily1);

        add(gray1);

        add(emily2);

        add(gray2);

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

        JButton next = new JButton("next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) parent.getLayout()).next(parent);
            }
        });
        add(next);
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
            return label;
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        return null;

    }

}
