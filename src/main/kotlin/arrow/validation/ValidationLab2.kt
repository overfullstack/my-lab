package arrow.validation

import arrow.Kind
import arrow.core.*
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.typeclasses.ApplicativeError
import arrow.validation.RandomType.RandomType1

sealed class RandomTypeVE(val msg: String) {
    data class IsNegative(val value: Int) : RandomTypeVE("$value is negative")
    data class IsOdd(val value: Int) : RandomTypeVE("$value is odd")
    data class NotValid(val reasons: Nel<RandomTypeVE>) : RandomTypeVE("Not a valid value")
}

sealed class RandomType {
    data class RandomType1(val someInt: Int) : RandomType()
    data class RandomType2(val someStr: String) : RandomType()
}

interface RandomTypeValidation<F, M : RandomType> : ApplicativeError<F, Nel<RandomTypeVE>> {
    private fun RandomType1.isPositive(): Kind<F, RandomType1> =
            if (someInt > 0) just(this)
            else raiseError(RandomTypeVE.IsNegative(someInt).nel())

    private fun RandomType1.isEven(): Kind<F, RandomType1> =
            if (someInt % 2 == 0) just(this)
            else raiseError(RandomTypeVE.IsOdd(someInt).nel())

    fun RandomType1.validateRandomType1(): Kind<F, Int> =
            mapN(
                    isPositive(),
                    isEven()
            ) {
                someInt
            }.handleErrorWith { raiseError(RandomTypeVE.NotValid(it).nel()) }
}

sealed class RulesLibrary<F, M : RandomType>(A: ApplicativeError<F, Nel<RandomTypeVE>>) :
        RandomTypeValidation<F, M>,
        ApplicativeError<F, Nel<RandomTypeVE>> by A {

    // These Strategies are agnostic of datatype. 
    // This can be extended to validate any Datatype other than `FormField` as well.
    object ErrorAccumulationStrategy :
            RulesLibrary<ValidatedPartialOf<Nel<RandomTypeVE>>, RandomType>(Validated.applicativeError(NonEmptyList.semigroup()))

    object FailFastStrategy :
            RulesLibrary<EitherPartialOf<Nel<RandomTypeVE>>, RandomType>(Either.applicativeError())

    companion object {
        infix fun <A> failFast(f: FailFastStrategy.() -> A): A = f(FailFastStrategy)
        infix fun <A> accumulateErrors(f: ErrorAccumulationStrategy.() -> A): A = f(ErrorAccumulationStrategy)
    }
}

// Consumer
fun main() {
    val failFastErrors = RulesLibrary failFast {
        listOf(
                RandomType1(-1),
                RandomType1(3)
        ).map { it.validateRandomType1() }
    }
    println("FailFast Errors: $failFastErrors")
}
