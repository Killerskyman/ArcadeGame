import java.awt.geom.Point2D;

/**
 * monster 2 class WIP
 */
public class Monster2 extends Sprite {
    
    public Monster2(double fallAccel, double x, double y) {
        super(fallAccel, x, y, true);
        spawnsSprite = true;
    }
    
    public Monster2(double fallAccel, double x, double y, Hero player){
        this(fallAccel, x, y);
        this.addMover(new AIMovement(this, player));
    }
    
    @Override
    public Sprite spawning() {
        double x = ((AIMovement) getMover()).player.getX();
        double y = ((AIMovement) getMover()).player.getY();
        Bullet bullet = new Bullet(getX(), getY(), new Point2D.Double(x,y));
        return bullet;
    }
    
    @Override
    public int spawnTiming() {
        return 50;
    }
    
    @Override
    public boolean interactsWith(Sprite p) {
        if(p.isMonster) return true;
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
}
