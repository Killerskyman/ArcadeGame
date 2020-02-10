import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Main class, runs and setups up all the objects required to run the game
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

	//TODO: adjust init values of arraylists as program expands
	private ArrayList<Physics> physics = new ArrayList<>(15);
	private ArrayList<Sprite> sprites = new ArrayList<>();
	private HashMap<Integer, ActionListener> keyActions = new HashMap<>(5);
	private HashMap<Integer, Boolean> keyStates = new HashMap<>(5);
	private ArrayList<Level> levels = new ArrayList<>(3);
    
    /**
     * sets the game up by loading levels from files, spawning the player, binding the keys, and setting up physics relations
     */
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
        gamecomp.setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        gamecomp.setActionMap(actMap);

	    gamecomp.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            
            }
        
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_U) switchLevel(levels.get(0), levels.get(1), player);
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
            
            }
        });
	    gamecomp.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            
            }
        
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_D) switchLevel(levels.get(1), levels.get(0), player);
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
            
            }
        });
	    gamecomp.setFocusable(true);

        JFrame frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setTitle("Milestone 1 Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamecomp, BorderLayout.CENTER);
        Timer timer = new Timer(20, new GameTickList(gamecomp));
        timer.addActionListener(new updateMove());
        timer.start();
        frame.setVisible(true);
	}
    
    /**
     * loads the levels into the levels arrayList from the ArrayList of Strings
     * @param levels arraylist to add the levels to
     * @param strings arraylist contain all the filenames of the level txt files
     */
    private void loadLevels(ArrayList<Level> levels, ArrayList<String> strings) {
        for(String filename : strings){
            try{
                levels.add(new Level(filename));
            }catch(Exception e){
                System.err.println("FAILED TO LOAD LEVEL FROM FILE: " + filename);
            }
        }
    }
    
    /**
     * switches between the remove level and the add level
     * @param remove level to remove from the screen
     * @param add level to add to the screen
     * @param player the player, used for spawning at correct location
     */
    public void switchLevel(Level remove, Level add, Hero player){
	    remove.removePlatsFromPhysics(physics);
	    add.addPlatsToPhysics(physics);
	    add.spawnHero(player);
    }
    
    /**
     * updates all objects in the physics arraylist by checking for collisions and updating their position
     */
	public void updatePhysics(){
	    Physics.updatePhysics(physics);
    }
    
    /**
     * makes a binding to a key that activates the action while the button is pressed
     * @param keyCode keyCode of the button you want to bind
     * @param action ActionListener that you want to activate while button is pressed
     * @param inputMap the inputMap to add the keyBind to
     * @param actionMap the actionMap to add the keyBind to
     */
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
    
    /**
     * ActionListener to update all the keybinds made from makeBinding
     */
    private class updateMove implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            for(Integer keyAction : keyActions.keySet()){
                if(keyStates.get(keyAction))keyActions.get(keyAction).actionPerformed(e);
            }
        }
    }
    
    /**
     * ActionListener that ticks the game forward
     */
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
