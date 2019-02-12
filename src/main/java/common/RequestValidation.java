/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package common;

/**
 * Represents a request validation function that accepts service input representation and produces a validation result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #validate(ValidatableT)}.
 *
 * @param <ValidatableT> the type of request to be validated.
 * 
 * @author gakshintala
 * @since 220
 */
@FunctionalInterface
public interface RequestValidation<ValidatableT> {
    
    /**
     * Applies this validation to the given object.
     *
     * @param validatable Type of Object to be validated
     * @return the Validation failure, if any, otherwise ValidationFailure.SUCCESS.
     */
    Bean validate(ValidatableT validatable);
}
