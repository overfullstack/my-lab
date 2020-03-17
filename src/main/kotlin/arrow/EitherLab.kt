/* gakshintala created on 2/27/20 */
package arrow

import arrow.core.Either
import arrow.core.extensions.fx
import arrow.core.left
import arrow.core.right

fun main() {
    val left: Either<Int, Boolean> = 1.left()
    val right: Either<String, String> = "1".right()

    val eitherFx = Either.fx<String, String> {
        left.mapLeft { it.toString() }.bind()
        right.bind()
    }
    println(eitherFx)
}
