import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * class that is the bullet from monster2
 * WIP
 */
public class Bullet extends Sprite{
    
    private double xv;
    private double yv;
    private Point2D.Double vector;
    private double vel;
    
    /**
     * constructs a bullet class
     * @param x position of sprite
     * @param y position of sprite
     * @param vel of the bullet
     * @param vector for bullet to travel
     */
    public Bullet(double x, double y, double vel, Point2D.Double vector) {
    	
        super(0, x, y, 10, 10, true);
        this.vector = vector;
        this.vel = vel;
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
    
    public Bullet(double x, double y, Point2D.Double vector){
        this(x,y,15,vector);
    }
    
    /**
     * spawns the bullet sprite
     */
    @Override
    public Sprite spawning() {
    	
        return null;
    }
    
    /**
     * makes the bullet unable to interact with the given sprite
     * @param otherSprite sprite
     */
    @Override
    public boolean interactsWith(Sprite otherSprite) {
    	
        return false;
    }
    
    /**
     * returns height to determine if hero dies
     */
    @Override
    public double getJoustHeight() {
    	
        return 0;
    }
    
    /**
     * removes the bullet object
     * @return
     */
    @Override
    public Sprite death() {
        return null;
    }
}
