package rpn.utils.exception;

import java.util.MissingResourceException;

public class MissingNumbersForOperation extends MissingResourceException {

    public MissingNumbersForOperation(int sizeMinimumValue, String stack) {
        super("Operation stack must have size >= " + sizeMinimumValue, stack, "Numeral");
    }
}
