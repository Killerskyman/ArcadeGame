import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Level extends JComponent {
    
    private String filename;
    private BufferedImage background;
    private ArrayList<LevelPlatform> platforms = new ArrayList<>();
    
    public Level(String filename){
        File newFile = new File("testLvl.txt");
        try {
            newFile.createNewFile();
        }catch(IOException e) {
            e.printStackTrace();
        }
    
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
    }
}
