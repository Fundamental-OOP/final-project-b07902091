package model;

import media.AudioPlayer;

import java.awt.*;

import characters.knight.HealthPointBar;
import characters.knight.MagicPointBar;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class HealthPointSprite extends Sprite {
    public static final String AUDIO_DIE = "Die";

    protected HealthPointBar hpBar;
    protected MagicPointBar mpBar;

    public HealthPointSprite(int hp,int mp) {
        this.hpBar = new HealthPointBar(hp);
        hpBar.setOwner(this);
        this.mpBar = new MagicPointBar(mp);
        mpBar.setOwner(this);
    }

    @Override
    public void onDamaged(Rectangle damageArea, int damage) {
        hpBar.onDamaged(damageArea, damage);
        //mpBar.onDamaged(damageArea, damage);
        if (hpBar.isDead()) {
            world.removeSprite(this);
            AudioPlayer.playSounds(AUDIO_DIE);
        }
    }

    @Override
    public void render(Graphics g) {
        hpBar.render(g);
        mpBar.render(g);
    }
}
