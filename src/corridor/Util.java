package corridor;

import java.util.Arrays;
import java.util.Vector;

class Util {
    static Vector<String> lineToVector(String line) {
        return new Vector<>(Arrays.asList(Util.split(line)));
    }
    private static String[] split(String line) {
        return line.split("\\s+");
    }
}
