package views;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Timer;

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

        // String filepath = "assets/pic/Sun.png";
        // setBackgroundImage(filepath);
        team1.clear();
        team2.clear();

        JLabel emily1 = getKnightIcon("emily", team1, 1);
        JLabel gray1 = getKnightIcon("gray", team1, 1);

        JLabel emily2 = getKnightIcon("emily", team2, 2);
        JLabel gray2 = getKnightIcon("gray", team2, 2);

        // emily.setBorder(new EmptyBorder(20, 140, 20, 140));
        // gray.setBorder(new EmptyBorder(40, 140, 20, 140));

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

        JButton start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.startGame(0);
                ((CardLayout) parent.getLayout()).show(parent, "Card with GamePlay panel");
            }
        });

        add(start);
    }

    private JLabel getKnightIcon(String filepath, List<Knight> team, Integer teamNum) {
        try {
            String iconPath = "assets/character/" + filepath + "/icon/" + teamNum.toString() + ".png";
            String disalbledIconPath = "assets/character/" + filepath + "/icon/" + teamNum.toString()+"-disabled.png";

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

    private void setBackgroundImage(String filepath) {
        try {
            ImageIcon icon = new ImageIcon(new File(filepath).toURI().toURL());

            this.backgroundImage = icon.getImage();
            this.width = icon.getIconWidth();
            this.height = icon.getIconHeight();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // @Override
    // public void paint(Graphics g) {
    // Image bufferImage = this.createImage(this.getSize().width,
    // this.getSize().height);
    // Graphics bufferGraphics = bufferImage.getGraphics();
    // bufferGraphics.drawImage(backgroundImage, 85, 50, width / 2, height / 2,
    // null);
    // g.drawImage(bufferImage, 120, 100, this);
    // }
}
