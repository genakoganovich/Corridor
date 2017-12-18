package corridor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

enum LayoutType {
    Flow, Box_X_AXIS, Box_Y_AXIS
}

class CorridorPanel extends JPanel {
    private final static int ROWS = 10;
    private final static int COLUMNS = 20;
    private JTextArea inputHeaderTextArea;
    private JTextArea outputHeaderTextArea;
    private JScrollPane inputTableScrollPane;
    private JScrollPane outputTableScrollPane;
    private JTable inputTable;
    private JTable outputTable;
    private JTextField inputFile;
    private JButton browseButton;
    private JButton testButton;
    private JComboBox<String> inputFormatComboBox;
    private JComboBox<String> outputFormatComboBox;
    private Model model;

    CorridorPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        inputFile = new JTextField(COLUMNS);
        browseButton = new JButton("...");
        model = new Model();
        inputFormatComboBox = new JComboBox<>(model.xmlParser.getCorridors());
        outputFormatComboBox = new JComboBox<>(model.xmlParser.getCorridors());
        testButton = new JButton("test");
        browseButton.addActionListener(new BrowseButtonListener());
        testButton.addActionListener(new TestListener());
        JPanel fileNamePanel = createPanel(LayoutType.Flow, new Component[]{inputFile, browseButton,
                inputFormatComboBox, outputFormatComboBox, testButton});

        inputHeaderTextArea = createTextArea();
        outputHeaderTextArea = createTextArea();

        JPanel headersPanel = createPanel(LayoutType.Box_X_AXIS,
                new Component[]{new JScrollPane(inputHeaderTextArea), new JScrollPane(outputHeaderTextArea)});

        JSplitPane headerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fileNamePanel, headersPanel);

        inputTable = new JTable();
        inputTableScrollPane = new JScrollPane(inputTable);
        outputTable = new JTable();
        outputTableScrollPane = new JScrollPane(outputTable);

        JPanel tablePanel = createPanel(LayoutType.Box_X_AXIS,
                new Component[]{inputTableScrollPane, outputTableScrollPane});
        add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, headerSplitPane, tablePanel));

        synchronizeTableScrollBars();
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea(ROWS, COLUMNS);
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        return textArea;
    }

    private JPanel createPanel(LayoutType layoutType, Component[] components) {
        JPanel panel = new JPanel();
        LayoutManager layoutManager = null;
        switch (layoutType) {
            case Flow:
                layoutManager = new FlowLayout(FlowLayout.LEFT);
                break;
            case Box_X_AXIS:
                layoutManager = new BoxLayout(panel, BoxLayout.X_AXIS);
                break;
            case Box_Y_AXIS:
                layoutManager = new BoxLayout(panel, BoxLayout.Y_AXIS);
                break;
        }
        panel.setLayout(layoutManager);
        for (Component c : components) {
            panel.add(c);
        }
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }

    private void synchronizeTableScrollBars() {
        JScrollBar sBar1 = inputTableScrollPane.getVerticalScrollBar();
        JScrollBar sBar2 = outputTableScrollPane.getVerticalScrollBar();
        sBar2.setModel(sBar1.getModel()); //<--------------synchronize
    }

    class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            int returnValue = chooser.showOpenDialog(CorridorPanel.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                inputFile.setText(file.getName());
                String from = (String) inputFormatComboBox.getSelectedItem();
                String to = (String) outputFormatComboBox.getSelectedItem();
                model.read(file.getName(), from, to);
                inputHeaderTextArea.setText(model.sb.toString());
                outputHeaderTextArea.setText("");
                inputTable.setModel(new DefaultTableModel(model.inputData, model.inputColumnNames));
                outputTable.setModel(new DefaultTableModel(model.outputData, model.outputColumnNames));
            }
        }
    }

    private class TestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(new XMLParser().parse("CMPStack"));
        }
    }
}