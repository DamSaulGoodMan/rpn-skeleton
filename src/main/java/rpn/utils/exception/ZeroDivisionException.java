package rpn.utils.exception;

public class ZeroDivisionException extends ArithmeticException {

    public ZeroDivisionException() {
        super("You can not divide with 'divider == 0'");
    }
}