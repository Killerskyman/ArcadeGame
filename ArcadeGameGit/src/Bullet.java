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
        super(0, x, y);
        this.vector = vector;
        this.vel = vel;
        double dx = vector.getX()-x;
        double dy = vector.getY()-y;
        double vm = (vel * vel) / ((dx * dx) + (dy * dy));
        this.xv = vector.getX()* vm;
        this.yv = vector.getY()* vm;
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
    
    @Override
    public void spawning() {
    
    }
    
    @Override
    public boolean interactsWith(Sprite otherSprite) {
        return true;
    }
    
    @Override
    public double getJoustHeight() {
        return 0;
    }
    
    @Override
    public void death() {
    
    }
}
