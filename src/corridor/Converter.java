package corridor;


abstract class Converter {
    abstract String convert(String line);
}
class CMPStackToZomfConverter extends Converter {
    @Override
    String convert(String line) {
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
}
class ZomfToCMPStackConverter extends Converter {
    @Override
    String convert(String line) {
        String[] splitLine = Util.split(line);
        return new StringBuilder()
                .append(splitLine[0]).append('\t')
                .append(splitLine[1]).append('\t')
                .append(splitLine[2]).append('\t')
                .append(splitLine[4]).append('\t')
                .append(0)
                .toString();
    }
}
class CMPStackToCMPStackConverter extends Converter {
    @Override
    String convert(String line) {
        return line;
    }
}
class ZomfToZomfConverter extends Converter {
    @Override
    String convert(String line) {
        return line;
    }
}
