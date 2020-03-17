package validation.lib;


public interface ValidationFailure {
    ValidationFailure SUCCESS = new ValidationWithException("SUCCESS");
    ValidationFailure FAILURE = new ValidationWithException("Failure");

    static ValidationFailure withErrorMessage(String exceptionMessage) {
        return new ValidationWithException(exceptionMessage);
    }
}
