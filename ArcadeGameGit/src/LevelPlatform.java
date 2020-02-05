import java.awt.*;

public class LevelPlatform extends Physics {
    
    public LevelPlatform(double x, double y, double w, double h) {
        super(0, x, y, w, h);
    }
    
    @Override
    public void physicsCollision(Physics p, PointCollide pointOtherPhysics) {
    
    }
    
    @Override
    public void drawOn(Graphics2D g) {
    
    }
}
