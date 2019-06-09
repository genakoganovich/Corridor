package corridor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Module extends JLabel {
    private BeanPanel owner;
    public Module(String text, BeanPanel owner, MouseListener mouseListener) {
        super(text);
        addMouseListener(mouseListener);
        this.owner = owner;
    }
    public void toggleBold() {
        Font f = getFont();
        setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
    }

}
