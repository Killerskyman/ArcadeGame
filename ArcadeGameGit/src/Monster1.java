/**
 * monster 1 class WIP
 */
public class Monster1 extends Sprite {
    public Monster1(double fallAccel, double x, double y) {
        super(fallAccel, x, y);
    }
    
    public Monster1(double fallAccel, double x, double y, Hero player){
        this(fallAccel, x, y);
        this.addMover(new AIMovement(this, player));
    }
    
    @Override
    public Sprite spawning() {
        return null;
    }
    
    @Override
    public boolean interactsWith(Sprite otherSprite) {
        return true;
    }
    
    @Override
    public double getJoustHeight() {
        return this.getY();
    }
    
    @Override
    public void death() {
    
    }
}
