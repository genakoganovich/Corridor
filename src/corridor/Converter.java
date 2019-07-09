package corridor;
import java.util.Vector;

abstract class Converter {
    public static final int NOT_FOR_USE = -999;
    public static final int CORRIDOR_WIDTH_LEFT = 500;
    public static final int CORRIDOR_WIDTH_RIGHT = 500;
    public static final int INITIAL_CORRIDOR_WIDTH = (CORRIDOR_WIDTH_LEFT + CORRIDOR_WIDTH_RIGHT) / 2;
    public static final int MINIMAL_VELOCITY = 1500;
    public static final int REPLACEMENT_VELOCITY = 2200;
    abstract Vector<String> convertData(Vector<String> vector);
}
class CMPStackToZomfConverter extends Converter {
    @Override
    Vector<String> convertData(Vector<String> vector) {
        Vector<String> res = new Vector<>();
        res.add(vector.get(0));
        res.add(vector.get(1));

        double oldVelocity = Double.parseDouble(vector.get(2));
        double newVelocity = ((oldVelocity - CORRIDOR_WIDTH_LEFT) + (oldVelocity + CORRIDOR_WIDTH_RIGHT)) / 2;
        if (newVelocity - CORRIDOR_WIDTH_LEFT < MINIMAL_VELOCITY) {
            newVelocity = MINIMAL_VELOCITY + CORRIDOR_WIDTH_LEFT + 100;
        }
        res.add(String.valueOf(newVelocity));
        res.add(String.valueOf(0.0));
        res.add(vector.get(3));
        res.add(String.valueOf(INITIAL_CORRIDOR_WIDTH));
        res.add(String.valueOf(1.55334));
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
class ObsNewToObsNewConverter extends Converter {
    @Override
    Vector<String> convertData(Vector<String> vector) {
        Vector<String> res = new Vector<>();
        int ffid = Integer.parseInt(vector.get(0));
        int ffidNew = ffid - 1000;
        res.add(String.valueOf(ffidNew));
        res.add(vector.get(1));
        res.add(vector.get(2));
        res.add(vector.get(3));
        res.add(vector.get(4));
        res.add(vector.get(5));
        res.add(vector.get(6));
        res.add(vector.get(7));
        res.add(vector.get(8));
        if (vector.get(9) != null) {
            res.add(vector.get(9));
        } else {
            res.add("");
        }
        return res;
    }
}
