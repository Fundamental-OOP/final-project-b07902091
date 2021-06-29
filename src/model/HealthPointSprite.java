package model;

import media.AudioPlayer;

import java.awt.*;

import characters.knight.HealthPointBar;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class HealthPointSprite extends Sprite {
    public static final String AUDIO_DIE = "Die";

    protected HealthPointBar hpBar;
    //protected int team;
    public HealthPointSprite(int hp,int team) {
        super(team);
        //this.team = team;
        this.hpBar = new HealthPointBar(hp);
        hpBar.setOwner(this);

    }

    @Override
    public void onDamaged(Rectangle damageArea, int damage) {
        hpBar.onDamaged(damageArea, damage);
        if (hpBar.isDead()) {
            //this.KNIGHT_HP = 0; //
            //System.out.println(this.team); 
            if(this.team ==1){
                world.removeSprite(this,1);
            }
            else if(this.team ==2){
                world.removeSprite(this,2);
            }
            else{
                world.removeSprite(this);
            }
            
            // AudioPlayer.playSounds(AUDIO_DIE);
        }
    }

    @Override
    public void render(Graphics g) {
        hpBar.render(g);
    }
}
