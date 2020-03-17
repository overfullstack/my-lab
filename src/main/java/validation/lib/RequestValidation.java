package validation.lib;

/**
 * Represents a request validation function that accepts service input representation and produces a validation result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #validate(InputRepresentationT)}.
 *
 * @param <InputRepresentationT> the type of request to be validated.
 * @author gakshintala
 * @since 220
 */
@FunctionalInterface
public interface RequestValidation<InputRepresentationT> {

    /**
     * Applies this validation to the given object.
     *
     * @param inputRepresentationT Type of Object to be validated
     * @return the Validation failure, if any, otherwise ValidationFailure.SUCCESS.
     */
    ValidationFailure validate(InputRepresentationT inputRepresentationT);
}
