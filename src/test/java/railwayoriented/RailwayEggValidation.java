package railwayoriented;

import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;
import railwayoriented.Egg.Yellow;

import static railwayoriented.Egg.Condition.BAD;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_1;

public class RailwayEggValidation {
    @Test
    void railwayCode() {
        final var validationStream = Egg.getEggCarton().stream()
                .map(Validation::<ValidationFailure, Egg>valid)
                .map(this::validate1)
                .map(this::validate2)
                .map(this::validate3);
        validationStream.forEach(System.out::println);
    }

    private Validation<ValidationFailure, Egg> validate1(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .filter(this::isValid1)
                .get()
                .toValidation(VALIDATION_FAILURE_1);

    }

    private boolean isValid1(Egg eggTobeValidated) {
        return true;
    }

    private Validation<ValidationFailure, Egg> validate2(Validation<ValidationFailure, Egg> validatedEgg) {
        return Try.of(validatedEgg::get)
                .filterTry(this::isValid2)
                .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage()));
    }

    private boolean isValid2(Egg eggTobeValidated) throws Exception {
        return true;
    }

    private Validation<ValidationFailure, Egg> validate3(Validation<ValidationFailure, Egg> validatedEgg) {
        return Try.of(validatedEgg::get)
                .filterTry(this::isValid31)
                .map(Egg::getYellow)
                .filterTry(this::isValid32)
                .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage()))
                .flatMap(ignore -> validatedEgg);
    }

    private boolean isValid32(Yellow yellowTobeValidated) throws Exception {
        if (yellowTobeValidated.getCondition() == BAD) {
            throw new IllegalArgumentException("Yellow is Bad");
        } else {
            return true;
        }
    }

    private boolean isValid31(Egg eggTobeValidated) throws Exception {
        return true;
    }

}
