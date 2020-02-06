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
    public void physicsCollision(Physics p, boolean[] pointOtherPhysics) {
        boolean continuePhysics = true;
        if(p.getClass() == this.getClass()){
            continuePhysics = this.interactsWith((Sprite) p);
        }
        if(!continuePhysics) return;
        setFalling(true);
        if(pointOtherPhysics[0] && pointOtherPhysics[1])setY(getY()+PERCENTMOVEPHYSICS*(p.getLowerY()-getY()));
        else if(pointOtherPhysics[1] && pointOtherPhysics[2])setX(getX()-PERCENTMOVEPHYSICS*(p.getX()-getRightX()));
        else if(pointOtherPhysics[2] && pointOtherPhysics[3]){
            setY(getY()-PERCENTMOVEPHYSICS*(getLowerY()-p.getY()));
            setFalling(false);
        }
        else if(pointOtherPhysics[3] && pointOtherPhysics[0])setX(getX()+PERCENTMOVEPHYSICS*(p.getRightX()-getX()));
        else {
            if(pointOtherPhysics[0]) {
                double deltaX = PERCENTMOVEPHYSICS * (p.getRightX() - this.getX());
                double deltaY = PERCENTMOVEPHYSICS * (p.getLowerY() - this.getY());
                if(deltaX > deltaY){
                    this.setX(getX() + deltaX);
                }else{
                    this.setY(getY() + deltaY);
                }
            }
            if(pointOtherPhysics[1]) {
                this.setX(this.getX() - Math.max(PERCENTMOVEPHYSICS * (p.getRightX() - this.getX()), 1));
                this.setY(this.getY() + PERCENTMOVEPHYSICS * (p.getLowerY() - this.getY()));
            }
            if(pointOtherPhysics[2]) {
                this.setX(this.getX() - Math.max(PERCENTMOVEPHYSICS * (p.getRightX() - this.getX()), 1));
                this.setY(this.getY() - PERCENTMOVEPHYSICS * (this.getLowerY() - p.getY()));
                setFalling(false);
            }
            if(pointOtherPhysics[3]) {
                this.setX(this.getX() + Math.min(PERCENTMOVEPHYSICS * (p.getRightX() - this.getX()), 1));
                this.setY(this.getY() - PERCENTMOVEPHYSICS * (this.getLowerY() - p.getY()));
                setFalling(false);
            }
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
    
    public Movement getMover(){
        return mover;
    }

    public abstract boolean interactsWith(Sprite otherSprite);
    public abstract double getJoustHeight();
    public abstract void death();
}
