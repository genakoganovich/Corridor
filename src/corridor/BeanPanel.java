package corridor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BeanPanel extends JPanel {
    private Module selectedModule = null;
    private JPanel modulesPanel;
    private JPanel parametersPanel;

    public BeanPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        modulesPanel = new JPanel();
        modulesPanel.setLayout(new BoxLayout(modulesPanel, BoxLayout.Y_AXIS));
        modulesPanel.add(new Module("Flow", this, new ModuleMouseListener()));
        modulesPanel.add(new Module("Read", this, new ModuleMouseListener()));
        modulesPanel.add(new Module("Convert", this, new ModuleMouseListener()));
        modulesPanel.add(new Module("Save", this, new ModuleMouseListener()));
        parametersPanel = new JPanel();
        setBorder(BorderFactory.createLineBorder(Color.black));
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, modulesPanel, parametersPanel);
        add(splitPane);
    }
    public void setSelectedModule(Module module) {
        selectedModule = module;
    }
    public Module getSelectedModule() {
        return selectedModule;
    }

    private class ModuleMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                System.out.println("double clicked");
            } else {
                System.out.println("clicked");
            }
            Module selected = getSelectedModule();
            if (selected != null) {
                selected.toggleBold();
            }
            setSelectedModule((Module) e.getSource());
            selected.toggleBold();
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
