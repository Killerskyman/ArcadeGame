import java.awt.*;
import java.awt.geom.Point2D;

/**
 * monster 2 is a monster that shoots projectiles at you
 */
public class Monster2 extends Monster1 {
    
    /**
     * makes a new monster with fall acceleration and starting position x,y.
     *
     * also automagically creates a simple but agressive AI movement with the player to track as player
     * @param fallAccel
     * @param x
     * @param y
     * @param player the Hero object player to track for the AI
     */
    public Monster2(double fallAccel, double x, double y, Hero player){
        super(fallAccel, x, y, player);
        color = Color.RED;
        spawnsSprite = true;
    }
    
    @Override
    public Sprite spawning() {
        double x = ((AIMovement) getMover()).player.getX();
        double y = ((AIMovement) getMover()).player.getY();
        Bullet bullet = new Bullet(getX()+(getWidth()/2), getY()+(getHeight()/2), new Point2D.Double(x,y));
        return bullet;
    }
    
    @Override
    public int spawnTiming() {
        return 50;
    }
}
