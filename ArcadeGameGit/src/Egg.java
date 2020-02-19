import java.awt.*;

/**
 * class that is the egg
 * WIP
 */
public class Egg extends Sprite{
    
    private Sprite spriteToSpawn;
    
    /**
     * makes a new egg object that spawns the specified sprite
     * @param spriteToSpawn sprite to spawn
     * @param fallAccel how fast it falls
     * @param x position x
     * @param y position y
     */
    public Egg(Sprite spriteToSpawn, double fallAccel, double x, double y) {
        super(fallAccel, x, y, true);
        color = Color.YELLOW;
        spawnsSprite = true;
        this.spriteToSpawn = spriteToSpawn;
    }
    
    @Override
    public Sprite spawning() {
        spriteToSpawn.setX(getX());
        spriteToSpawn.setY(getY());
        isDead = true;//kms
        spriteToSpawn.isDead = false;
        return spriteToSpawn;
    }
    
    @Override
    public int spawnTiming() {
        return 100;
    }
    
    @Override
    public boolean interactsWith(Sprite otherSprite) {
        if(otherSprite.isFriendly) return true;
        else if(otherSprite.getJoustHeight() < getJoustHeight()){
            isDead = true;
            return false;
        }
        return true;
    }
    
    @Override
    public double getJoustHeight() {
        return Double.MAX_VALUE;
    }
    
    @Override
    public Sprite death() {
        Game.playerScore++;
        return super.death();
    }
}
