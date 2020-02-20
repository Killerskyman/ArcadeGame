import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Menus{
    
    public static void loadStartMenu(JFrame frame){
        frame.getContentPane().removeAll();
        JPanel menu = new JPanel();
        JButton newGame = new JButton("Start New Game");
        JButton loadGame = new JButton("Load Saved Game");
        JButton viewControls = new JButton("View Controls");
        menu.add(newGame);
        menu.add(loadGame);
        menu.add(viewControls);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setMaximumSize(menu.getPreferredSize());
        frame.add(menu, BorderLayout.SOUTH);
        Game.loadLevels(Game.levels, getAllLevelFileNames("Levels"));
        newGame.addActionListener(e -> {
            Game.playerScore = 0;
            new Game(frame);
        });
        loadGame.addActionListener(e -> {
            loadGameLoadMenu(frame);
        });
        viewControls.addActionListener(e -> {
            loadViewControls(frame);
        });
        frame.repaint();
        frame.setVisible(true);
    }
    
    public static void loadDeathMenu(JFrame frame){
        loadStartMenu(frame);
    }
    
    public static void loadSaveMenu(JFrame frame){
        frame.getContentPane().removeAll();
        JPanel menu = new JPanel();
        JPanel getinfo = new JPanel();
        JFormattedTextField playerName = new JFormattedTextField();
        JButton saveGame = new JButton("Confirm Save Game");
        
    }
    
    private static ArrayList<SaveGame> savedGames = new ArrayList<>();
    private static void loadGameLoadMenu(JFrame frame){
        frame.getContentPane().removeAll();
        JPanel center = new JPanel();
        for(SaveGame game : savedGames){
        }
        JPanel menu = new JPanel();
        makeSubMenu(frame, menu);
    }
    
    private static void loadViewControls(JFrame frame){
        frame.getContentPane().removeAll();
        JPanel menu = new JPanel();
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(new JLabel("Left Arrow Key -> move Left"));
        info.add(new JLabel("Right Arrow Key -> move Right"));
        info.add(new JLabel("Up Arrow Key -> Jump"));
        for(Component component : info.getComponents()) {
            ((JComponent) component).setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        frame.add(info, BorderLayout.CENTER);
        makeSubMenu(frame, menu);
    }
    
    private static void makeSubMenu(JFrame frame, JPanel menu) {
        JButton mainMenu = new JButton("Main Menu");
        menu.add(mainMenu);
        mainMenu.addActionListener(e -> {
            loadStartMenu(frame);
        });
        menu.setMaximumSize(menu.getPreferredSize());
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        frame.add(menu, BorderLayout.SOUTH);
        frame.repaint();
        frame.setVisible(true);
    }
    
    private static void loadSaveGames(String saveGamesFile){
        savedGames.clear();
        try{
            BufferedReader saveGame = new BufferedReader(new FileReader(saveGamesFile));
            String curLine = "";
            while((curLine = saveGame.readLine()) != null){
                String[] savedGame = curLine.split(";");
                savedGames.add(new SaveGame(savedGame[0], Integer.parseInt(savedGame[1]), savedGame[2]));
            }
        }catch(Exception e){
            System.err.println("SAVE GAME FILE NOT FOUND!");
        }
    }
    
    private static ArrayList<String> getAllLevelFileNames(String folderLocation){
        ArrayList<String> output = new ArrayList<>();
        try{
            File foldLoc = new File(folderLocation);
            System.out.println("Level Files Found:");
            for(File file : foldLoc.listFiles()){
                output.add(folderLocation+"/"+file.getName());
                System.out.println("\t"+file.getName());
            }
        }catch(Exception e){
            System.err.println("FAILED TO LOAD LEVELS FROM FOLDER PATH!");
        }
        System.out.println(output.toString());
        return output;
    }
    
    private static class SaveGame{
        public String playerName;
        public int playerScore;
        public String levelFileName;
        public SaveGame(String playerName, int playerScore, String levelFileName){
            this.playerName = playerName;
            this.playerScore = playerScore;
            this.levelFileName = levelFileName;
        }
    }
}
