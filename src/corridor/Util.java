package corridor;

import javafx.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

class Util {
    @Contract("_ -> !null")
    static Vector<String> lineToVector(String line) {
        return new Vector<>(Arrays.asList(Util.split(line)));
    }
    @NotNull
    static String[] split(String line) {
        return line.split("\\s+");
    }
    static HashMap<Pair<String, String>, Converter> createConverterMap(XMLParser xmlParser) {
        HashMap<Pair<String, String>, Converter> converterMap = new HashMap<>();
        for (String from: xmlParser.getCorridors()) {
            for (String to: xmlParser.getCorridors()) {
                try {
                    Class<? extends Converter> aClass =
                            (Class<? extends Converter>) Class.forName("corridor." + from + "To" + to + "Converter");
                    converterMap.put(new Pair<>(from, to), aClass.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        converterMap.put(new Pair<>("Zomf", "CMPStack"), new ZomfToCMPStackConverter());
        return converterMap;
    }
    static String addStringToFilename(String filename, String addedString) {
        int pos = filename.lastIndexOf('.');
        return new StringBuilder(new String(filename.substring(0, pos)))
                .append(addedString)
                .append(new String(filename.substring(pos)))
                .toString();
    }
    static String vectorToLine(Vector<String> vector) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vector.size() - 1; i++) {
            sb.append(vector.get(i)).append('\t');
        }
        sb.append(vector.get(vector.size() - 1));
        return sb.toString();
    }
    static void writeVector(BufferedWriter writer, Vector<String> vector) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vector.size() - 1; i++) {
            sb.append(vector.get(i)).append('\t');
        }
        sb.append(vector.get(vector.size() - 1));
        writer.write(Util.vectorToLine(vector));
    }
    static double get(Vector<String> vector, int index) {
        return Double.parseDouble(vector.get(index));
    }
    static void set(Vector<String> vector, int index, double value) {
        vector.set(index, String.valueOf(value));
    }
}
