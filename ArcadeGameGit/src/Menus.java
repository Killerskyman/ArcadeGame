import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * holds all the methods needed to load the menus for the game and navigate between them
 * it also handles loading in information about previous games from files
 */
public class Menus{

    private static final int INITSCREENSIZEWIDTH = 1920, INITSCREENSIZEHEIGHT = 1080;
    private Game gameMain;

    public static void main(String[] args) {
        JFrame frame = new JFrame("The Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(INITSCREENSIZEWIDTH/2, INITSCREENSIZEHEIGHT/2);
        Menus menu = new Menus();
        menu.loadStartMenu(frame);
    }
    
    /**
     * sets up the start menu overtop anything else the given frame
     * @param frame frame to display to
     */
    public void loadStartMenu(JFrame frame){
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
        newGame.addActionListener(e -> gameMain = new Game(frame, this, Game.levels.get(0).filename));
        loadGame.addActionListener(e -> loadGameLoadMenu(frame));
        viewControls.addActionListener(e -> loadViewControls(frame));
        frame.repaint();
        frame.setVisible(true);
    }
    
    /**
     * sets up the death menu
     * @param frame frame to display to
     */
    public void loadDeathMenu(JFrame frame){
        frame.getContentPane().removeAll();
        loadLeaderBoard("leaderBoard.txt");
        JPanel menu = new JPanel();
        JTextField playerName = new JTextField(30);
        JButton addLeader = new JButton("Add To LeaderBoard");
        addLeader.addActionListener(e -> {
            LeaderBoard.insertEntryToLeaderBoard(leaderBoards, new LeaderBoard(playerName.getText(), gameMain.getPlayerScore()));
            saveLeaderBoard("leaderBoard.txt");
            loadStartMenu(frame);
        });
        JPanel info = new JPanel();
        JLabel death = new JLabel("YOU DIED!");
        JLabel score = new JLabel("Score: "+ gameMain.getPlayerScore());
        death.setAlignmentX(Component.CENTER_ALIGNMENT);
        score.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(death);
        info.add(score);
        info.add(playerName);
        menu.add(addLeader);
        frame.add(info, BorderLayout.NORTH);
        JPanel center = new JPanel();
        LeaderBoard.addLeaderBoardToJpanel(leaderBoards, center);
        frame.add(center, BorderLayout.CENTER);
        makeSubMenu(frame, menu);
    }

    private ArrayList<LeaderBoard> leaderBoards = new ArrayList<>();
    
    /**
     * loads the information from the specified file into the leaderBoard ArrayList
     * @param leaderBoardFile file to load from
     */
    public void loadLeaderBoard(String leaderBoardFile){
        leaderBoards.clear();
        try{
            BufferedReader boardEntry = new BufferedReader(new FileReader(leaderBoardFile));
            String curLine;
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
     * @param leaderBoardFile file to load leader Board info from
     */
    public void saveLeaderBoard(String leaderBoardFile){
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
    public void loadSaveMenu(JFrame frame, String levelFileName, int playerHealth){
        frame.getContentPane().removeAll();
        JPanel menu = new JPanel();
        JPanel getinfo = new JPanel();
        JTextField playerName = new JTextField(50);
        JButton saveGame = new JButton("Confirm Save Game");
        saveGame.addActionListener(e -> {
            Game.SaveGame.addSaveToSaveGame("saveGames.txt", new Game.SaveGame(playerName.getText(), gameMain.getPlayerScore(), levelFileName, playerHealth));
            loadStartMenu(frame);
        });
        getinfo.add(playerName);
        getinfo.add(saveGame);
        frame.add(getinfo, BorderLayout.CENTER);
        makeSubMenu(frame, menu);
    }

    private ArrayList<Game.SaveGame> savedGames = new ArrayList<>();
    
    /**
     * load the menu that is used for loading a game
     * @param frame frame to display to
     */
    private void loadGameLoadMenu(JFrame frame){
        Game.SaveGame.loadSaveGames(savedGames, "saveGames.txt");
        frame.getContentPane().removeAll();
        JPanel center = new JPanel();
        int saveCount = 1;
        for(Game.SaveGame game : savedGames){
            JPanel row = new JPanel();
            row.add(new JLabel(String.valueOf(saveCount)));
            row.add(new JLabel(game.playerName));
            row.add(new JLabel(String.valueOf(game.playerScore)));
            JButton loadGame = new JButton("Load Game");
            loadGame.addActionListener(e -> this.gameMain = new Game(frame, this, game));
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
    private void loadViewControls(JFrame frame){
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
    private void makeSubMenu(JFrame frame, JPanel menu) {
        JButton mainMenu = new JButton("Main Menu");
        menu.add(mainMenu);
        mainMenu.addActionListener(e -> loadStartMenu(frame));
        menu.setMaximumSize(menu.getPreferredSize());
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        frame.add(menu, BorderLayout.SOUTH);
        frame.repaint();
        frame.setVisible(true);
    }

    /**
     * returns all the filenames in the specified folder
     *
     * used to load all the levels in from the Levels folder
     * @param folderLocation folder location
     * @return ArrayList of filenames
     */
    private ArrayList<String> getAllLevelFileNames(String folderLocation){
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

        /**
         * adds the leaderBoard and all its info to the JPanel
         * @param leaderBoards all leaderBoard entrys to add to the JPanel
         * @param panel panel to add info to
         */
        private static void addLeaderBoardToJpanel(ArrayList<LeaderBoard> leaderBoards, JPanel panel){
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

        /**
         * inserts the given entry into the leaderBoard ArrayList in a sorted manner
         * @param leaderBoards ArrayList to add entry to
         * @param entry the LeaderBoard to add
         */
        private static void insertEntryToLeaderBoard(ArrayList<LeaderBoard> leaderBoards, LeaderBoard entry){
            for(int i = 0, size = leaderBoards.size(); i < size; i++) {
                if(leaderBoards.get(i).playerScore < entry.playerScore){
                    leaderBoards.add(i, entry);
                    return;
                }
            }
            leaderBoards.add(entry);
        }
    }

}
