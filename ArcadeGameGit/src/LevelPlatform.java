import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * level platform that is a physics object and owned by a level
 */
public class LevelPlatform extends Physics {
    
    /**
     * makes a new level at the location with width and height
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public LevelPlatform(double x, double y, double w, double h) {
        super(0, x, y, w, h);
    }
    
    @Override
    public boolean physicsCollision(Physics p, boolean[] pointOtherPhysics) {
        setFalling(false); //should never move up or down
        return false;
    }

    @Override
    public boolean isSprite() {
        return false;
    }

    @Override
    public void drawOn(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fill(new Rectangle2D.Double(getX(), getY(),getWidth(), getHeight()));
    }
}
