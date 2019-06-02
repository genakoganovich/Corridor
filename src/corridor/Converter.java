package corridor;

import java.util.Vector;

abstract class Converter {
    public static final int NOT_FOR_USE = -999;
    public static final int INITIAL_CORRIDOR_WIDTH = 1000;
    abstract Vector<String> convertData(Vector<String> vector);
}
class CMPStackToZomfConverter extends Converter {
    @Override
    Vector<String> convertData(Vector<String> vector) {
        Vector<String> res = new Vector<>();
        res.add(vector.get(0));
        res.add(vector.get(1));
        res.add(vector.get(2));
        res.add(String.valueOf(0.0));
        res.add(vector.get(3));
        res.add(String.valueOf(INITIAL_CORRIDOR_WIDTH));
        res.add(String.valueOf(1.5));
        return res;
    }
}
class ZomfToCMPStackConverter extends Converter {
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
class ObsOldSergeyToObsNewConverter extends Converter {
    @Override
    Vector<String> convertData(Vector<String> vector) {
        Vector<String> res = new Vector<>();
        res.add(vector.get(0));
        res.add(vector.get(2));
        res.add(vector.get(3));
        res.add(vector.get(4));
        if(vector.get(5) == null) {
            res.add(String.valueOf(NOT_FOR_USE));
        } else if (vector.get(5).equals("")){
            res.add(String.valueOf(NOT_FOR_USE));
        } else {
            res.add(vector.get(5));
        }
        if(vector.get(6) == null) {
            res.add(String.valueOf(NOT_FOR_USE));
        } else if (vector.get(6).equals("")){
            res.add(String.valueOf(NOT_FOR_USE));
        } else {
            res.add(vector.get(6));
        }
        res.add(String.valueOf(NOT_FOR_USE));
        res.add(String.valueOf(NOT_FOR_USE));
        res.add(String.valueOf(NOT_FOR_USE));
        if (vector.get(7) != null) {
            res.add(vector.get(7));
        } else {
            res.add("");
        }
        return res;
    }
}
class ObsOldJasonToObsNewConverter extends Converter {
    @Override
    Vector<String> convertData(Vector<String> vector) {
        Vector<String> res = new Vector<>();
        res.add(vector.get(0));
        res.add(vector.get(1));
        res.add(vector.get(7));
        res.add(vector.get(8));
        res.add(String.valueOf(NOT_FOR_USE));
        res.add(String.valueOf(NOT_FOR_USE));
        res.add(String.valueOf(NOT_FOR_USE));
        res.add(String.valueOf(NOT_FOR_USE));
        res.add(String.valueOf(NOT_FOR_USE));
        res.add("");
        return res;
    }
}
class CMPStackToCMPStackConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return vector;
    }
}
class ZomfToZomfConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return vector;
    }
}
class ObsNewToObsOldConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class ZomfToObsOldConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}

class CMPStackToObsOldConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class CMPStackToObsNewConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class ZomfToObsNewConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class ObsOldSergeyToCMPStackConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class ObsOldSergeyToZomfConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class ObsNewToCMPStackConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class ObsNewToZomfConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class ObsOldSergeyToObsOldSergeyConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}
class ObsNewToObsNewConverter extends Converter {

    @Override
    Vector<String> convertData(Vector<String> vector) {
        return null;
    }
}