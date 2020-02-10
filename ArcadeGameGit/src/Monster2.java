/**
 * monster 2 class WIP
 */
public class Monster2 extends Sprite {
    public Monster2(double fallAccel, double x, double y) {
        super(fallAccel, x, y);
    }
    
    @Override
    public void spawning() {
    
    }
    
    @Override
    public boolean interactsWith(Sprite otherSprite) {
        return false;
    }
    
    @Override
    public double getJoustHeight() {
        return 0;
    }
    
    @Override
    public void death() {
    
    }
}
