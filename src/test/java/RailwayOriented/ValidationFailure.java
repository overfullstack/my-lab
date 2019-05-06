package RailwayOriented;

public enum ValidationFailure {
    VALIDATION_FAILURE_1(""),
    VALIDATION_FAILURE_2(""),
    VALIDATION_FAILURE_3(""),
    VALIDATION_FAILURE_32(""),
    VALIDATION_FAILURE_WITH_EXCEPTION("");

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    private ValidationFailure setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    ValidationFailure(String errorMessage) {
        this.errorMessage = errorMessage;    
    }
    
    static ValidationFailure withErrorMessage(String errorMessage) {
        return VALIDATION_FAILURE_WITH_EXCEPTION.setErrorMessage(errorMessage);
    }
}
