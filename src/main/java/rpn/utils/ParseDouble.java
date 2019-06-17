package rpn.utils;

public class ParseDouble {
    public static Double parse(String str) {

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    public static Double parseWithException(String str) throws NumberFormatException {

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            throw nfe;
        }
    }
}
