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
    public Bullet(double x, double y, double vel, Point2D.Double vector) {
    	/**
    	 * constructs a bullet class
    	 * @param x position of sprite
    	 * @param y position of sprite
    	 * @param velocity of the bullet
    	 * @param direction for bullet to travel
    	 */
        super(0, x, y, 10, 10, true);
        this.vector = vector;
        this.vel = vel;
        double dx = vector.getX()-x;
        double dy = vector.getY()-y;
        double vm = Math.sqrt((dx * dx) + (dy * dy));
        this.xv = (dx*vel)/vm;
        this.yv = (dy*vel)/vm;
        addMover(new Movement(this) {
        	/**
        	 * adds a mover to the bullet 
        	 */
            @Override
            public void updatePos() {
            	/**
            	 * moves the x and y position of the bullet in the direction of the vector
            	 */
                sprite.setX(sprite.getX()+xv);
                sprite.setY(sprite.getY()+yv);
            }
    
            @Override
            public void moveLeft() {
            	/**
            	 * moves bullet left
            	 */
        
            }
    
            @Override
            public void moveRight() {
            	/**
            	 * moves bullet right
            	 */
        
            }
    
            @Override
            public void jump() {
            	/**
            	 * updates bullet's vertical velocity
            	 */
        
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
    
    @Override
    public Sprite spawning() {
    	/**
    	 * spawns the bullet sprite
    	 */
        return null;
    }
    
    @Override
    public boolean interactsWith(Sprite otherSprite) {
    	/**
    	 * makes the bullet unable to interact with the given sprite
    	 * @param other sprite
    	 */
        return false;
    }
    
    @Override
    public double getJoustHeight() {
    	/**
    	 * returns height to determine if hero dies
    	 */
        return 0;
    }
    
    @Override
    public void death() {
    	/**
    	 * removes the bullet object
    	 */
    
    }
}
