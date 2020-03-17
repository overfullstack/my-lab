package arrow.validation

import arrow.Kind
import arrow.core.*
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.typeclasses.ApplicativeError

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

    // These Strategies are Polymorphic to the datatype. 
    // This can be extended to validate any Datatype other than `FormField` as well.
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
    val failFastErrors = Rules failFast {
        listOf(
                FormField("Invalid Email Domain Label", "nowhere.com"),
                FormField("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this fails fast
                FormField("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateEmail() }
    }
    println("FailFast Errors: $failFastErrors")


    val accumulatedErrors = Rules accumulateErrors {
        listOf(
                FormField("Invalid Email Domain Label", "nowhere.com"),
                FormField("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this accumulates N errors
                FormField("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateEmail() }
    }
    println("Accumulated errors: $accumulatedErrors")

    Rules.FailFastStrategy.run {
        listOf(
                FormField("Invalid Email Domain Label", "nowhere.com"),
                FormField("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this fails fast
                FormField("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateEmail() }
    }
}
