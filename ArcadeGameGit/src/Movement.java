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
    
    public abstract void updatePos();
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void jump();
    public abstract ArrayList<ActionListener> getMovers();//for using actionListeners specific to each Movement Type
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
}
