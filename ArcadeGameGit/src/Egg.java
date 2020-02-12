/**
 * class that is the egg
 * WIP
 */
public class Egg extends Sprite{
    
    public Egg(double fallAccel, double x, double y) {
        super(fallAccel, x, y);
    }
    
    @Override
    public Sprite spawning() {
        return null;
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
