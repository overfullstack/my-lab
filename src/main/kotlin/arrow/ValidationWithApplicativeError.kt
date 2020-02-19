package arrow

import arrow.core.*
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.typeclasses.ApplicativeError

sealed class ValidationError(val msg: String) {
    data class DoesNotContain(val value: String) : ValidationError("Did not contain $value")
    data class MaxLength(val value: Int) : ValidationError("Exceeded length of $value")
    data class NotAnEmail(val reasons: Nel<ValidationError>) : ValidationError("Not a valid email")
}

data class FormField(val label: String, val value: String)
data class Email(val value: String)

sealed class Rules<F>(A: ApplicativeError<F, Nel<ValidationError>>) : ApplicativeError<F, Nel<ValidationError>> by A {

    private fun FormField.contains(needle: String): Kind<F, FormField> =
            if (value.contains(needle, false)) just(this)
            else raiseError(ValidationError.DoesNotContain(needle).nel())

    private fun FormField.maxLength(maxLength: Int): Kind<F, FormField> =
            if (value.length <= maxLength) just(this)
            else raiseError(ValidationError.MaxLength(maxLength).nel())

    fun FormField.validateEmail(): Kind<F, Email> =
            mapN(
                    contains("@"),
                    maxLength(250)
            ) {
                Email(value)
            }.handleErrorWith { raiseError(ValidationError.NotAnEmail(it).nel()) }

    object ErrorAccumulationStrategy :
            Rules<ValidatedPartialOf<Nel<ValidationError>>>(Validated.applicativeError(NonEmptyList.semigroup()))

    object FailFastStrategy :
            Rules<EitherPartialOf<Nel<ValidationError>>>(Either.applicativeError())

    companion object {
        infix fun <A> failFast(f: FailFastStrategy.() -> A): A = f(FailFastStrategy)
        infix fun <A> accumulateErrors(f: ErrorAccumulationStrategy.() -> A): A = f(ErrorAccumulationStrategy)
    }
}

fun main() {
    val failFastError = Rules failFast {
        listOf(
                FormField("Invalid Email Domain Label", "nowhere.com"),
                FormField("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this fails fast
                FormField("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateEmail() }
    }
    println("FailFast Errors: $failFastError")

    val accumulatedErrors = Rules accumulateErrors {
        listOf(
                FormField("Invalid Email Domain Label", "nowhere.com"),
                FormField("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this accumulates N errors
                FormField("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateEmail() }
    }
    println("Accumulated errors: $accumulatedErrors")
    
}


