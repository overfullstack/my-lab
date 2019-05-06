package RailwayOriented;

import RailwayOriented.egg.Egg;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import static RailwayOriented.ValidationFailure.VALIDATION_FAILURE_1;

public class RailwayEggValidation {
    @Test
    void goodCode() {
        final var validationStream = Egg.getEggCarton().stream()
                .map(Validation::<ValidationFailure, Egg>valid)
                .map(this::isValid1)
                .map(this::isValid2)
                .map(this::isValid31)
                .map(this::isValid32);
    }

    private Validation<ValidationFailure, Egg> isValid32(Validation<ValidationFailure, Egg> eggTobeValidated) {
        return Try.of(eggTobeValidated::get)
                .map(Egg::getYellow)
                .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage()))
                .map(ignore -> eggTobeValidated.get());
    }

    private Validation<ValidationFailure, Egg> isValid31(Validation<ValidationFailure, Egg> eggTobeValidated) {
        return Try.of(eggTobeValidated::get)
                .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage()));
    }

    private Validation<ValidationFailure, Egg> isValid2(Validation<ValidationFailure, Egg> eggTobeValidated) {
        return Try.of(eggTobeValidated::get)
                .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage()));
    }

    private Validation<ValidationFailure, Egg> isValid1(Validation<ValidationFailure, Egg> eggTobeValidated) {
        return eggTobeValidated
                .toValidation(VALIDATION_FAILURE_1);
    }

}
