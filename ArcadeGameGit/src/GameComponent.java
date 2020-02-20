import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * draws all the current physics objects and contains all the listeners and keyBindings for controlling the game
 */
public class GameComponent extends JPanel {
    
    /**
     * adds objects with physics to the game component
     */
    public GameComponent(){
    }
    
    /**
     * adds inputs to the game component
     * @param keyInput map
     * @param actMap of actions for those keys
     */
    public GameComponent(ComponentInputMap keyInput, ActionMap actMap){
        setInputMap(WHEN_IN_FOCUSED_WINDOW, keyInput);
        setActionMap(actMap);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.scale(getWidth()/1920.0, getHeight()/1080.0);
        Physics2.drawPhysics(g2);
    }
}
