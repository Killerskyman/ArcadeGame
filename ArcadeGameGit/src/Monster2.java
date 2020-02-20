import java.awt.*;
import java.awt.geom.Point2D;

/**
 * monster 2 is a monster that shoots projectiles at you
 */
public class Monster2 extends Sprite {
    
    /**
     * makes a new monster with fall acceleration and starting position x,y
     * @param fallAccel
     * @param x
     * @param y
     */
    public Monster2(double fallAccel, double x, double y) {
        super(fallAccel, x, y, true);
        color = Color.RED;
        isMonster = true;
        spawnsSprite = true;
    }
    
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
        this(fallAccel, x, y);
        isMonster = true;
        spawnsSprite = true;
        this.addMover(new AIMovement(this, player));
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
    
    @Override
    public boolean interactsWith(Sprite p) {
        if(p.isFriendly) return true;
        else if(p.getJoustHeight() < getJoustHeight()){
            isDead = true;
            return false;
        }
        return true;
    }
    
//    @Override
//    public boolean physicsCollision(Physics p, boolean[] pointOtherPhysics) {
//        boolean output = true;
//        if(!p.isSprite()) {
//            output = super.physicsCollision(p, pointOtherPhysics);
//        }else{
//            if(((Sprite) p).isFriendly){
//                return true;
//            }else{
//                output = super.physicsCollision(p, pointOtherPhysics);
//            }
//        }
//        return output;
//    }
    
    @Override
    public double getJoustHeight() {
        return this.getY();
    }
    
    @Override
    public Sprite death() {
        super.death();
        return new Egg(this,0.5, getX(), getY());
    }
}
