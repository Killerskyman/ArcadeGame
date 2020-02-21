import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main class, runs and setups up all the objects required to run the game
 * 
 * @author Skyler Cleland and Daniel Vega
 *
 */
public class Game {
    
    private static final int INITSCREENSIZEWIDTH = 1920, INITSCREENSIZEHEIGHT = 1080;
    private static final int PLAYERHEALTH = 3;
    private static final double GRAVITY = 0.5;
    private static final double PLAYERSIZE = 50;
    private static final int GAMETICKSPEED = 20;

    private Menus menu;
	//TODO: adjust init values of arraylists as program expands
	private ArrayList<Physics> physics = new ArrayList<>(15);
	private ArrayList<Sprite> monsters = new ArrayList<>();
	private HashMap<Integer, ActionListener> keyActions = new HashMap<>(5);
	private HashMap<Integer, Boolean> keyStates = new HashMap<>(5);
	public static ArrayList<Level> levels = new ArrayList<>(10);
	public int currentLevel;
	public int playerScore;
	private Hero player;
    private JFrame frame;
    private JLabel gameScore;
    private Timer timer;
    
    /**
     * sets the game up by loading levels from files, spawning the player, binding the keys, and setting up physics relations
     * @param frame the JFrame to display in
     */
	public Game(JFrame frame, Menus menu, String firstLevelFileName){
        this(frame, menu, firstLevelFileName, 0, PLAYERHEALTH);
	}

	public Game(JFrame frame, Menus menu, SaveGame game){
	    this(frame, menu, game.levelFileName, game.playerScore, game.playerHealth);
    }
    
    /**
     * sets the game up with some initial values
     * @param frame the JFrame to display in
     * @param levelFileName the filename of the initial level to start in
     * @param playerScore initial player score
     * @param playerHealth initial player health
     */
	public Game(JFrame frame, Menus menu, String levelFileName, int playerScore, int playerHealth){
        frame.getContentPane().removeAll();
        this.playerScore = playerScore;
	    this.frame = frame;
	    this.menu = menu;
	    player = new Hero(GRAVITY, PLAYERSIZE, PLAYERSIZE);
	    player.health = playerHealth;
        physics.add(player);
        physics.add(new LevelPlatform(-20,0,20,INITSCREENSIZEHEIGHT+40));
        physics.add(new LevelPlatform(INITSCREENSIZEWIDTH, 0, 20, INITSCREENSIZEHEIGHT+40));
        physics.add(new LevelPlatform(-20,-20, INITSCREENSIZEWIDTH+40, 20));
        physics.add(new LevelPlatform(-20, INITSCREENSIZEHEIGHT-10, INITSCREENSIZEWIDTH+40, 40));
        currentLevel = findLevelIndex(levels, levelFileName);
	    switchLevel(null, levels.get(currentLevel), player);

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
                if(e.getKeyCode() == KeyEvent.VK_U) moveUpLevel(player);
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
                if(e.getKeyCode() == KeyEvent.VK_D) moveDownLevel(player);
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
            
            }
        });
	    gamecomp.setFocusable(true);
	    
        gameScore = new JLabel("Player Score: ");
        
        JButton saveGame = new JButton("Save Game");
        saveGame.addActionListener(e -> {
            timer.stop();
            menu.loadSaveMenu(frame, Game.levels.get(this.currentLevel).filename, player.getHealth());
        });
        frame.add(gamecomp, BorderLayout.CENTER);
        JPanel north = new JPanel();
        north.add(saveGame);
        north.add(gameScore);
        frame.add(north, BorderLayout.NORTH);
        frame.repaint();
        frame.setVisible(true);
        gamecomp.requestFocus();
        timer = new Timer(GAMETICKSPEED, new GameTickList(gamecomp));
        timer.addActionListener(new updateBinds());
        timer.addActionListener(e -> {
            gameScore.setText(gameScore.getText().split(":")[0] + ":  " + getPlayerScore());
            gameScore.repaint();
        });
        timer.start();
	}
	
	public int getPlayerScore(){
	    return playerScore;
    }
    
    /**
     * loads the levels into the levels arrayList from the ArrayList of Strings
     * @param levels arraylist to add the levels to
     * @param strings arraylist contain all the filenames of the level txt files
     */
    public static void loadLevels(ArrayList<Level> levels, ArrayList<String> strings) {
        levels.clear();
        System.out.println("Levels Loaded from files: ");
        for(String filename : strings){
            try{
                levels.add(new Level(filename));
                System.out.println("\t"+filename);
            }catch(Exception e){
                System.err.println("FAILED TO LOAD LEVEL FROM FILE: " + filename);
            }
        }
    }
    
    /**
     * returns the index of the level int levels list based on the filename
     * @param levels ArrayList to search through
     * @param filename filename to find index for
     * @return the index of the level in the ArrayList (-1 if not found)
     */
    private static int findLevelIndex(ArrayList<Level> levels, String filename){
        for(Level level : levels){
            if(level.filename.equals(filename)){
                return levels.indexOf(level);
            }
        }
        return -1;
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
            for(Sprite monster : new ArrayList<>(monsters)){
                destroySprite(monster);
            }
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

    public void moveUpLevel(Hero player){
        if(currentLevel < levels.size()-1){
            switchLevel(levels.get(currentLevel), levels.get(currentLevel+1), player);
            currentLevel++;
        }
    }

    public void moveDownLevel(Hero player){
        if(currentLevel > 0){
            switchLevel(levels.get(currentLevel), levels.get(currentLevel-1), player);
            currentLevel--;
        }
    }
    
    /**
     * kills the sprite
     *
     * removes the sprite from the necessary arraylists and spawns the new sprite upon death if needed
     * @param spriteToKill
     */
    private void killSprite(Sprite spriteToKill){
        destroySprite(spriteToKill);
        Sprite spriteToSpawn = spriteToKill.death();
        if(spriteToSpawn==null && spriteToKill.getJoustHeight()>2000) playerScore++;
        spawnSprite(spriteToSpawn);
    }
    
    /**
     * only removes the sprite from the necessary arraylists,
     * @param spriteToDest
     */
    private void destroySprite(Sprite spriteToDest){
        physics.remove(spriteToDest);
        monsters.remove(spriteToDest);
        if(spriteToDest.spawnsSprite){
            removeTimedSpawn(spriteToDest);
        }
    }
    
    /**
     * adds the sprite to the necessary arraylists and spawnings, may be null
     * @param spriteToSpawn
     */
    private void spawnSprite(Sprite spriteToSpawn){
        if(spriteToSpawn == null) return;
        physics.add(spriteToSpawn);
        monsters.add(spriteToSpawn);
        if(spriteToSpawn.spawnsSprite){
            makeTimedSpawn(spriteToSpawn, spriteToSpawn.spawnTiming());
        }
    }
    
    /**
     * updates all objects in the physics arraylist by checking for collisions and updating their position
     */
	private void updatePhysics(){
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
    
    /**
     * creates a new timed spawn to check by adding the proper vales to the hashmaps
     * @param sprite sprite that needs to spawn things
     * @param cyclesToWait how many cycles to wait to spawn said thing
     */
    private void makeTimedSpawn(Sprite sprite, int cyclesToWait){
        timedSpawns.put(sprite, cyclesToWait);
        currentTimeSpawns.put(sprite, 0);
    }
    
    /**
     * removes a timed spawn if it exists from the proper hashmaps
     * @param spriteToRem
     */
    private void removeTimedSpawn(Sprite spriteToRem){
        timedSpawns.remove(spriteToRem);
        currentTimeSpawns.remove(spriteToRem);
    }
    
    /**
     * updates any timed spawns that are in the hashmaps
     */
    private void updateTimedSpawns(){
        ArrayList<Sprite> spritesToSpawn = new ArrayList<>();
        for(Sprite sprite : timedSpawns.keySet()){
            if(timedSpawns.get(sprite) > currentTimeSpawns.get(sprite)){
                currentTimeSpawns.put(sprite, currentTimeSpawns.get(sprite) + 1);
            }else{
                spritesToSpawn.add(sprite);
            }
        }
        for(Sprite sprite : spritesToSpawn) {
            spawnSprite(sprite.spawning());
            currentTimeSpawns.put(sprite, 0);
        }
    }
    
    /**
     * removes any monsters that are marked with isDead and marks if the hero has died and takes appropriate action
     */
    private void updateDead(){
        ArrayList<Sprite> spritesToRem = new ArrayList<>();
        for(Sprite sprite : monsters){
            if(sprite.isDead){
                spritesToRem.add(sprite);
            }
        }
        for(Sprite sprite : spritesToRem) {
            killSprite(sprite);
        }
        if(monsters.size()==0){
            moveUpLevel(player);
        }
        if(player.isDead){
            timer.stop();
            player.health = player.health - 1;
            if(player.health <= 0){
                menu.loadDeathMenu(frame);
            }else {
                switchLevel(levels.get(currentLevel), levels.get(currentLevel), player);
                player.isDead = false;
                timer.start();
            }
        }
    }

    /**
     * used to store info for saving/loading a game
     */
    public static class SaveGame{
        public String playerName;
        public int playerScore;
        public String levelFileName;
        public int playerHealth;
        public SaveGame(String playerName, int playerScore, String levelFileName, int playerHealth){
            this.playerName = playerName;
            this.playerScore = playerScore;
            this.levelFileName = levelFileName;
            this.playerHealth = playerHealth;
        }

        /**
         * loads all the saveGames into the ArrayList from the specified file (clears before loading)
         * @param savedGames arrayList to load saveGames into
         * @param saveGamesFile file to load from
         */
        static void loadSaveGames(ArrayList<SaveGame> savedGames, String saveGamesFile){
            savedGames.clear();
            try{
                BufferedReader saveGame = new BufferedReader(new FileReader(saveGamesFile));
                String curLine;
                while((curLine = saveGame.readLine()) != null){
                    String[] savedGame = curLine.split(";");
                    savedGames.add(new SaveGame(savedGame[0], Integer.parseInt(savedGame[1]), savedGame[2], Integer.parseInt(savedGame[3])));
                }
                saveGame.close();
            }catch(Exception e){
                System.err.println("SAVE GAME FILE NOT FOUND!");
            }
        }

        /**
         * saves the saveGame to the specified file
         * @param saveFileName file to save to
         * @param game the SaveGame to pull information from
         */
        static void addSaveToSaveGame(String saveFileName, SaveGame game){
            try {
                FileWriter saves = new FileWriter(saveFileName, true);
                saves.write(game.playerName);
                saves.write(";");
                saves.write(String.valueOf(game.playerScore));
                saves.write(";");
                saves.write(game.levelFileName);
                saves.write(";");
                saves.write(String.valueOf(game.playerHealth));
                saves.write("\r\n");
                saves.close();
            }catch(IOException e) {
                e.printStackTrace();
            }
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
