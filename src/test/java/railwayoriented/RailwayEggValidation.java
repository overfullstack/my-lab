package railwayoriented;

import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;
import railwayoriented.Egg.Yellow;

import static railwayoriented.Egg.Condition.BAD;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_1;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_2;

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
                .filter(this::simpleValidation)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_1));
    }

    private boolean simpleValidation(Egg eggTobeValidated) {
        return true;
    }

    private Validation<ValidationFailure, Egg> validate2(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .flatMap(eggTobeValidated -> throwableValidation(eggTobeValidated)
                        .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_2))
                .flatMap(ignore -> validatedEgg);
    }

    private Try<Boolean> throwableValidation(Egg eggTobeValidated) {
        return Try.of(() -> {
            if (true) throw new RuntimeException("throwableValidation");
            return false;
        });
    }

    private Validation<ValidationFailure, Egg> validate3(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .flatMap(egg -> Try.of(() -> egg)
                        .filterTry(this::throwableValidation31)
                        .map(Egg::getYellow)
                        .filterTry(this::throwableAndNestedValidation32)
                        .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage()))
                        .flatMap(ignore -> validatedEgg));
    }

    private boolean throwableAndNestedValidation32(Yellow yellowTobeValidated) throws Exception {
        if (yellowTobeValidated.getCondition() == BAD) {
            throw new IllegalArgumentException("Yellow is Bad");
        } else {
            return true;
        }
    }

    private boolean throwableValidation31(Egg eggTobeValidated) throws Exception {
        return true;
    }

}
