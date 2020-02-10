import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * defines what a movable object that interacts with others of itself should have
 */
public abstract class Sprite extends Physics {

    private static final double PERCENTMOVEPHYSICS = 0.99; //how fast it moves from other objects colliding with it
    private Movement mover; //how it should be moved
    public boolean isDead = false; //whether it should be deleted
    
    /**
     * creates a sprite with following characteristics
     * @param fallAccel how fast it falls
     * @param x starting x
     * @param y starting y
     */
    public Sprite(double fallAccel, double x, double y) {
        super(fallAccel, x, y);
    }
    
    /**
     * coordinates how it should move when it collides with object p, autodetects interaction with another sprite object
     * @param p physics object it collided with
     * @param pointOtherPhysics output of Physics.doesCollideWith(p)
     * @return whether it should fall or not
     */
    @Override
    public boolean physicsCollision(Physics p, boolean[] pointOtherPhysics) {
        boolean continuePhysics = true;
        if(p.isSprite()){//check to see if the other object is a sprite and interact with it
            continuePhysics = this.interactsWith((Sprite) p);
        }
        if(!continuePhysics) return getFalling();
        boolean shouldFall = true;
        if(pointOtherPhysics[0] && pointOtherPhysics[1])setY(getY()+PERCENTMOVEPHYSICS*(p.getLowerY()-getY()));
        else if(pointOtherPhysics[1] && pointOtherPhysics[2])setX(getX()-PERCENTMOVEPHYSICS*(p.getX()-getRightX()));
        else if(pointOtherPhysics[2] && pointOtherPhysics[3]){
            setY(getY()-PERCENTMOVEPHYSICS*(getLowerY()-p.getY()));
            shouldFall = false;
        }
        else if(pointOtherPhysics[3] && pointOtherPhysics[0])setX(getX()+PERCENTMOVEPHYSICS*(p.getRightX()-getX()));
        else {
            if(pointOtherPhysics[0]) {
                double deltaX = PERCENTMOVEPHYSICS * (p.getRightX() - this.getX());
                double deltaY = PERCENTMOVEPHYSICS * (p.getLowerY() - this.getY());
                if(deltaX < deltaY){
                    this.setX(getX() + deltaX);
                }else{
                    this.setY(getY() + deltaY);
                }
            }
            if(pointOtherPhysics[1]) {
                double deltaX = PERCENTMOVEPHYSICS * (this.getRightX() - p.getX());
                double deltaY = PERCENTMOVEPHYSICS * (p.getLowerY() - this.getY());
                if(deltaX < deltaY){
                    this.setX(getX() - deltaX);
                }else{
                    setY(getY() + deltaY);
                }
            }
            if(pointOtherPhysics[2]) {
                double deltaX = PERCENTMOVEPHYSICS * (this.getRightX() - p.getX());
                double deltaY = PERCENTMOVEPHYSICS * (this.getLowerY() - p.getY());
                if(deltaX < deltaY){
                    this.setX(getX() - deltaX);
                }else{
                    setY(getY() - deltaY);
                }
                shouldFall = false;
            }
            if(pointOtherPhysics[3]) {
                double deltaX = PERCENTMOVEPHYSICS * (p.getRightX() - this.getX());
                double deltaY = PERCENTMOVEPHYSICS * (this.getLowerY() - p.getY());
                if(deltaX < deltaY){
                    this.setX(getX() + deltaX);
                }else{
                    setY(getY() - deltaY);
                }
                shouldFall = false;
            }
        }
        return shouldFall;
    }
    
    @Override
    public void drawOn(Graphics2D g) {
        g.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
    }

    public abstract void spawning();
    
    public void updateMovement(){
        mover.updatePos();
    }
    
    public void addMover(Movement mover){
        this.mover = mover;
        mover.setSprite(this);
    }
    
    public Movement getMover(){
        return mover;
    }

    @Override
    public boolean isSprite() {
        return true;
    }

    public abstract boolean interactsWith(Sprite otherSprite);
    public abstract double getJoustHeight();
    public abstract void death();
}
