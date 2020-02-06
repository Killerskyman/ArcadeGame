import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
 
	private ArrayList<Physics> physics = new ArrayList<>();
	private ArrayList<Sprite> sprites = new ArrayList<>();
	private HashMap<Integer, ActionListener> keyActions = new HashMap<>();
	private HashMap<Integer, Boolean> keyStates = new HashMap<>();
	private ArrayList<Level> levels = new ArrayList<>();
	
	public Main(){
	    Hero player = new Hero(0.5, 50, 50);
        physics.add(player);
	    loadLevels(levels, new ArrayList<>(Arrays.asList("testLvl.txt", "New Text Document.txt")));
	    levels.get(0).spawnHero(player);
	    levels.get(0).addPlatsToPhysics(physics);
		GameComponent gamecomp = new GameComponent(physics);
	    ComponentInputMap inputMap = new ComponentInputMap(gamecomp);
	    ActionMap actMap = new ActionMap();
	    makeBinding(KeyEvent.VK_LEFT, player.getMover().getMovers().get(0), inputMap, actMap);
	    makeBinding(KeyEvent.VK_RIGHT, player.getMover().getMovers().get(1), inputMap, actMap);
	    makeBinding(KeyEvent.VK_UP, player.getMover().getMovers().get(2), inputMap, actMap);
	    makeBinding(KeyEvent.VK_U, e -> switchLevel(levels.get(0), levels.get(1), player), inputMap, actMap);
	    makeBinding(KeyEvent.VK_D, e -> switchLevel(levels.get(1), levels.get(0), player), inputMap, actMap);
		gamecomp.setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
		gamecomp.setActionMap(actMap);
        JFrame frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setTitle("physics test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamecomp, BorderLayout.CENTER);
        Timer timer = new Timer(10, new GameTickList(gamecomp));
        timer.addActionListener(new updateMove());
        timer.start();
        frame.setVisible(true);
	}
    
    private void loadLevels(ArrayList<Level> levels, ArrayList<String> strings) {
        for(String filename : strings){
            try{
                levels.add(new Level(filename));
            }catch(Exception e){
                System.err.println("FAILED TO LOAD LEVEL FROM FILE: " + filename);
            }
        }
    }
    
    public void switchLevel(Level remove, Level add, Hero player){
	    remove.removePlatsFromPhysics(physics);
	    add.addPlatsToPhysics(physics);
	    add.spawnHero(player);
    }
	
	public void updatePhysics(){
	    for(Physics checking : physics){
	        boolean shouldNotFall = false;
	        for(Physics checker : physics){
	            if(checking != checker){
	                boolean[] col = checking.doesCollideWith(checker);
	                shouldNotFall = shouldNotFall || !checking.physicsCollision(checker, col);
	                boolean[] opcol = {col[2], col[3], col[0], col[1]};
	                checker.physicsCollision(checking, opcol);
                }
            }
	        checking.setFalling(!shouldNotFall);
        }
	    for(Physics physic : physics){
	        physic.updatePos();
        }
    }
    
    private void makeBinding(Integer keyCode, ActionListener action, InputMap inputMap, ActionMap actionMap){
	    KeyStroke keyPress = KeyStroke.getKeyStroke(keyCode, 0, false);
	    KeyStroke keyRel = KeyStroke.getKeyStroke(keyCode, 0, true);
	    inputMap.put(keyPress, keyCode.toString()+" pressed");
	    inputMap.put(keyRel, keyCode.toString()+" released");
	    actionMap.put(keyCode.toString()+ " pressed", new KeyBind(keyCode, keyStates, true));
        actionMap.put(keyCode.toString()+ " released", new KeyBind(keyCode, keyStates, false));
        keyActions.put(keyCode, action);
        keyStates.put(keyCode, false);
    }
    
    private class updateMove implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            for(Integer keyAction : keyActions.keySet()){
                if(keyStates.get(keyAction))keyActions.get(keyAction).actionPerformed(e);
            }
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
