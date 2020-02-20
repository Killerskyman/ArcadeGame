import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * defines what a movable object that interacts with others of itself should have
 */
public abstract class Sprite extends Physics {

    private static final double PERCENTMOVEPHYSICS = 0.99; //how fast it moves from other objects colliding with it
    private Movement mover; //how it should be moved
    public boolean isDead = false; //whether it should be deleted
    public boolean spawnsSprite = false;
    public boolean isFriendly;
    public boolean isMonster = false;
    
    /**
     * creates a sprite with following characteristics
     * @param fallAccel how fast it falls
     * @param x starting x
     * @param y starting y
     */
    public Sprite(double fallAccel, double x, double y, boolean isFriendly) {
        super(fallAccel, x, y);
        this.isFriendly = isFriendly;
    }
    
    /**
     * creates a sprite with following characteristics
     * @param fallAccel how fast it falls
     * @param x starting x
     * @param y starting y
     * @param w width of the object
     * @param h height of the object
     * @param isFriendly whether it is friendly to monsters
     */
    public Sprite(double fallAccel, double x, double y, double w, double h, boolean isFriendly){
        super(fallAccel, x, y, w, h);
        this.isFriendly = isFriendly;
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
        boolean[] noContact = {false, false, false, false};
        if(p.isSprite() && !(Arrays.equals(pointOtherPhysics, noContact))){//check to see if the other object is a sprite and interact with it
            continuePhysics = this.interactsWith((Sprite) p) || ((Sprite) p).interactsWith(this);
        }else if(!Arrays.equals(pointOtherPhysics, noContact)){
            continuePhysics = this.interactsWith(p);
        }
        if(!continuePhysics) return true;
        boolean shouldFall = true;
        if(pointOtherPhysics[0] && pointOtherPhysics[1])setY(getY()+PERCENTMOVEPHYSICS*(p.getLowerY()-getY()));
        if(pointOtherPhysics[1] && pointOtherPhysics[2])setX(getX()-PERCENTMOVEPHYSICS*(getRightX()-p.getX()));
        if(pointOtherPhysics[2] && pointOtherPhysics[3]){
            setY(getY()-PERCENTMOVEPHYSICS*(getLowerY()-p.getY()));
            shouldFall = false;
        }
        if(pointOtherPhysics[3] && pointOtherPhysics[0])setX(getX()+PERCENTMOVEPHYSICS*(p.getRightX()-getX()));
        {
            if(pointOtherPhysics[0]&&!(pointOtherPhysics[1]||pointOtherPhysics[2]||pointOtherPhysics[3])) {
                double deltaX = PERCENTMOVEPHYSICS * (p.getRightX() - this.getX());
                double deltaY = PERCENTMOVEPHYSICS * (p.getLowerY() - this.getY());
                if(deltaX < deltaY){
                    this.setX(getX() + deltaX);
                }else{
                    this.setY(getY() + deltaY);
                }
            }
            if(pointOtherPhysics[1]&&!(pointOtherPhysics[0]||pointOtherPhysics[2]||pointOtherPhysics[3])) {
                double deltaX = PERCENTMOVEPHYSICS * (this.getRightX() - p.getX());
                double deltaY = PERCENTMOVEPHYSICS * (p.getLowerY() - this.getY());
                if(deltaX < deltaY){
                    this.setX(getX() - deltaX);
                }else{
                    setY(getY() + deltaY);
                }
            }
            if(pointOtherPhysics[2]&&!(pointOtherPhysics[0]||pointOtherPhysics[1]||pointOtherPhysics[3])) {
                double deltaX = PERCENTMOVEPHYSICS * (this.getRightX() - p.getX());
                double deltaY = PERCENTMOVEPHYSICS * (this.getLowerY() - p.getY());
                if(deltaX < deltaY){
                    this.setX(getX() - deltaX);
                }else{
                    setY(getY() - deltaY);
                    shouldFall = false;
                }
                
            }
            if(pointOtherPhysics[3]&&!(pointOtherPhysics[0]||pointOtherPhysics[1]||pointOtherPhysics[2])) {
                double deltaX = PERCENTMOVEPHYSICS * (p.getRightX() - this.getX());
                double deltaY = PERCENTMOVEPHYSICS * (this.getLowerY() - p.getY());
                if(deltaX < deltaY){
                    this.setX(getX() + deltaX);
                }else{
                    setY(getY() - deltaY);
                    shouldFall = false;
                }
            }
        }
        return shouldFall;
    }
    
    @Override
    public void drawOn(Graphics2D g) {
        g.setColor(color);
        g.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
    }
    
    /**
     * what to do when spawning a new sprite
     * @return the sprite to spawn
     */
    public abstract Sprite spawning();
    
    public void updateMovement(){
        if(mover != null) {
            mover.updatePos();
        }
    }
    
    public void addMover(Movement mover){
        this.mover = mover;
        mover.setSprite(this);
    }
    
    public Movement getMover(){
        return mover;
    }
    
    /**
     * retrieves the actionListener for the mover attached to the sprite
     * @param index index to grab from in case of multiple
     * @return will be null if actionlistener is not found
     */
    public ActionListener getAction(int index){
        ActionListener out = mover.getMovers().get(index);
        if(out == null){
            return e -> {};
        }else{
            return out;
        }
    }

    @Override
    public boolean interactsWith(Physics p) {
        if(p.isSprite()){
            return this.interactsWith((Sprite) p);
        }else {
            return super.interactsWith(p);
        }
    }

    @Override
    public boolean isSprite() {
        return true;
    }
    
    /**
     * how long between spawn intervals
     * @return number of game cycles to wait
     */
    public int spawnTiming(){
        return 0;
    }
    
    /**
     * sprite specific interaction, allows for specific yet necessary calls to certain parameters
     * @param sprite sprite that this sprite interacts with
     * @return whether to continue physics on the object
     */
    public abstract boolean interactsWith(Sprite sprite);
    public abstract double getJoustHeight();
    
    /**
     * what to do at death, only modify this objects parameters, removing the object from specific arraylists is taken care of elsewhere
     * @return the object to spawn upon death, may be null
     */
    public Sprite death(){
        isDead = true;
        return null;
    }
}
