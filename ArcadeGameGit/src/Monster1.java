import java.awt.*;

/**
 * monster 1 is a melee fighter
 */
public class Monster1 extends Sprite {
    
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
        super(fallAccel, x, y, true);
        color = Color.ORANGE;
        this.addMover(new AIMovement(this, player));
    }

    @Override
    public boolean jousts(Physics p) {
        if(p.isFriendly) return true;
        return super.jousts(p);
    }

    @Override
    public Sprite death() {
        super.death();
        return new Egg(this, 0.5, this.getX(), this.getY());
    }
}
