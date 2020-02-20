import java.awt.*;
import java.util.ArrayList;

/**
 * class defines what a rectangular physics object should have and has associated methods for doing physics calculations
 */
public abstract class Physics2{
    public Color color;
    private double x;
    private double y;
    private double height = 40;
    private double width = 40;
    private double vely = 0;
    private double fallAccel;
    private static ArrayList<Physics2> canCollide = new ArrayList<>();
    
    /**
     * creates a physics object, default 40 x 40
     * @param fallAccel acceleration that the object should fall at
     * @param x starting x position
     * @param y starting y position
     */
    public Physics2(double fallAccel, double x, double y){
        this.x = x;
        this.y = y;
        this.fallAccel = fallAccel;
    }
    
    /**
     * creates a physics object
     * @param fallAccel acceleration that the object should fall at
     * @param x starting x position
     * @param y starting y position
     * @param width width of the physics object
     * @param height height of the physics object
     */
    public Physics2(double fallAccel, double x, double y, double width, double height){
        this(fallAccel, x, y);
        this.width = width;
        this.height = height;
    }
    
    public static void addToCollision(Physics2 physics2){
        if(canCollide.contains(physics2))return;
        canCollide.add(physics2);
    }
    
    public static void removeCollision(Physics2 physics2){
        canCollide.remove(physics2);
    }
    
    public static void addToCollision(ArrayList<Physics2> physics2s){
        canCollide.addAll(physics2s);
    }
    
    public static void removeCollision(ArrayList<Physics2> physics2s){
        canCollide.removeAll(physics2s);
    }
    
    public static void drawPhysics(Graphics2D g){
        for(Physics2 physics2 : canCollide){
            physics2.drawOn(g);
        }
    }
    
    public static void updatePhysics() {
        for(Physics2 physics2 : canCollide){
            physics2.updatePos();
        }
    }
    
    /**
     * determines if and where the physics object collides with physics p
     * @param p object to test against
     * @return array of 4 booleans that note what points this object has inside of p
     */
    public boolean[] doesCollideWith(Physics2 p){
        boolean[] pointCols = new boolean[4];
        if(p.contains(getX(), getY())) pointCols[0] = true;
        if(p.contains(getRightX(), getY())) pointCols[1] = true;
        if(p.contains(getRightX(), getLowerY())) pointCols[2] = true;
        if(p.contains(getX(), getLowerY())) pointCols[3] = true;
        return pointCols;
    }
    
    public boolean[] doesCollideWith(Physics2 p, double nextx, double nexty){
        boolean[] pointCols = new boolean[4];
        if(p.contains(nextx, nexty)) pointCols[0] = true;
        if(p.contains(nextx+getWidth(), nexty)) pointCols[1] = true;
        if(p.contains(nextx+getWidth(), nexty+getHeight())) pointCols[2] = true;
        if(p.contains(nextx, nexty+getHeight())) pointCols[3] = true;
        return pointCols;
    }
    
    public boolean getFalling(){
        return updateY(1);
    }
    
    public abstract boolean isSprite();
    
    /**
     * updates the position of the object based on isFalling and the acceleration and velocity override; mainly used with "jumping"
     * @param velyOveride
     */
    public void updatePos(double velyOveride){
        if(getFalling()){
            vely += fallAccel;
        }else{
            vely = velyOveride;
        }
        updateY(vely);
    }
    
    /**
     * updatePos with 0 override
     */
    public void updatePos(){
        updatePos(0);
    }
    
    /**
     * determines whether the point is contained within this object
     * @param x point x location
     * @param y point y location
     * @return true if contained inside
     */
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
    
    public void updateX(double x){
        double nextx = getX() + x;
        for(Physics2 physics2 : canCollide){
            boolean[] collide = this.doesCollideWith(physics2, nextx, 0);
            if(collide[0]||collide[3]){
                nextx = nextx+(physics2.getRightX()-nextx);
            }
            if(collide[1]||collide[2]){
                nextx = nextx - (getRightX()-physics2.getX());
            }
        }
    }
    
    public boolean updateY(double y){
        double nexty = getY() + y;
        boolean shouldfall = true;
        for(Physics2 physics2 : canCollide){
            boolean[] collide = this.doesCollideWith(physics2, 0, nexty);
            if(collide[0]||collide[1]){
                nexty = nexty-(physics2.getLowerY()-nexty);
            }
            if(collide[2]||collide[3]){
                nexty = nexty + (getLowerY()-physics2.getY());
                shouldfall = false;
            }
        }
        return shouldfall;
    }
    
    /**
     * @return the furthest x coordinate of the rectangle
     */
    public double getRightX(){
        return getX()+getWidth();
    }
    
    /**
     *
     * @return the furthest y coordinate of the rectangle
     */
    public double getLowerY(){
        return getY()+getHeight();
    }
    
    /**
     * makes object "jump"
     * @param vely how fast the object should jump
     */
    public void setJumpVely(double vely){
        if(!getFalling()) {
            updatePos(vely);
        }
    }
    
    public abstract void drawOn(Graphics2D g);
}
