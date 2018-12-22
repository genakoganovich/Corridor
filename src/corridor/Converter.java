package corridor;

import java.util.Vector;

abstract class Converter {
    abstract String convertData(String line);
    abstract Vector<String> convertData(Vector<String> vector);
}
class CMPStackToZomfConverter extends Converter {

    public static final int CORRIDOR_WIDTH = 1000;
    public static final int MINIMUM_VELOCITY = 1500;

    @Override
    String convertData(String line) {
        String[] splitLine = Util.split(line);
        return new StringBuilder()
                .append(splitLine[0]).append('\t')
                .append(splitLine[1]).append('\t')
                .append(splitLine[2]).append('\t')
                .append(0.0).append('\t')
                .append(splitLine[3]).append('\t')
                .append(1000).append('\t')
                .append(1.5)
                .toString();
    }

    @Override
    Vector<String> convertData(Vector<String> vector) {
        int currentCorridorWidth = CORRIDOR_WIDTH;
        Vector<String> res = new Vector<>();
        res.add(vector.get(0));
        res.add(vector.get(1));
        double velocity = Double.valueOf(vector.get(2));
        if (velocity - CORRIDOR_WIDTH < MINIMUM_VELOCITY) {
            velocity = (MINIMUM_VELOCITY + velocity + CORRIDOR_WIDTH) / 2;
            vector.set(2, String.valueOf(velocity));
            currentCorridorWidth = (int) (velocity - MINIMUM_VELOCITY);
        }
        res.add(vector.get(2));
        res.add(String.valueOf(0.0));
        res.add(vector.get(3));
        res.add(String.valueOf(currentCorridorWidth));
        res.add(String.valueOf(1.5));
        return res;
    }
}
class ZomfToCMPStackConverter extends Converter {
    @Override
    String convertData(String line) {
        String[] splitLine = Util.split(line);
        return new StringBuilder()
                .append(splitLine[0]).append('\t')
                .append(splitLine[1]).append('\t')
                .append(splitLine[2]).append('\t')
                .append(splitLine[4]).append('\t')
                .append(0)
                .toString();
    }

    @Override
    Vector<String> convertData(Vector<String> vector) {
        Vector<String> res = new Vector<>();
        res.add(vector.get(0));
        res.add(vector.get(1));
        res.add(vector.get(2));
        res.add(vector.get(4));
        res.add(String.valueOf(0));
        return res;
    }
}
class CMPStackToCMPStackConverter extends Converter {
    @Override
    String convertData(String line) {
        return line;
    }

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return vector;
    }
}
class ZomfToZomfConverter extends Converter {
    @Override
    String convertData(String line) {
        return line;
    }

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return vector;
    }
}