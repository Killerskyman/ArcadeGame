import java.awt.*;
import java.util.ArrayList;

/**
 * class defines what a rectangular physics object should have and has associated methods for doing physics calculations
 */
public abstract class Physics{
    public Color color;
    private double x;
    private double y;
    private double height = 40;
    private double width = 40;
    private boolean isFalling = true;
    private double vely = 0;
    private double fallAccel;
    
    /**
     * creates a physics object, default 40 x 40
     * @param fallAccel acceleration that the object should fall at
     * @param x starting x position
     * @param y starting y position
     */
    public Physics(double fallAccel, double x, double y){
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
    public Physics(double fallAccel, double x, double y, double width, double height){
        this(fallAccel, x, y);
        this.width = width;
        this.height = height;
    }
    
    /**
     * determines if and where the physics object collides with physics p
     * @param p object to test against
     * @return array of 4 booleans that note what points this object has inside of p
     */
    public boolean[] doesCollideWith(Physics p){
        boolean[] pointCols = new boolean[4];
        if(p.contains(getX(), getY())) pointCols[0] = true;
        if(p.contains(getRightX(), getY())) pointCols[1] = true;
        if(p.contains(getRightX(), getLowerY())) pointCols[2] = true;
        if(p.contains(getX(), getLowerY())) pointCols[3] = true;
        return pointCols;
    }
    
    /**
     * what to do when this physics object collides with p physics object
     * @param p object that was collided with
     * @param pointOtherPhysics array of booleans indicating all the corners of this object that are inside p object
     * @return whether the object should be falling after the collision
     */
    public abstract boolean physicsCollision(Physics p, boolean[] pointOtherPhysics);
    
    /**
     * what to do when two physics objects in general collide, useful for killing one of the objects if the touch
     * @param p object to interact with
     * @return whether to continue physics operations on p
     */
    public boolean interactsWith(Physics p){
        return true;
    }

    public void setFalling(boolean isFalling){
        this.isFalling = isFalling;
    }
    
    public boolean getFalling(){
        return isFalling;
    }
    
    /**
     * whether this physics object is a sprite or not
     * @return
     */
    public abstract boolean isSprite();
    
    /**
     * updates the position of the object based on isFalling and the acceleration and velocity override; mainly used with "jumping"
     * @param velyOveride
     */
    public void updatePos(double velyOveride){
        if(isFalling){
            vely += fallAccel;
        }else{
            vely = velyOveride;
        }
        y += vely;
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
        if(!isFalling) {
            updatePos(vely);
        }
    }
    
    public abstract void drawOn(Graphics2D g);
    
    /**
     * updates the physics engine for the arraylist of the physics provided
     * @param physics list of objects to update physics for
     */
    public static void updatePhysics(ArrayList<Physics> physics){
        for(Physics checking : physics){
            boolean shouldNotFall = false;
            for(Physics checker : physics){
                if(checking != checker){
                    boolean[] col = checking.doesCollideWith(checker);
                    boolean shouldNotFallHere = !checking.physicsCollision(checker, col);
                    shouldNotFall = shouldNotFall || shouldNotFallHere;
                }
            }
            checking.setFalling(!shouldNotFall);
        }
        for(Physics physic : physics){
            physic.updatePos();
        }
    }
}
