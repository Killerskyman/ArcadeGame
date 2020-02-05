import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LevelPlatform extends Physics {
    
    public LevelPlatform(double x, double y, double w, double h) {
        super(0, x, y, w, h);
    }
    
    @Override
    public void physicsCollision(Physics p, boolean[] pointOtherPhysics) {
        setFalling(false);
    }
    
    @Override
    public void drawOn(Graphics2D g) {
        g.fill(new Rectangle2D.Double(getX(), getY(),getWidth(), getHeight()));
    }
}
