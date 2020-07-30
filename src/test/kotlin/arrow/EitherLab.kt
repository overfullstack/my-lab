/* gakshintala created on 4/16/20 */
package arrow

import arrow.core.Either
import arrow.core.extensions.either.monad.flatten
import arrow.core.right
import org.junit.jupiter.api.Test

class EitherLab {
    fun plusThree(value: Int) = value + 3

    @Test
    fun `right plus three`() {
        Either.right(2).map(::plusThree).fold({}, { it })

        Either.right(2)
                .map(::plusThree)
                .fold({}, { it })
    }

    @Test
    fun `left plus three`() {
        Either.left(2).map(::plusThree).fold({}, { it }) // Unit
    }

    @Test
    fun `sequence`() {
        sequenceOf(1, 2, 3).map(::plusThree) // 4,5,6
    }

    @Test
    fun `flatten`() {
        val innerEither: Either<String, Int> = 1.right()
        val nestedEither: Either<Nothing, Either<String, Int>> = Either.right(innerEither)
        val flatten = nestedEither.flatten()
    }
}
