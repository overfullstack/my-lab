/* gakshintala created on 2/19/20 */
package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Main Start")
    delayFor(1, 2000L)
    println("After-1")
    delayFor(2, 1000L)
    println("After-2")
    println("Main End")
}

suspend fun delayFor(id: Int, delayTime: Long) {
    println("Delay-$id Start")
    delay(delayTime)
    println("Delay-$id End")
}
