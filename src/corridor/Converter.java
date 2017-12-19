package corridor;


import java.util.Vector;

abstract class Converter {
    abstract String convertData(String line);
    abstract Vector<String> convertData(Vector<String> vector);
}
class CMPStackToZomfConverter extends Converter {
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
        Vector<String> res = new Vector<>();
        res.add(vector.get(0));
        res.add(vector.get(1));
        res.add(vector.get(2));
        res.add(String.valueOf(0.0));
        res.add(vector.get(3));
        res.add(String.valueOf(1000));
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