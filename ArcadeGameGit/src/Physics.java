import java.awt.*;

public abstract class Physics{
    private double x;
    private double y;
    private double height;
    private double width;
    public boolean isFalling;
    private double vely;
    private double fallAccel;

    public enum PointCollide{
        UL,
        UR,
        LL,
        LR,
        NAC
    }

    public Physics(double fallAccel){
        this.fallAccel = fallAccel;
    }

    public PointCollide doesCollideWith(Physics p){
        PointCollide doesContain = PointCollide.NAC;
        if(this.contains(p.getX(), p.getY())) doesContain = PointCollide.UL;
        else if(this.contains(p.getX(), p.getY()+p.getHeight())) doesContain = PointCollide.LL;
        else if(this.contains(p.getX()+p.getWidth(), p.getY()+p.getHeight())) doesContain = PointCollide.LR;
        else if(this.contains(p.getX()+p.getWidth(), p.getY())) doesContain = PointCollide.UR;
        return doesContain;
    }

    public abstract void physicsCollision(Physics p, PointCollide pointOtherPhysics);

    public void setFalling(boolean isFalling){
        this.isFalling = isFalling;
    }

    public void updatePos(double velyOveride){
        if(isFalling){
            vely += fallAccel;
        }else{
            vely = velyOveride;
        }
        y += vely;
    }

    public void updatePos(){
        updatePos(0);
    }

    public boolean contains(double x, double y){
        if(this.getX() <= x && x <= this.getX()+this.getWidth()){
            return this.getY() <= y && y <= this.getY() + this.getHeight();
        }
        return false;
    }

    public double getY(){
        return y;
    }

    public double getX(){
        return x;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getRightX(){
        return getX()+getWidth();
    }

    public double getLowerY(){
        return getY()+getHeight();
    }

    public void setJumpVely(double vely){
        updatePos(vely);
    }
    
    public abstract void drawOn(Graphics2D g);
}
