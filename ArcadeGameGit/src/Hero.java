/**
 * hero class that is made to be the player
 */
public class Hero extends Sprite{
    
    /**
     * make a new Hero object that defaults to the UserMovement as input (arrow keys for control)
     * @param fallAccel falling acceleration for the hero
     * @param x starting x position
     * @param y starting y position
     */
    public Hero(double fallAccel, double x, double y) {
        super(fallAccel, x, y, false);
        addMover(new UserMovement(this));
    }
    
    @Override
    public Sprite spawning() {
        return null;
    }
    
    /**
     * determines the object can interact with the given sprite
     * @param otherSprite
     * @return false if the given sprite is higher and true if the hero is higher
     */
    @Override
    public boolean interactsWith(Sprite otherSprite) {
    	
        if(otherSprite.getJoustHeight() > this.getJoustHeight()){
            otherSprite.death();
            return false;
        }
        return true;
    }
    
    /**
     * gets height used to determine interaction with monsters
     * @return hero's y value
     */
    @Override
    public double getJoustHeight() {
        return this.getY();
    }
}
