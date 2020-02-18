/**
 * monster 1 class WIP
 */
public class Monster1 extends Sprite {
    public Monster1(double fallAccel, double x, double y) {
        super(fallAccel, x, y, true);
    }
    
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
        if(p.getJoustHeight() > getJoustHeight()){
            isDead = true;
            return false;
        }
        return true;
    }

    @Override
    public double getJoustHeight() {
        return this.getY();
    }
    
    @Override
    public Sprite death() {
        super.death();
        return new Egg(0, this.getX(), this.getY());
    }
}
