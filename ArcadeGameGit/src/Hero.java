import java.awt.*;

/**
 * hero class that is made to be the player
 */
public class Hero extends Sprite{
    
    public int health = 3;
    
    /**
     * make a new Hero object that defaults to the UserMovement as input (arrow keys for control)
     * @param fallAccel falling acceleration for the hero
     * @param x starting x position
     * @param y starting y position
     */
    public Hero(double fallAccel, double x, double y) {
        super(fallAccel, x, y, false);
        color = Color.GREEN;
        addMover(new UserMovement(this));
    }
    
    @Override
    public Sprite spawning() {
        return null;
    }
    
    public int getHealth(){
        return health;
    }
}
