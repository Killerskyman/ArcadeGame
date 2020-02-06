import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameComponent extends JPanel {
    
    private ArrayList<Physics> physics;
    
    public GameComponent(ArrayList<Physics> physics){
        this.physics = physics;
    }
    
    public GameComponent(ArrayList<Physics> physics, ComponentInputMap keyInput, ActionMap actMap){
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
