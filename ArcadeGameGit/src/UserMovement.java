import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class UserMovement extends Movement {
    
    /**
     * defines how the user interacts with a sprite this object is attached to
     */
    private static final double horzMovSpeed = 7;//how fast it moves side to side
    private static final double jumpVel = 20;//how high it jumps
    private UsrMoveLeftList moverLeft = new UsrMoveLeftList();
    private UsrMoveRightList moverRight = new UsrMoveRightList();
    private UsrJumpList moverJump = new UsrJumpList();
    private ArrayList<ActionListener> movers = new ArrayList<>(Arrays.asList(moverLeft, moverRight, moverJump));//combine in an arraylist to get it easier

    public UsrMoveLeftList getMoverLeft() {
        return moverLeft;
    }

    public UsrMoveRightList getMoverRight() {
        return moverRight;
    }

    public UsrJumpList getMoverJump() {
        return moverJump;
    }

    public UserMovement(Sprite sprite) {
        super(sprite);
    }
    
    @Override
    public void updatePos() {
    }
    
    @Override
    public void moveLeft() {
        sprite.setX(sprite.getX()-horzMovSpeed);
    }
    
    @Override
    public void moveRight() {
        sprite.setX(sprite.getX()+horzMovSpeed);
    }
    
    @Override
    public void jump() {
        sprite.setJumpVely(-jumpVel);
    }

    @Override
    public ArrayList<ActionListener> getMovers() {
        return movers;
    }
    
    /**
     * action listener can be used to move the object to left
     */
    public class UsrMoveLeftList implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            moveLeft();
        }
    }
    
    /**
     * action listener can be used to move the object to the right
     */
    public class UsrMoveRightList implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            moveRight();
        }
    }
    
    /**
     * action listener can be used to jump the object
     */
    public class UsrJumpList implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            jump();
        }
    }
}
