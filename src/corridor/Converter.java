package corridor;


abstract class Converter {
    abstract String convert(String line);
}
class CMPStackToZomfConverter extends Converter {
    @Override
    String convert(String line) {

        return new StringBuilder()
                .append(" ")
                .toString();
    }
}
