import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
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
	private ArrayList<Sprite> monsters = new ArrayList<>();
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
	    switchLevel(null, levels.get(0), player);

		GameComponent gamecomp = new GameComponent(physics);
	    ComponentInputMap inputMap = new ComponentInputMap(gamecomp);
	    ActionMap actMap = new ActionMap();
	    makeBinding(KeyEvent.VK_LEFT, player.getAction(UserMovement.ActionListIndex.LEFT.getIndex()), inputMap, actMap);
	    makeBinding(KeyEvent.VK_RIGHT, player.getAction(UserMovement.ActionListIndex.RIGHT.getIndex()), inputMap, actMap);
	    makeBinding(KeyEvent.VK_UP, player.getAction(UserMovement.ActionListIndex.UP.getIndex()), inputMap, actMap);
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
        frame.setSize(1200, 1200);
        frame.setTitle("Milestone 1 Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamecomp, BorderLayout.CENTER);
        Timer timer = new Timer(20, new GameTickList(gamecomp));
        timer.addActionListener(new updateBinds());
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
        if(remove != null) {
            remove.removePlatsFromPhysics(physics);
            for(Sprite monster : monsters){
                destroySprite(monster);
            }
            monsters.clear();
        }
	    add.addPlatsToPhysics(physics);
        for(Point2D spawn : add.getMonsterSpawns()){
            if(Math.random()>0.5){
                spawnSprite(new Monster1(0.5, spawn.getX(), spawn.getY()-50, player));
            }else{
                spawnSprite(new Monster2(0.5, spawn.getX(), spawn.getY()-50, player));
            }
        }
	    add.spawnHero(player);
    }
    
    public void destroySprite(Sprite spriteToDest){
        physics.remove(spriteToDest);
        if(spriteToDest.spawnsSprite){
            removeTimedSpawn(spriteToDest);
        }
    }
    
    public void spawnSprite(Sprite spriteToSpawn){
        physics.add(spriteToSpawn);
        monsters.add(spriteToSpawn);
        if(spriteToSpawn.spawnsSprite){
            makeTimedSpawn(spriteToSpawn, spriteToSpawn.spawnTiming());
        }
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
    
    private HashMap<Sprite, Integer> timedSpawns = new HashMap<>();
    private HashMap<Sprite, Integer> currentTimeSpawns = new HashMap<>();
    private void makeTimedSpawn(Sprite sprite, int cyclesToWait){
        timedSpawns.put(sprite, cyclesToWait);
        currentTimeSpawns.put(sprite, 0);
    }
    
    private void removeTimedSpawn(Sprite spriteToRem){
        timedSpawns.remove(spriteToRem);
        currentTimeSpawns.remove(spriteToRem);
    }
    
    private void updateTimedSpawns(){
        for(Sprite sprite : timedSpawns.keySet()){
            if(timedSpawns.get(sprite) > currentTimeSpawns.get(sprite)){
                currentTimeSpawns.put(sprite, currentTimeSpawns.get(sprite) + 1);
            }else{
                spawnSprite(sprite.spawning());
                currentTimeSpawns.put(sprite, 0);
            }
        }
    }
    
    private void updateDead(){
        for(Sprite sprite : monsters){
            if(sprite.isDead){
                destroySprite(sprite);
            }
        }
        if(((Sprite)physics.get(0)).isDead){
            System.out.println("i died");
        }
    }
    
    /**
     * ActionListener to update all the keybinds made from makeBinding
     */
    private class updateBinds implements ActionListener{
    
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
	        Movement.updateMovement(monsters);
	        updateTimedSpawns();
	        updatePhysics();
	        updateDead();
	        component.repaint();
        }
    }
 
}
