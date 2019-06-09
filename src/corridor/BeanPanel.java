package corridor;

import javax.swing.*;
import java.awt.*;

public class BeanPanel extends JPanel {
    private Module selectedModule = null;
    private JPanel modulesPanel;
    private JPanel parametersPanel;

    public BeanPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        modulesPanel = new JPanel();
        modulesPanel.setLayout(new BoxLayout(modulesPanel, BoxLayout.Y_AXIS));
        modulesPanel.add(new Module("Flow", this));
        modulesPanel.add(new Module("Read", this));
        modulesPanel.add(new Module("Convert", this));
        modulesPanel.add(new Module("Save", this));
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
}
