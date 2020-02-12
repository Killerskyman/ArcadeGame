import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * draws all the current physics objects and contains all the listeners and keyBindings for controlling the game
 */
public class GameComponent extends JPanel {
    
    private ArrayList<Physics> physics;
    
    public GameComponent(ArrayList<Physics> physics){
    	/**
    	 * adds objects with physics to the game component
    	 * @param array list of physics objects
    	 */
        this.physics = physics;
    }
    
    public GameComponent(ArrayList<Physics> physics, ComponentInputMap keyInput, ActionMap actMap){
    	/**
    	 * adds inputs to the game component
    	 * @param array list of physics objects
    	 * @param input map 
    	 * @param maps of actions for those keys
    	 */
        this(physics);
        setInputMap(WHEN_IN_FOCUSED_WINDOW, keyInput);
        setActionMap(actMap);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for(Physics physic : physics){
            physic.drawOn(g2);
        }
    }
}
