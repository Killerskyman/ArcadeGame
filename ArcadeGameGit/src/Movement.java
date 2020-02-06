import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Movement{
    
    public Sprite sprite;
    
    public Movement(Sprite sprite){
        this.sprite = sprite;
    }
    
    public abstract void updatePos();
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void jump();
    public abstract ArrayList<ActionListener> getMovers();
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
}
