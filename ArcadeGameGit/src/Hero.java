public class Hero extends Sprite {
    
    public Hero(double fallAccel, double x, double y) {
        super(fallAccel, x, y);
    }
    
    @Override
    public void spawning() {
    
    }
    
    @Override
    public boolean interactsWith(Sprite otherSprite) {
        if(otherSprite.getJoustHeight() > this.getJoustHeight()){
            otherSprite.death();
            return false;
        }
        return true;
    }
    
    @Override
    public double getJoustHeight() {
        return this.getY();
    }
    
    @Override
    public void death() {
        isDead = true;
    }
}
