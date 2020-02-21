import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * class that controls AI movement
 */
public class AIMovement extends Movement{
    
    /**
     * constructs the AIMovement class
     * @param sprite Ai's sprite
     * @param player hero object to follow
     */
    public AIMovement(Sprite sprite, Sprite player) {
        super(sprite, player);
    }
    
    @Override
    public void updatePos() {
        if(track.getY()+5 < sprite.getY()){
            this.jump();
        }
        if(track.getX() > sprite.getX()){
            moveRight();
        }else{
            moveLeft();
        }
    }
}
