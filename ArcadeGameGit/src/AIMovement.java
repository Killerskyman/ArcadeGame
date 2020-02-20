import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * class that controls AI movement
 */
public class AIMovement extends Movement{
    
    private static final double horzMove = 5;
    private static final double jumpVel = 10;
    public Hero player;
    
    /**
     * constructs the AIMovement class
     * @param sprite Ai's sprite
     * @param player hero object to follow
     */
    public AIMovement(Sprite sprite, Hero player) {
    	
        super(sprite);
        this.player = player;
    }
    
    @Override
    public void updatePos() {
        if(player.getY()+5 < sprite.getY()){
            this.jump();
        }
        if(player.getX() > sprite.getX()){
            moveRight();
        }else{
            moveLeft();
        }
    }
    
    @Override
    public void moveLeft() {
        sprite.setX(sprite.getX() - horzMove);
    }
    
    @Override
    public void moveRight() {
    	sprite.setX(sprite.getX() + horzMove);
    }
    
    @Override
    public void jump() {
    	sprite.setJumpVely(-jumpVel);
    }

    @Override
    public ArrayList<ActionListener> getMovers() {
        return null;
    }
}
