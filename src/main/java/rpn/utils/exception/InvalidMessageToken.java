package rpn.utils.exception;

import java.security.InvalidKeyException;
import java.util.Set;

public class InvalidMessageToken extends InvalidKeyException {
    public InvalidMessageToken(String invalidToken, Set<String> validToken) {
        super("'" + invalidToken + "' ; Only this message token are allowed: " + validToken);
    }
}
