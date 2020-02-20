import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * holds all the methods needed to load the menus for the game and navigate between them
 * it also handles loading in information about previous games from files
 */
public class Menus{
    
    /**
     * sets up the start menu overtop anything else the given frame
     * @param frame frame to display to
     */
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
    
    /**
     * sets up the death menu
     * @param frame frame to display to
     */
    public static void loadDeathMenu(JFrame frame){
        frame.getContentPane().removeAll();
        loadLeaderBoard("leaderBoard.txt");
        JPanel menu = new JPanel();
        JTextField playerName = new JTextField(30);
        JButton addLeader = new JButton("Add To LeaderBoard");
        addLeader.addActionListener(e -> {
            insertEntryToLeaderBoard(new LeaderBoard(playerName.getText(), Game.playerScore));
            saveLeaderBoard("leaderBoard.txt");
            loadStartMenu(frame);
        });
        JPanel info = new JPanel();
        JLabel death = new JLabel("YOU DIED!");
        JLabel score = new JLabel("Score: "+Game.getPlayerScore());
        death.setAlignmentX(Component.CENTER_ALIGNMENT);
        score.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(death);
        info.add(score);
        info.add(playerName);
        menu.add(addLeader);
        frame.add(info, BorderLayout.NORTH);
        JPanel center = new JPanel();
        addLeaderBoardToJpanel(center);
        frame.add(center, BorderLayout.CENTER);
        makeSubMenu(frame, menu);
    }
    
    /**
     * inserts the given entry into the leaderBoard ArrayList in a sorted manner
     * @param entry the LeaderBoard to add
     */
    private static void insertEntryToLeaderBoard(LeaderBoard entry){
        for(int i = 0, size = leaderBoards.size(); i < size; i++) {
            if(leaderBoards.get(i).playerScore < entry.playerScore){
                leaderBoards.add(i, entry);
                return;
            }
        }
        leaderBoards.add(entry);
    }
    
    /**
     * adds the leaderBoard and all its info to the JPanel
     * @param panel panel to add info to
     */
    private static void addLeaderBoardToJpanel(JPanel panel){
        int saveCount = 1;
        for(LeaderBoard game : leaderBoards){
            JPanel row = new JPanel();
            row.add(new JLabel(String.valueOf(saveCount)));
            row.add(new JLabel(game.playerName));
            row.add(new JLabel(String.valueOf(game.playerScore)));
            row.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(row);
            saveCount++;
        }
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }
    
    private static ArrayList<LeaderBoard> leaderBoards = new ArrayList<>();
    
    /**
     * loads the information from the specified file into the leaderBoard ArrayList
     * @param leaderBoardFile file to load from
     */
    public static void loadLeaderBoard(String leaderBoardFile){
        leaderBoards.clear();
        try{
            BufferedReader boardEntry = new BufferedReader(new FileReader(leaderBoardFile));
            String curLine = "";
            while((curLine = boardEntry.readLine()) != null){
                String[] entry = curLine.split(";");
                leaderBoards.add(new LeaderBoard(entry[0], Integer.parseInt(entry[1])));
            }
            boardEntry.close();
        }catch(Exception e){
            System.err.println("LEADERBOARD FILE NOT FOUND!");
        }
    }
    
    /**
     * saves all information in the leaderBoard ArrayList to the specified file
     * @param leaderBoardFile
     */
    public static void saveLeaderBoard(String leaderBoardFile){
        try {
            FileWriter saves = new FileWriter(leaderBoardFile);
            for(LeaderBoard entry : leaderBoards) {
                saves.write(entry.playerName);
                saves.write(";");
                saves.write(String.valueOf(entry.playerScore));
                saves.write("\r\n");
            }
            saves.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * loads the menu to save a game from
     * @param frame frame to display to
     * @param levelFileName current level based on the filename
     * @param playerHealth current player health
     */
    public static void loadSaveMenu(JFrame frame, String levelFileName, int playerHealth){
        frame.getContentPane().removeAll();
        JPanel menu = new JPanel();
        JPanel getinfo = new JPanel();
        JTextField playerName = new JTextField(50);
        JButton saveGame = new JButton("Confirm Save Game");
        saveGame.addActionListener(e -> {
            addSaveToSaveGame("saveGames.txt", new SaveGame(playerName.getText(), Game.playerScore, levelFileName, playerHealth));
            loadStartMenu(frame);
        });
        getinfo.add(playerName);
        getinfo.add(saveGame);
        frame.add(getinfo, BorderLayout.CENTER);
        makeSubMenu(frame, menu);
    }
    
    /**
     * saves the saveGame to the specified file
     * @param saveFileName file to save to
     * @param game the SaveGame to pull information from
     */
    private static void addSaveToSaveGame(String saveFileName, SaveGame game){
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
    
    private static ArrayList<SaveGame> savedGames = new ArrayList<>();
    
    /**
     * load the menu that is used for loading a game
     * @param frame frame to display to
     */
    private static void loadGameLoadMenu(JFrame frame){
        loadSaveGames("saveGames.txt");
        frame.getContentPane().removeAll();
        JPanel center = new JPanel();
        int saveCount = 1;
        for(SaveGame game : savedGames){
            JPanel row = new JPanel();
            row.add(new JLabel(String.valueOf(saveCount)));
            row.add(new JLabel(game.playerName));
            row.add(new JLabel(String.valueOf(game.playerScore)));
            JButton loadGame = new JButton("Load Game");
            loadGame.addActionListener(e -> new Game(frame, game.levelFileName, game.playerScore, game.playerHealth));
            row.add(loadGame);
            row.setAlignmentX(Component.CENTER_ALIGNMENT);
            center.add(row);
            saveCount++;
        }
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        frame.add(center, BorderLayout.CENTER);
        JPanel menu = new JPanel();
        makeSubMenu(frame, menu);
    }
    
    /**
     * loads the menu where you can see what buttons do
     * @param frame frame to display to
     */
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
    
    /**
     * makes the back button and organizes the menu items in the specified Panel
     * @param frame frame to display to
     * @param menu panel to organize items into a menu
     */
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
    
    /**
     * loads all the saveGames into the ArrayList from the specified file (clears before loading)
     * @param saveGamesFile file to load from
     */
    private static void loadSaveGames(String saveGamesFile){
        savedGames.clear();
        try{
            BufferedReader saveGame = new BufferedReader(new FileReader(saveGamesFile));
            String curLine = "";
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
     * returns all the filenames in the specified folder
     *
     * used to load all the levels in from the Levels folder
     * @param folderLocation folder location
     * @return ArrayList of filenames
     */
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
    
    /**
     * used to store info for the LeaderBoard
     */
    private static class LeaderBoard{
        public String playerName;
        public int playerScore;
        public LeaderBoard(String playerName, int playerScore){
            this.playerName = playerName;
            this.playerScore = playerScore;
        }
    }
    
    /**
     * used to store info for saving/loading a game
     */
    private static class SaveGame{
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
    }
}
