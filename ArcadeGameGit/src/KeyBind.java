import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * class is for binding keys by updating a Map that holds the keyCode and whether the button is pressed
 */
public class KeyBind extends AbstractAction {
    private boolean state;
    private Map<Integer, Boolean> keyMap;
    private Integer keyCode;
    
    /**
     * creates half of a keyAction
     * @param keyCode int code of the button to listen to
     * @param keyMap the current map that stores the boolean of whether the key is pressed
     * @param state the state that this action should set the boolean to
     */
    public KeyBind(Integer keyCode, Map<Integer, Boolean> keyMap, Boolean state){
        this.keyCode = keyCode;
        this.keyMap = keyMap;
        this.state = state;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        keyMap.put(keyCode, state);
    }
}
