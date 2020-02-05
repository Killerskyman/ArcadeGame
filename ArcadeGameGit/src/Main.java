import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * TODO:make a good comment here
 * 
 * @author Skyler Cleland and Daniel Vega
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}
 
	ArrayList<Physics> physics = new ArrayList<>();
	ArrayList<Sprite> sprites = new ArrayList<>();
	
	public Main(){
	    physics.add(new LevelPlatform(0, 300, 1000, 30));
	    physics.add(new Hero(0.5, 50, 50));
	    physics.get(1).setFalling(true);
        JFrame frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setTitle("physics test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameComponent gamecomp = new GameComponent(physics);
        frame.add(gamecomp, BorderLayout.CENTER);
        Timer timer = new Timer(20, new GameTickList(gamecomp));
        timer.start();
        frame.setVisible(true);
	}
	
	public void updatePhysics(){
	    for(Physics checking : physics){
	        for(Physics checker : physics){
	            if(checking != checker){
	                boolean[] col = checking.doesCollideWith(checker);
	                checking.physicsCollision(checker, col);
	                boolean[] opcol = {col[2], col[3], col[0], col[1]};
	                checker.physicsCollision(checking, opcol);
                }
            }
        }
	    for(Physics physic : physics){
	        physic.updatePos();
        }
    }
    
    private class GameTickList implements ActionListener {
	    
	    GameComponent component;
	    
	    public GameTickList(GameComponent component){
	        this.component = component;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
	        updatePhysics();
	        component.repaint();
        }
    }
 
}
