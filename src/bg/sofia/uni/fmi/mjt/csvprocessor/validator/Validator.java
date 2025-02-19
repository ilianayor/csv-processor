package bg.sofia.uni.fmi.mjt.csvprocessor.validator;

public class Validator {
    public static void validateNotNull(Object value, String argumentName) {
        if (value == null) {
            throw new IllegalArgumentException(argumentName + "cannot be null.");
        }
    }

    public static void validateNotBlank(String value, String argumentName) {
        if (value.isBlank()) {
            throw new IllegalArgumentException(argumentName + "cannot be blank.");
        }
    }
}
