import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * draws all the current physics objects and contains all the listeners and keyBindings for controlling the game
 */
public class GameComponent extends JPanel {
    
    private ArrayList<Physics> physics;
    
    /**
     * adds objects with physics to the game component
     * @param physics array list of physics objects
     */
    public GameComponent(ArrayList<Physics> physics){
    	
        this.physics = physics;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.scale(getWidth()/1920.0, getHeight()/1080.0);
        for(Physics physic : physics){
            physic.drawOn(g2);
        }
    }
}
