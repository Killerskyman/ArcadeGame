import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * class defines what all movements should have, is connected to a sprite
 */
public abstract class Movement{
    
    public Sprite sprite;
    
    public Movement(Sprite sprite){
        this.sprite = sprite;
    }
    
    /**
     * is called when the Movement subclass should move its sprite
     */
    public abstract void updatePos();
    
    /**
     * specific method for basic movement of every sprite
     * call when you want to move the sprite left
     */
    public abstract void moveLeft();
    /**
     * specific method for basic movement of every sprite
     * call when you want to move the sprite right
     */
    public abstract void moveRight();
    /**
     * specific method for basic movement of every sprite
     * call when you want the sprite to jump
     */
    public abstract void jump();
    
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
