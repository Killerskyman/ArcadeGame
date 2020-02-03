import java.awt.*;

public abstract class Physics{
    private double x;
    private double y;
    private double height;
    private double width;
    private boolean isFalling;
    private double vely;
    private double fallAccel;

    public Physics(double fallAccel){
        this.fallAccel = fallAccel;
    }

    public boolean doesCollideWith(Physics p){
        boolean doesContain = false;
        if(this.contains(p.getX(), p.getY())) doesContain = true;
        else if(this.contains(p.getX(), p.getY()+p.getHeight())) doesContain = true;
        else if(this.contains(p.getX()+p.getWidth(), p.getY()+p.getHeight())) doesContain = true;
        else if(this.contains(p.getX()+p.getWidth(), p.getY())) doesContain = true;
        return doesContain;
    }

    public abstract void physicsCollision(Physics p);

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
    
    public abstract void drawOn(Graphics2D g);
}
