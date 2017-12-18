package corridor;


import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class Model {
    XMLParser xmlParser;
    private HashMap<Pair<String, String>, Converter> converterMap;
    private Format inputFormat;
    private Format outputFormat;
    Vector<String> inputColumnNames;
    Vector<String> outputColumnNames;
    Vector<Vector<String>> inputData;
    Vector<Vector<String>> outputData;
    private Converter converter;
    StringBuilder sb;
    Model() {
        xmlParser = new XMLParser();
        converterMap = Util.createConverterMap(xmlParser);
    }
    void read(String filename, String from, String to) {
        inputFormat = xmlParser.parse(from);
        outputFormat = xmlParser.parse(to);
        sb = new StringBuilder();
        inputColumnNames = new Vector<>(Arrays.asList(inputFormat.tableHeaders));
        outputColumnNames = new Vector<>(Arrays.asList(outputFormat.tableHeaders));
        inputData = new Vector<>();
        outputData = new Vector<>();
        converter = converterMap.get(new Pair<>(from, to));

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            long lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber < inputFormat.headerSize) {
                    sb.append(line).append(System.lineSeparator());
                } else if(lineNumber == inputFormat.headerSize) {
                    sb.append(line);
                } else {
                    inputData.add(Util.lineToVector(line));
                    outputData.add(Util.lineToVector(converter.convertData(line)));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
