import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * holds all the relevant information to a level and automagically loads it from a filename
 */
public class Level extends JComponent {
    
    private String filename;
    private BufferedImage background;
    private ArrayList<LevelPlatform> platforms = new ArrayList<>();
    private ArrayList<Point2D> monsterSpawns = new ArrayList<>();
    private String heroSpawn;
    private Integer heroSpawnX;
    private Integer heroSpawnY;
    
    /**
     * make a new level from the file named filename
     * @param filename filename that contains the level information
     * @throws Exception throws multiple exceptions
     */
    public Level(String filename) throws Exception{
        
        BufferedReader sc = new BufferedReader(new FileReader(filename));
        heroSpawn = sc.readLine();
        String[] heroArgs = heroSpawn.split(";");
        //first line is the hero spawn location
        heroSpawnX = Integer.parseInt(heroArgs[0]);
        heroSpawnY = Integer.parseInt(heroArgs[1]);
        
        while(true) {
        	String currentPlat = sc.readLine(); //read the next line
        	if(currentPlat == null) break; //if it's null break
        	String[] args =currentPlat.split(";");
        	//platform position x; platform position y; platform width; platform height;
        	LevelPlatform current = new LevelPlatform(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        	platforms.add(current);
        	if(Boolean.parseBoolean(args[4])){
        	    monsterSpawns.add(new Point2D.Double(current.getX()+Double.parseDouble(args[5]), current.getY()));
            }
        }
    }
    
    /**
     * adds the platforms owned by this level into the physics array
     * @param input the array to add the platforms to
     */
    public void addPlatsToPhysics(ArrayList<Physics> input){
        if(input.contains(platforms.get(0))) return;
        input.addAll(platforms);
    }
    
    /**
     * removes the platforms owned by this level from the specified array
     * @param physics array to remove from
     */
    public void removePlatsFromPhysics(ArrayList<Physics> physics){
        for(Physics plat : platforms){
            physics.remove(plat);
        }
    }
    
    public ArrayList<Point2D> getMonsterSpawns(){
        return monsterSpawns;
    }
    
    /**
     * sets the spawn point of the hero and resets vely
     * @param player the hero to spawn
     */
    public void spawnHero(Hero player) {
    	player.setX(heroSpawnX);
    	player.setY(heroSpawnY);
    	player.setFalling(false);
    	player.updatePos(0);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
    }
}
