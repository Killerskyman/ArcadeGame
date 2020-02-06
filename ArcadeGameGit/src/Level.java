import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Level extends JComponent {
    
    private String filename;
    private BufferedImage background;
    private ArrayList<LevelPlatform> platforms = new ArrayList<>();
    private String heroSpawn;
    private Integer heroSpawnX;
    private Integer heroSpawnY;
    
    public Level(String filename) throws Exception{
        
        BufferedReader sc = new BufferedReader(new FileReader("testLvl.txt"));
        heroSpawn = sc.readLine();
        String[] heroArgs = heroSpawn.split(";");
        heroSpawnX = Integer.parseInt(heroArgs[0]);
        heroSpawnY = Integer.parseInt(heroArgs[1]);
        
        while(true) {
        	String currentPlat = sc.readLine();
        	if(currentPlat == null) break;
        	String[] args =currentPlat.split(";");
        	
        	LevelPlatform current = new LevelPlatform(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        	platforms.add(current);
        }
    }
    
    public void addPlatsToPhysics(ArrayList<Physics> input){
        input.addAll(platforms);
    }
    
    public void removePlatsFromPhysics(ArrayList<Physics> physics){
        physics.removeAll(platforms);
    }
    
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
