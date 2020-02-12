import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * class that controls AI movement WIP
 */
public class AIMovement extends Movement{
    
    private static final double horzMove = 5;
    private static final double jumpVel = 10;
    public Hero player;
    
    public AIMovement(Sprite sprite, Hero player) {
    	/**
    	 * constructs the AIMovement class
    	 * @param Ai's sprite
    	 * @param hero object player controls
    	 */
        super(sprite);
        this.player = player;
    }
    
    @Override
    public void updatePos() {
    	/**
    	 * changes the AI's position based on where the player is
    	 */
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
    	/**
    	 * updates the sprite's X value to make it move left
    	 */
        sprite.setX(sprite.getX() - horzMove);
    }
    
    @Override
    public void moveRight() {
    	/**
    	 * updates the sprite's X value to make it move right
    	 */
        sprite.setX(sprite.getX() + horzMove);
    }
    
    @Override
    public void jump() {
    	/**
    	 * updates sprite's jump velocity
    	 */
        sprite.setJumpVely(-jumpVel);
    }

    @Override
    public ArrayList<ActionListener> getMovers() {
        return null;
    }
}
