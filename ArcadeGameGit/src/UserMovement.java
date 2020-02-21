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
    private final UsrMoveLeftList moverLeft = new UsrMoveLeftList();
    private final UsrMoveRightList moverRight = new UsrMoveRightList();
    private final UsrJumpList moverJump = new UsrJumpList();
    private final ArrayList<ActionListener> movers = new ArrayList<>(Arrays.asList(moverLeft, moverRight, moverJump));//combine in an arraylist to get it easier

    public enum ActionListIndex{
        LEFT(0),
        RIGHT(1),
        UP(2);

        private int index;
        ActionListIndex(int index) {
            this.index = index;
        }

        public int getIndex(){
            return index;
        }
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
