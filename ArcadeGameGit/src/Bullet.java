/**
 * class that is the bullet from monster2
 * WIP
 */
public class Bullet extends Sprite{
    
    public Bullet(double fallAccel, double x, double y) {
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
