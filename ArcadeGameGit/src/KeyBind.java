import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class KeyBind extends AbstractAction {
    private boolean state;
    private Map<Integer, Boolean> keyMap;
    private Integer keyCode;
    
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
