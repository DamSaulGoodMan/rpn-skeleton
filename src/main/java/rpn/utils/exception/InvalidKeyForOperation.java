package rpn.utils.exception;

import java.security.InvalidKeyException;
import java.util.Set;

public class InvalidKeyForOperation extends InvalidKeyException {
    public InvalidKeyForOperation(String invalidKey, Set<String> validKeys) {
        super("'" + invalidKey + "' ; Only this key are allowed: " + validKeys);
    }
}
