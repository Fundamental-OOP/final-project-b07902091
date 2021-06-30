package views;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import characters.emily.Emily;
import characters.gray.Gray;
import characters.knight.Knight;
import media.AudioPlayer;
import model.Direction;

import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {
    private final int WIDTH = 1300;
    private final int HEIGHT = 1000;
    private Thread musicThread;
    private Image backgroundImage;
    private int width;
    private int height;
    private JPanel parent;
    private Canvas canvas;

    public MainMenu(JPanel cards, List<Knight> team1, List<Knight> team2, Canvas canvas) {
        this.canvas = canvas;
        this.parent = cards;
        setBackground(Color.blue);
        // setContentPane(canvas);
        setSize(WIDTH, HEIGHT);

        // setContentPane(canvas);
        String filepath = "assets/pic/Sun.png";
        setBackgroundImage(filepath);

        JButton start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ((CardLayout) parent.getLayout()).next(parent);
                canvas.startGame(0);
                ((CardLayout) parent.getLayout()).show(parent, "Card with GamePlay panel");
            }
        });
        add(start);

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

    @Override
    public void paint(Graphics g) {
        Image bufferImage = this.createImage(this.getSize().width, this.getSize().height);
        Graphics bufferGraphics = bufferImage.getGraphics();
        bufferGraphics.drawImage(backgroundImage, 85, 50, width / 2, height / 2, null);
        g.drawImage(bufferImage, 120, 100, this);
    }
}
