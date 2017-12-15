package corridor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

enum LayoutType {
    Flow, Box_X_AXIS, Box_Y_AXIS
}
public class CorridorPanel extends JPanel {
    private final static int ROWS = 10;
    private final static int COLUMNS = 20;
    private JTextArea inputHeader;
    private JTextArea outputHeader;
    private JScrollPane inputTableScrollPane;
    private JScrollPane outputTableScrollPane;
    private JTable inputJTable;
    private JTable outputJTable;

    public CorridorPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel fileNamePanel =  createPanel(LayoutType.Flow,
                new Component[] {new JTextField(COLUMNS), new JButton("...")});

        inputHeader = createTextArea();
        outputHeader = createTextArea();

        JPanel headersPanel = createPanel(LayoutType.Box_X_AXIS,
                new Component[] {new JScrollPane(inputHeader), new JScrollPane(outputHeader)});


        inputJTable = new JTable();
        inputTableScrollPane = new JScrollPane(inputJTable);

        outputJTable = new JTable();
        outputTableScrollPane = new JScrollPane(outputJTable);

        JSplitPane headerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, fileNamePanel, headersPanel);
        JPanel tablePanel = createPanel(LayoutType.Box_X_AXIS,
                new Component[] {inputTableScrollPane, outputTableScrollPane});
        add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, headerSplitPane, tablePanel));

        new Reader().read(10);

        synchronizeTableScrollBars();
    }
    public JTextArea createTextArea() {
        JTextArea textArea = new JTextArea(ROWS, COLUMNS);
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        return textArea;
    }
    public JPanel createPanel(LayoutType layoutType, Component[] components) {
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
        for (Component c: components) {
            panel.add(c);
        }
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel;
    }
    public void synchronizeTableScrollBars() {
        JScrollBar sBar1 = inputTableScrollPane.getVerticalScrollBar();
        JScrollBar sBar2 = outputTableScrollPane.getVerticalScrollBar();
        sBar2.setModel(sBar1.getModel()); //<--------------synchronize
    }
    class Reader {
        public void read(int headerSize) {
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(new File("001_MZ16_cmp_stack_vel001.corr")))){
                String line = null;
                long lineNumber = 0;
                Vector<String> inputColumnNames = null;
                Vector<Vector<String>> inputData = new Vector<>();
                Vector<String> outputColumnNames = null;
                Vector<Vector<String>> outputData = new Vector<>();

                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (lineNumber < headerSize) {
                        inputHeader.append(line);
                        inputHeader.append(System.lineSeparator());
                        outputHeader.append(System.lineSeparator());
                    } else if(lineNumber == headerSize) {
                        inputHeader.append(line);
                        outputHeader.append(convertHeader(line));
                        inputColumnNames = lineToVector(line);
                        inputColumnNames.add(" ");
                        outputColumnNames = lineToVector(convertHeader(line));
                    } else {
                        inputData.add(lineToVector(line));
                        outputData.add(lineToVector(convertTable(line)));
                    }
                }
                inputJTable.setModel(new DefaultTableModel(inputData, inputColumnNames));
                outputJTable.setModel(new DefaultTableModel(outputData, outputColumnNames));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public Vector<String> lineToVector(String line) {
            return new Vector<>(Arrays.asList(split(line)));
        }
        public String[] split(String line) {
            return line.split("\\s+");
        }
        public String convertHeader(String line) {
            String[] values = split(line);
            return new StringBuilder()
                    .append(values[0]).append('\t')
                    .append(values[1]).append('\t')
                    .append('V').append('\t')
                    .append('A').append('\t')
                    .append('T').append('\t')
                    .append("DeltaV").append('\t')
                    .append("DeltaA")
                    .toString();
        }
        public String convertTable(String line) {
            String[] values = split(line);
            return new StringBuilder()
                    .append(values[0]).append('\t')
                    .append(values[1]).append('\t')
                    .append(values[2]).append('\t')
                    .append(0.0).append('\t')
                    .append(values[3]).append('\t')
                    .append(1000).append('\t')
                    .append(1.5)
                    .toString();
        }
    }
}