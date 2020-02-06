import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class Physics{
    private double x;
    private double y;
    private double height = 40;
    private double width = 40;
    private boolean isFalling = true;
    private double vely = 0;
    private double fallAccel;

    public Physics(double fallAccel, double x, double y){
        this.x = x;
        this.y = y;
        this.fallAccel = fallAccel;
    }
    
    public Physics(double fallAccel, double x, double y, double width, double height){
        this(fallAccel, x, y);
        this.width = width;
        this.height = height;
    }

    public boolean[] doesCollideWith(Physics p){
        boolean[] pointCols = new boolean[4];
        if(p.contains(getX(), getY())) pointCols[0] = true;
        if(p.contains(getRightX(), getY())) pointCols[1] = true;
        if(p.contains(getRightX(), getLowerY())) pointCols[2] = true;
        if(p.contains(getX(), getLowerY())) pointCols[3] = true;
        return pointCols;
    }

    public abstract boolean physicsCollision(Physics p, boolean[] pointOtherPhysics);

    public void setFalling(boolean isFalling){
        this.isFalling = isFalling;
    }
    
    public boolean getFalling(){
        return isFalling;
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
        if(!isFalling) {
            updatePos(vely);
        }
    }
    
    public abstract void drawOn(Graphics2D g);
}
