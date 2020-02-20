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
    
    public String filename;
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
        this.filename = filename;
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
     */
    public void addPlatsToPhysics(){
        for(Physics2 plats : platforms){
            Physics2.addToCollision(plats);
        }
    }
    
    /**
     * removes the platforms owned by this level from the specified array
     */
    public void removePlatsFromPhysics(){
        for(Physics2 plat : platforms){
            Physics2.removeCollision(plat);
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
    	player.updatePos(0);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
    }
    
    @Override
    public String toString() {
        return "Level{" +
                       "filename='" + filename + '\'' +
                       ", platforms=" + platforms +
                       ", monsterSpawns=" + monsterSpawns +
                       ", heroSpawn='" + heroSpawn + '\'' +
                       ", heroSpawnX=" + heroSpawnX +
                       ", heroSpawnY=" + heroSpawnY +
                       '}';
    }
}
