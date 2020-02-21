import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * class that is the bullet from monster2
 */
public class Bullet extends Sprite{
    
    private static final double WIDTH = 20, HEIGHT= 20;
    private double xv;
    private double yv;

    /**
     * constructs a bullet class
     * @param x position of sprite
     * @param y position of sprite
     * @param vel of the bullet
     * @param vector for bullet to travel
     */
    public Bullet(double x, double y, double vel, Point2D.Double vector) {
    	
        super(0, x, y, WIDTH, HEIGHT, true);
        color = Color.DARK_GRAY;
        double dx = vector.getX()-x;
        double dy = vector.getY()-y;
        double vm = Math.sqrt((dx * dx) + (dy * dy));
        this.xv = (dx*vel)/vm;
        this.yv = (dy*vel)/vm;
        addMover(new Movement(this) {
            @Override
            public void updatePos() {
                sprite.setX(sprite.getX()+xv);
                sprite.setY(sprite.getY()+yv);
            }
    
            @Override
            public void moveLeft() {
            
            }
    
            @Override
            public void moveRight() {
            
            }
    
            @Override
            public void jump() {
            
            }
    
            @Override
            public ArrayList<ActionListener> getMovers() {
                return null;
            }
        });
    }
    
    /**
     * constructs a bullet class with the default velocity (15)
     * @param x position of sprite
     * @param y position of sprite
     * @param vector for bullet to travel
     */
    public Bullet(double x, double y, Point2D.Double vector){
        this(x,y,15,vector);
    }

    @Override
    public boolean physicsCollision(Physics p, boolean[] pointOtherPhysics) {
        boolean output;
        if(!p.isSprite()) {
            output = super.physicsCollision(p, pointOtherPhysics);
        }else{
            if(((Sprite) p).isFriendly){
                return true;
            }else{
                output = super.physicsCollision(p, pointOtherPhysics);
            }
        }
        return output;
    }
    
    @Override
    public boolean interactsWith(Sprite otherSprite) {
        if(!otherSprite.isFriendly){
            isDead = true;
        }
        return false;
    }
    
    @Override
    public boolean interactsWith(Physics p) {
        if(!p.isSprite()){
            isDead = true;
            return false;
        }
        return super.interactsWith(p);
    }
    
    @Override
    public double getJoustHeight() {
        return 0;
    }
    
    @Override
    public Sprite death() {
        return null;
    }
}
