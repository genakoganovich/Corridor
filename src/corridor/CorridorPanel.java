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
    private JButton readButton;
    private JButton convertButton;
    private JButton saveButton;
    private JComboBox<String> inputFormatComboBox;
    private JComboBox<String> outputFormatComboBox;
    private Controller controller;

    CorridorPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        inputFile = new JTextField(COLUMNS);
        browseButton = new JButton("...");
        controller = new Controller();
        inputFormatComboBox = new JComboBox<>(controller.model.xmlParser.getCorridors());
        outputFormatComboBox = new JComboBox<>(controller.model.xmlParser.getCorridors());
        inputFormatComboBox.addActionListener(new InputFormatComboboxListener());
        outputFormatComboBox.addActionListener(new OutputFormatComboboxListener());
        readButton = new JButton("Read");
        convertButton = new JButton("Convert");
        saveButton = new JButton("Save");
        browseButton.addActionListener(new BrowseButtonListener());
        readButton.addActionListener(new ReadButtonListener());
        convertButton.addActionListener(new ConvertButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        JPanel fileNamePanel = createPanel(LayoutType.Flow, new Component[]{inputFile, browseButton,
                inputFormatComboBox, outputFormatComboBox, readButton, convertButton, saveButton});

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
        controller.updateFromFormat();
        controller.updateToFormat();
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
            if (chooser.showOpenDialog(CorridorPanel.this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                inputFile.setText(file.getName());
                controller.updateInputFileName(file.getName());
            }
        }
    }

    private class ReadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.model.read();
            controller.updateInputGUI();
        }
    }
    private class ConvertButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.model.convert();
            controller.updateOutputGUI();
        }
    }
    private class InputFormatComboboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {controller.updateFromFormat();}
    }
    private class OutputFormatComboboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {controller.updateToFormat();}
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.generateOutputFileName();
            controller.model.save();
        }
    }
    class Controller {
        Model model;
        Controller() {model = new Model();}
        void updateFromFormat() {
            model.fromFormat = (String) inputFormatComboBox.getSelectedItem();
            model.inputFormat = model.xmlParser.parse(model.fromFormat);
            model.inputHeader = model.inputFormat.header;
        }
        void updateToFormat() {
            model.toFormat = (String) outputFormatComboBox.getSelectedItem();
            model.outputFormat = model.xmlParser.parse(model.toFormat);
            if (model.fromFormat.equals(model.toFormat)) {
                model.outputHeader = model.inputHeader;
            } else {
                model.outputHeader = model.outputFormat.header;
            }
        }
        void updateInputFileName(String filename) {model.inputFileName = filename;}
        void updateInputGUI() {
            inputHeaderTextArea.setText(model.inputHeader);
            inputTable.setModel(new DefaultTableModel(model.inputData, model.inputColumnNames));
        }
        void updateOutputGUI() {
            outputHeaderTextArea.setText(model.outputHeader);
            outputTable.setModel(new DefaultTableModel(model.outputData, model.outputColumnNames));
        }
        void generateOutputFileName() {
            model.outputFileName = Util.addStringToFilename(model.inputFileName, "_converted");
        }
    }
}