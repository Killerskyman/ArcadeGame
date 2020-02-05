import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Sprite extends Physics {

    private static final double PERCENTMOVEPHYSICS = 0.5;
    private Movement mover;
    public boolean isDead = false;

    public Sprite(double fallAccel, double x, double y) {
        super(fallAccel, x, y);
    }
    
    @Override
    public void physicsCollision(Physics p, PointCollide pointOtherPhysics) {
        boolean continuePhysics = true;
        if(p.getClass() == this.getClass()){
            continuePhysics = this.interactsWith((Sprite) p);
        }
        if(!continuePhysics) return;
        if(pointOtherPhysics == PointCollide.UL){
            this.setX(this.getX()-PERCENTMOVEPHYSICS*(this.getRightX()-p.getX()));
            this.setY(this.getY()-PERCENTMOVEPHYSICS*(this.getLowerY()-p.getY()));
        }
        if(pointOtherPhysics == PointCollide.UR){
            this.setX(this.getX()+PERCENTMOVEPHYSICS*(p.getRightX()-this.getX()));
            this.setY(this.getY()-PERCENTMOVEPHYSICS*(this.getLowerY()-p.getY()));
        }
        if(pointOtherPhysics == PointCollide.LR){
            this.setX(this.getX()+PERCENTMOVEPHYSICS*(p.getRightX()-this.getX()));
            this.setY(this.getY()+PERCENTMOVEPHYSICS*(p.getLowerY()-this.getY()));
        }
        if(pointOtherPhysics == PointCollide.LR){
            this.setX(this.getX()-PERCENTMOVEPHYSICS*(this.getRightX()-p.getX()));
            this.setY(this.getY()+PERCENTMOVEPHYSICS*(p.getLowerY()-this.getY()));
        }
    }
    
    @Override
    public void drawOn(Graphics2D g) {
        g.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
    }

    public abstract void spawning();

    public void updateMovement(){
        mover.updatePos();
    }
    
    public void addMover(Movement mover){
        this.mover = mover;
        mover.setSprite(this);
    }

    public abstract boolean interactsWith(Sprite otherSprite);
    public abstract double getJoustHeight();
    public abstract void death();
}
