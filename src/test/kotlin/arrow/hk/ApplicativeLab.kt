/* gakshintala created on 3/16/20 */
package arrow.hk

import arrow.core.Either
import arrow.core.extensions.either.applicative.applicative
import arrow.core.fix
import arrow.core.left
import arrow.core.right
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ApplicativeLab {

    @Test
    fun `Left * Right`() {
        val product = Either.applicative<String>().run {
            mapN(
                    "left".left(),
                    getRight()
            ) {}
        }.fix()
        product.fold({ assertEquals("left", it) }, {})
    }

    private fun getRight(): Either<String, String> {
        println("This should not be printed")
        return "right".right()
    }

    @Test
    fun `Left * Right * Left`() {
        val product = Either.applicative<String>().run {
            mapN(
                    "left1".left(),
                    "right".right(),
                    "left2".left()
            ) {}
        }.fix()
        product.fold({ assertEquals("left1", it) }, {})
    }

    @Test
    fun `Left * Right * Left * Right`() {
        val product = Either.applicative<String>().run {
            mapN(
                    "left1".left(),
                    "right1".right(),
                    "left2".left(),
                    "left3".left(),
                    "right2".right(),
                    "right3".right(),
                    "left4".left()
            ) {}
        }.fix()
        product.fold({ assertEquals("left1", it) }, {})
    }

    @Test
    fun `Product Left * Right * Left`() {
        val product3 = Either.applicative<String>().run {
            "left1".left().just().product("right".right().just()).product("left2".left().just())
        }
        println(product3)
    }
}
