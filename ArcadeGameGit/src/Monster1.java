public class Monster1 extends Sprite {
    public Monster1(double fallAccel, double x, double y) {
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
