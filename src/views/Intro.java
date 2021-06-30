package views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
public class Intro extends JPanel {
    private JPanel parent;

    public Intro(JPanel cards) {
        this.parent = cards;

        setSize(GameView.WIDTH, GameView.HEIGHT);
        setBackground(Color.blue);

        JButton start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) parent.getLayout()).next(parent);
            }
        });
        add(start);
    }
}
