import java.util.ArrayList;

/**
 * TODO:make a good comment here
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
 
	ArrayList<Physics> physics = new ArrayList<>();
	ArrayList<Sprite> sprites = new ArrayList<>();
	
	public void Main(){
	    physics.add(new LevelPlatform(0, 300, 1000, 30));
	    physics.add(new Hero(-2, 50, 50));
	}
 
}
