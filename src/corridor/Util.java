package corridor;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

class Util {
    static Vector<String> lineToVector(String line) {
        return new Vector<>(Arrays.asList(Util.split(line)));
    }
    static String[] split(String line) {
        return line.split("\\s+");
    }
    static HashMap<Pair<String, String>, Converter> createConverterMap(XMLParser xmlParser) {
        HashMap<Pair<String, String>, Converter> converterMap = new HashMap<>();
        for (String from: xmlParser.getCorridors()) {
            for (String to: xmlParser.getCorridors()) {
                try {
                    Class<? extends Converter> aClass = (Class<? extends Converter>) Class.forName("corridor." + from + "To" + to + "Converter");
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
        return converterMap;
    }
}
