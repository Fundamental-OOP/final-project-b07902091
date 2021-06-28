package model;

import media.AudioPlayer;

import java.awt.*;

import characters.knight.HealthPointBar;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class MagicPointSprite extends Sprite {
    

    protected HealthPointBar mpBar;

    public MagicPointSprite(int mp) {
        this.mpBar = new MagicPointBar(mp);
        mpBar.setOwner(this);
    }

    @Override
    public void render(Graphics g) {
        mpBar.render(g);
    }
}
