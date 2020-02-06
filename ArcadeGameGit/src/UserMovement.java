import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class UserMovement extends Movement {
    
    private static final double horzMovSpeed = 5;
    private static final double jumpVel = 10;
    private UsrMoveLeftList moverLeft = new UsrMoveLeftList();
    private UsrMoveRightList moverRight = new UsrMoveRightList();
    private UsrJumpList moverJump = new UsrJumpList();
    private ArrayList<ActionListener> movers = new ArrayList<>(Arrays.asList(moverLeft, moverRight, moverJump));

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

    public class UsrMoveLeftList implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            moveLeft();
        }
    }
    
    public class UsrMoveRightList implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            moveRight();
        }
    }
    
    public class UsrJumpList implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            jump();
        }
    }
}
