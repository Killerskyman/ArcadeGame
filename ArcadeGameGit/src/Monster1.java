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
    public void spawning() {
    
    }
    
    @Override
    public boolean interactsWith(Sprite otherSprite) {
        return true;
    }
    
    @Override
    public double getJoustHeight() {
        return 0;
    }
    
    @Override
    public void death() {
    
    }
}
