package controller;

import characters.knight.Knight;
import model.Direction;
import model.World;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Game extends GameLoop {
    private static Knight p1;
    private static Knight p2;
    private static Knight p3;
    private static Knight p4;
    private static Knight temp;
    private static World world;

    public Game(World world, Knight p1, Knight p2, Knight p3, Knight p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.temp = p1;
        this.world = world;
    }

    public void moveKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).move(direction);
    }

    public void changeKnight(int playerNumber){
        if(playerNumber == 1){
            if(!this.p3.isDead()){
                this.p1.stop(Direction.LEFT);
                this.p1.stop(Direction.RIGHT);
                this.p1.stopCrouch();
                this.temp = this.p1;
                this.p1 = this.p3;
                this.p3 = this.temp;
                this.world.removeSprite(this.p3); 
                this.world.addSprite(this.p1); 
            }
        }
        else if(playerNumber == 2){
            if(!this.p4.isDead()){
                this.p2.stop(Direction.LEFT);
                this.p2.stop(Direction.RIGHT);
                this.p2.stopCrouch();
                this.temp = this.p2;
                this.p2 = this.p4;
                this.p4 = this.temp;
                this.world.removeSprite(this.p4); 
                this.world.addSprite(this.p2); 
            }
        }
    }

    public void jumpKnight(int playerNumber){
        getPlayer(playerNumber).jump();
    }

    public void crouchKnight(int playerNumber){
        getPlayer(playerNumber).crouch();
    }
    public void stopCrouchKnight(int playerNumber){
        getPlayer(playerNumber).stopCrouch();
    }

    public void stopKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).stop(direction);
    }

    public void attack(int playerNumber) {
        getPlayer(playerNumber).attack();
    }

    public void kick(int playerNumber) {
        getPlayer(playerNumber).kick();
    }

    public void skill_1(int playerNumber) {
        getPlayer(playerNumber).skill_1();
    }

    public Knight getPlayer(int playerNumber) {
        return playerNumber == 1 ? p1 : p2;
    }

    @Override
    protected World getWorld() {
        return world;
    }

}
