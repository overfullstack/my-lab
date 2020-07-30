package arrow

import arrow.core.Either
import arrow.core.EitherOf
import arrow.core.EitherPartialOf
import arrow.core.Nel
import arrow.core.NonEmptyList
import arrow.core.Validated
import arrow.core.ValidatedNel
import arrow.core.ValidatedOf
import arrow.core.ValidatedPartialOf
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicativeError.applicativeError
import arrow.core.nel
import arrow.fx.typeclasses.Async
import arrow.typeclasses.ApplicativeError

interface AsyncValidator<F, S, E> : Async<F> {
    fun AE(): ApplicativeError<S, Nel<E>>
    fun <A> Kind<F, A>.validate(): Kind<F, Kind<S, A>>

    companion object {
        fun <F> failfast(AS: Async<F>): AsyncValidator<F, EitherPartialOf<Nel<Throwable>>, Throwable> =
                object : AsyncValidator<F, EitherPartialOf<Nel<Throwable>>, Throwable>, Async<F> by AS {
                    override fun AE(): ApplicativeError<EitherPartialOf<Nel<Throwable>>, Nel<Throwable>> = Either.applicativeError()
                    override fun <A> Kind<F, A>.validate(): Kind<F, EitherOf<Nel<Throwable>, A>> = attempt().map { either -> either.mapLeft { it.nel() } }
                }

        fun <F> accumulate(AS: Async<F>): AsyncValidator<F, ValidatedPartialOf<Nel<Throwable>>, Throwable> =
                object : AsyncValidator<F, ValidatedPartialOf<Nel<Throwable>>, Throwable>, Async<F> by AS {
                    override fun AE(): ApplicativeError<ValidatedPartialOf<Nel<Throwable>>, Nel<Throwable>> = ValidatedNel.applicativeError(NonEmptyList.semigroup())
                    override fun <A> Kind<F, A>.validate(): Kind<F, ValidatedOf<Nel<Throwable>, A>> = attempt().map(::toValidatedNel)
                }
    }
}

private fun <A> toValidatedNel(a: Either<Throwable, A>): ValidatedNel<Throwable, A> =
        a.fold({ e -> Validated.Invalid(NonEmptyList(e)) }, { Validated.Valid(it) })
