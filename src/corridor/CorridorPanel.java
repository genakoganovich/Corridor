package corridor;

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

enum LayoutType {
    Flow, Box_X_AXIS, Box_Y_AXIS
}

class CorridorPanel extends JPanel {
    private final static int ROWS = 10;
    private final static int COLUMNS = 20;
    private JTextArea inputHeader;
    private JTextArea outputHeader;
    private JScrollPane inputTableScrollPane;
    private JScrollPane outputTableScrollPane;
    private JTable inputTable;
    private JTable outputTable;
    private JTextField inputFile;
    private JButton browseButton;
    private JButton testButton;
    private JComboBox<String> inputFormatComboBox;
    private JComboBox<String> outputFormatComboBox;
    private XMLParser xmlParser;
    private HashMap<Pair<String, String>, Converter> converterMap;

    CorridorPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        inputFile = new JTextField(COLUMNS);
        browseButton = new JButton("...");
        xmlParser = new XMLParser();
        converterMap = Util.createConverterMap(xmlParser);
        inputFormatComboBox = new JComboBox<>(xmlParser.getCorridors());
        outputFormatComboBox = new JComboBox<>(xmlParser.getCorridors());
        testButton = new JButton("test");
        browseButton.addActionListener(new BrowseButtonListener());
        testButton.addActionListener(new TestListener());
        JPanel fileNamePanel = createPanel(LayoutType.Flow, new Component[]{inputFile, browseButton,
                inputFormatComboBox, outputFormatComboBox, testButton});

        inputHeader = createTextArea();
        outputHeader = createTextArea();

        JPanel headersPanel = createPanel(LayoutType.Box_X_AXIS,
                new Component[]{new JScrollPane(inputHeader), new JScrollPane(outputHeader)});

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

    class Reader {
        void read(String filename) {
            Format inputFormat = xmlParser.parse((String) inputFormatComboBox.getSelectedItem());
            Format outputFormat = xmlParser.parse((String) outputFormatComboBox.getSelectedItem());
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
                String line;
                long lineNumber = 0;
                inputHeader.setText("");
                outputHeader.setText("");
                Vector<String> inputColumnNames = new Vector<>(Arrays.asList(inputFormat.tableHeaders));
                Vector<String> outputColumnNames = new Vector<>(Arrays.asList(outputFormat.tableHeaders));
                Vector<Vector<String>> inputData = new Vector<>();
                Vector<Vector<String>> outputData = new Vector<>();
                String from = (String) inputFormatComboBox.getSelectedItem();
                String to = (String) outputFormatComboBox.getSelectedItem();
                Converter converter = converterMap.get(new Pair<>(from, to));
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (lineNumber <= inputFormat.headerSize) {
                        inputHeader.append(line);
                        inputHeader.append(System.lineSeparator());
                    } else {
                        inputData.add(Util.lineToVector(line));
                        outputData.add(Util.lineToVector(converter.convertData(line)));
                    }
                }
                inputTable.setModel(new DefaultTableModel(inputData, inputColumnNames));
                outputTable.setModel(new DefaultTableModel(outputData, outputColumnNames));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class BrowseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            int returnValue = chooser.showOpenDialog(CorridorPanel.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                inputFile.setText(file.getName());
                new Reader().read(file.getName());
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