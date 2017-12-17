package corridor;


class Converter {
    String convert(String line) {
        return "under construction";
    }
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
class NoneToNoneConverter extends Converter{
    @Override
    String convert(String line) {
        return line;
    }
}
class NoneToCMPStackConverter extends Converter {}
class NoneToZomfConverter extends Converter {}
class CMPStackToNoneConverter extends Converter {}
class CMPStackToCMPStackConverter extends Converter {
    @Override
    String convert(String line) {
        return line;
    }
}
class ZomfToNoneConverter extends Converter {}
class ZomfToZomfConverter extends Converter {
    @Override
    String convert(String line) {
        return line;
    }
}
