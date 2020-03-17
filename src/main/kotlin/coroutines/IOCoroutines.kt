/* gakshintala created on 2/14/20 */
package coroutines

import arrow.fx.IO
import arrow.fx.unsafeRunSync

fun main(): Unit = IO {
    delayFun(1)
}.unsafeRunSync()
