import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameComponent extends JComponent {
    
    ArrayList<Physics> physics;
    
    public GameComponent(ArrayList<Physics> physics){
        this.physics = physics;
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
