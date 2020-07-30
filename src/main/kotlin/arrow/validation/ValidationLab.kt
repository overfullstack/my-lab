package arrow.validation

import arrow.Kind
import arrow.core.*
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.typeclasses.ApplicativeError
import arrow.validation.FFType.NewFormField1

interface FormFieldValidation<F, M : FFType> : ApplicativeError<F, Nel<ValidationError>> {
    private fun NewFormField1.contains(needle: String): Kind<F, NewFormField1> =
            if (value.contains(needle, false)) just(this)
            else raiseError(ValidationError.DoesNotContain(needle).nel())

    private fun NewFormField1.maxLength(maxLength: Int): Kind<F, NewFormField1> =
            if (value.length <= maxLength) just(this)
            else raiseError(ValidationError.MaxLength(maxLength).nel())

    fun NewFormField1.validateNewFormField1(): Kind<F, Email> =
            mapN(
                    contains("@"),
                    maxLength(250)
            ) {
                Email(value)
            }.handleErrorWith { raiseError(ValidationError.NotAnEmail(it).nel()) }

    private fun FormField22.contains(needle: String): Kind<F, FormField22> =
            if (value.contains(needle, false)) just(this)
            else raiseError(ValidationError.DoesNotContain(needle).nel())

    private fun FormField22.maxLength(maxLength: Int): Kind<F, FormField22> =
            if (value.length <= maxLength) just(this)
            else raiseError(ValidationError.MaxLength(maxLength).nel())

    fun FormField22.validateWithEmailRules(): Kind<F, Email> =
            mapN(
                    contains("@"),
                    maxLength(250)
            ) {
                Email(value)
            }.handleErrorWith { raiseError(ValidationError.NotAnEmail(it).nel()) }
    
}

sealed class Rules2<F, M : FFType>(A: ApplicativeError<F, Nel<ValidationError>>) :
        FormFieldValidation<F, M>,
        ApplicativeError<F, Nel<ValidationError>> by A {

    // These Strategies are agnostic of datatype. 
    // This can be extended to validate any Datatype other than `FormField` as well.
    object ErrorAccumulationStrategy :
            Rules2<ValidatedPartialOf<Nel<ValidationError>>, NewFormField1>(Validated.applicativeError(NonEmptyList.semigroup()))

    object FailFastStrategy :
            Rules2<EitherPartialOf<Nel<ValidationError>>, NewFormField1>(Either.applicativeError())

    companion object {
        infix fun <A> failFast(f: FailFastStrategy.() -> A): A = f(FailFastStrategy)
        infix fun <A> accumulateErrors(f: ErrorAccumulationStrategy.() -> A): A = f(ErrorAccumulationStrategy)
    }
}

fun main() {
    val failFastErrors = Rules2 failFast {
        listOf(
                NewFormField1("Invalid Email Domain Label", "nowhere.com"),
                NewFormField1("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this fails fast
                NewFormField1("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateNewFormField1() }
    }
    println("FailFast Errors: $failFastErrors")

    val accumulatedErrors = Rules2 accumulateErrors {
        listOf(
                FormField22("Invalid Email Domain Label", "nowhere.com"),
                FormField22("Too Long Email Label", "nowheretoolong${(0..251).map { "g" }}"), //this accumulates N errors
                FormField22("Valid Email Label", "getlost@nowhere.com")
        ).map { it.validateWithEmailRules() }
    }
    println("Accumulated errors: $accumulatedErrors")
}
