import java.awt.*;
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
        color = Color.DARK_GRAY;
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
                if(getX() < 0 || getY() < 0 || getRightX() > 1920 || getLowerY() > 1080-10){
                    sprite.isDead = true;
                }
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
    public Sprite spawning() {
    	
        return null;
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
    public Sprite death() {
        return null;
    }
}
