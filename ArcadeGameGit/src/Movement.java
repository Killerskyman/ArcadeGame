import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * class defines what all movements should have, is connected to a sprite
 */
public abstract class Movement{

    /**
     * defines how the user interacts with a sprite this object is attached to
     */
    public double horzMov = 5;//how fast it moves side to side
    public double jumpVel = 10;//how high it jumps
    public Sprite sprite;
    
    public Movement(Sprite sprite){
        this.sprite = sprite;
    }
    
    /**
     * is called when the Movement subclass should move its sprite
     */
    public abstract void updatePos();

    /**
     * moves the sprite to left
     */
    public void moveLeft() {
        sprite.setX(sprite.getX()-horzMov);
    }

    /**
     * moves the sprite to the right
     */
    public void moveRight() {
        sprite.setX(sprite.getX()+horzMov);
    }

    /**
     * makes the sprite jump
     */
    public void jump() {
        sprite.setJumpVely(-jumpVel);
    }
    
    /**
     * returns an ArrayList ActionListeners that are attached to the subclass
     * @return ArrayList of possible actions that can be performed on the sprite as ActionListeners, may be null
     */
    public abstract ArrayList<ActionListener> getMovers();//for using actionListeners specific to each Movement Type
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    /**
     * updates the movers of all the sprites in the given ArrayList
     * @param sprites sprites to update the Movement object for
     */
    public static void updateMovement(ArrayList<Sprite> sprites){
        for(Sprite sprite : sprites){
            sprite.updateMovement();
        }
    }
    
}
