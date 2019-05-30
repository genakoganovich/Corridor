package corridor;


import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class Model {
    XMLParser xmlParser;
    private HashMap<Pair<String, String>, Converter> converterMap;
    Format inputFormat;
    Format outputFormat;
    Vector<String> inputColumnNames;
    Vector<String> outputColumnNames;
    Vector<Vector<String>> inputData;
    Vector<Vector<String>> outputData;
    private Converter converter;
    String inputHeader;
    String outputHeader;
    String inputFileName;
    String outputFileName;
    String toFormat;
    String fromFormat;

    Model() {
        xmlParser = new XMLParser();
        converterMap = Util.createConverterMap(xmlParser, "corridor");
    }
    void read() {
        inputColumnNames = new Vector<>(Arrays.asList(inputFormat.tableHeaders));
        inputData = new Vector<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputFileName)))) {
            StringBuilder sb = new StringBuilder();
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
                }
            } // while
            inputHeader = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void convert() {
        converter = converterMap.get(new Pair<>(fromFormat, toFormat));
        outputColumnNames = new Vector<>(Arrays.asList(outputFormat.tableHeaders));
        if (fromFormat.equals(toFormat)) {
            outputHeader = inputHeader;
        } else {
            outputHeader = outputFormat.header;
        }
        outputData = new Vector<>();
        for (Vector<String> vector: inputData) {
            outputData.add(converter.convertData(vector));
        }
    }
    void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFileName)))){
            writer.write(outputHeader);
            writer.newLine();
            for (int i = 0; i < outputData.size() - 1; i++) {
                Vector<String> vector = outputData.get(i);
                Util.writeVector(writer, vector);
                writer.newLine();
            }
            Vector<String> vector = outputData.get(outputData.size() - 1);
            Util.writeVector(writer, vector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
