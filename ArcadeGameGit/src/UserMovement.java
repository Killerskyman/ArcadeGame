
public class UserMovement extends Movement {
    
    private static final double horzMovSpeed = 5;
    private static final double jumpVel = 20;
    
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
        sprite.setJumpVely(jumpVel);
    }
}
