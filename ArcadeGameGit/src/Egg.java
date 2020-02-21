import java.awt.*;

/**
 * class that is the egg, spawns monsters after 100 gameTicks
 */
public class Egg extends Sprite{
    
    private static final int SPAWNCYCLES = 100;
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
        return SPAWNCYCLES;
    }

    @Override
    public boolean jousts(Physics p) {
        if(p.isFriendly) return true;
        return super.jousts(p);
    }

    @Override
    public double getJoustHeight() {
        return Double.MAX_VALUE;
    }
}
