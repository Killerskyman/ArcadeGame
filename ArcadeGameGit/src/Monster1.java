import java.awt.*;

/**
 * monster 1 is a melee fighter
 */
public class Monster1 extends Sprite {
    
    /**
     * makes a new monster with fall acceleration and starting position x,y
     * @param fallAccel
     * @param x
     * @param y
     */
    public Monster1(double fallAccel, double x, double y) {
        super(fallAccel, x, y, true);
        color = Color.ORANGE;
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
    public Monster1(double fallAccel, double x, double y, Hero player){
        this(fallAccel, x, y);
        isMonster = true;
        this.addMover(new AIMovement(this, player));
    }
    
    @Override
    public Sprite spawning() {
        return null;
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
        return new Egg(this, 0.5, this.getX(), this.getY());
    }
}
