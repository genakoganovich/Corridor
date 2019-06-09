package corridor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Module extends JLabel {
    private BeanPanel owner;
    public Module(String text, BeanPanel owner) {
        super(text);
        addMouseListener(new ModuleMouseListener());
        this.owner = owner;
    }
    private void toggleBold() {
        Font f = getFont();
        setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
    }
    private class ModuleMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                System.out.println("double clicked");
            } else {
                System.out.println("clicked");
            }
            Module selected = owner.getSelectedModule();
            if (selected != null) {
                selected.toggleBold();
            }
            owner.setSelectedModule(Module.this);
            toggleBold();
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
