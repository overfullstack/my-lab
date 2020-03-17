package arrow.validation.decoupled

import arrow.Kind
import arrow.core.*
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.typeclasses.ApplicativeError

/**
 * A generic rules class that abstracts over validation strategies
 */
sealed class Rules<F, E>(A: ApplicativeError<F, Nel<E>>) : ApplicativeError<F, Nel<E>> by A {

    /**
     * Accumulates errors thanks to validated and non empty list
     */
    class ErrorAccumulationStrategy<E> :
            Rules<ValidatedPartialOf<Nel<E>>, E>(Validated.applicativeError(NonEmptyList.semigroup()))

    /**
     * Fails fast thanks to Either
     */
    class FailFastStrategy<E> :
            Rules<EitherPartialOf<Nel<E>>, E>(Either.applicativeError())

    /**
     * DSL
     */
    companion object {
        fun <E> failFast(): FailFastStrategy<E> = FailFastStrategy()
        fun <E> accumulateErrors(): ErrorAccumulationStrategy<E> = ErrorAccumulationStrategy()
    }

}

/**
 * User defined model errors
 */
sealed class ValidationError(val msg: String) {
    data class DoesNotContain(val value: String) : ValidationError("Did not contain $value")
    data class MaxLength(val value: Int) : ValidationError("Exceeded length of $value")
    data class NotAnEmail(val reasons: Nel<ValidationError>) : ValidationError("Not a valid email")
}

/**
 * Arbitrary rules can be defined anywhere outside the Rules algebra
 */
fun <F> Rules<F, ValidationError>.contains(value: String, needle: String): Kind<F, String> =
        if (value.contains(needle, false)) just(value)
        else raiseError(ValidationError.DoesNotContain(needle).nel())

/**
 * Arbitrary rules can be defined anywhere outside the Rules algebra
 */
fun <F> Rules<F, ValidationError>.maxLength(value: String, maxLength: Int): Kind<F, String> =
        if (value.length <= maxLength) just(value)
        else raiseError(ValidationError.MaxLength(maxLength).nel())

data class Email(val value: String)

/**
 * Some rules that use the applicative syntax to validate and gather errors
 */
fun <F> Rules<F, ValidationError>.validateEmailWithRules(value: String): Kind<F, Email> =
        mapN(
                contains(value, "@"),
                maxLength(value, 250)
        ) {// Using mapN for rules, so rules can be added without changing fn signature.
            Email(value)
        }.handleErrorWith { raiseError(ValidationError.NotAnEmail(it).nel()) }

/**
 * Proof the same code works polymorphically
 */
fun main() {

    val accResult = Rules.accumulateErrors<ValidationError>().run {
        tupledN( // This is required only in case of validating for multiple emails.
                validateEmailWithRules("nowhere.com"),
                validateEmailWithRules("nowheretoolong${(0..251).map { "g" }}")
        )
    }

    val validEmailAcc = Rules.accumulateErrors<ValidationError>().run {
        validateEmailWithRules("abc@kt.com")
    }

    val failFastResult = Rules.failFast<ValidationError>().run {
        tupledN(
                validateEmailWithRules("nowhere.com"),
                validateEmailWithRules("nowheretoolong${(0..251).map { "g" }}")
        )
    }

    val validEmailFF = Rules.failFast<ValidationError>().run {
        validateEmailWithRules("abc@kt.com").fix()
    }

    println(accResult)
    //Invalid(e=NonEmptyList(all=[NotAnEmail(reasons=NonEmptyList(all=[DoesNotContain(value=@)])), NotAnEmail(reasons=NonEmptyList(all=[DoesNotContain(value=@), MaxLength(value=250)]))]))
    println(failFastResult)
    //Left(a=NonEmptyList(all=[NotAnEmail(reasons=NonEmptyList(all=[DoesNotContain(value=@)]))]))
    println(validEmailAcc)
    println(validEmailFF)
}
