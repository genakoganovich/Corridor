package corridor;

import javax.swing.*;

public class CorridorFrame extends JFrame{
    public CorridorFrame() {
        //add(new CorridorPanel());
        add(new BeanPanel());
        pack();
    }
}
