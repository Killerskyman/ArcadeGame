import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Sprite extends Physics {

    private static final double PERCENTMOVEPHYSICS = 0.5;

    public Sprite(double fallAccel) {
        super(fallAccel);
    }
    
    @Override
    public void physicsCollision(Physics p, PointCollide pointOtherPhysics) {
        boolean continuePhysics = true;
        if(p.getClass() == this.getClass()){
            continuePhysics = this.interactsWith((Sprite) p);
        }
        if(!continuePhysics) return;
        if(pointOtherPhysics == PointCollide.UL){
            this.setX(this.getX()-PERCENTMOVEPHYSICS*(this.getWidth()-(p.getX()-this.getX())));
            this.setY(this.getY()-PERCENTMOVEPHYSICS*(this.getHeight()-(p.getY()-this.getY())));
        }
        if(pointOtherPhysics == PointCollide.UR){
            this.setX(this.getX()+PERCENTMOVEPHYSICS*(this.getWidth()-(p.getRightX()-this.getX())));
            this.setY(this.getY()-PERCENTMOVEPHYSICS*(this.getHeight()-(p.getY()-this.getY())));
        }
    }
    
    @Override
    public void drawOn(Graphics2D g) {
        g.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
    }

    public abstract void spawning();

    public void updateMovement(int x, int vely){
        setX(x);
        setJumpVely(vely);
    }

    public abstract boolean interactsWith(Sprite otherSprite);
}
