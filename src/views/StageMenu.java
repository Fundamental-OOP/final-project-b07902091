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

public class StageMenu extends JPanel {
    private JPanel parent;
    private Canvas canvas;

    public StageMenu(JPanel cards, Canvas canvas) {
        this.canvas = canvas;
        this.parent = cards;
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.blue);

        JLabel title = new JLabel("Select Stage"); // title
        title.setForeground(Color.gray);
        title.setFont(new Font("Times New Roman", Font.BOLD, 42));

        add(title);

        JLabel background1 = getBackgroundIcon("1");
        JLabel background2 = getBackgroundIcon("2");
        JLabel background3 = getBackgroundIcon("3");
        JLabel background4 = getBackgroundIcon("4");

        add(background1);

        add(background2);

        add(background3);

        add(background4);

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
            return label;
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

}
