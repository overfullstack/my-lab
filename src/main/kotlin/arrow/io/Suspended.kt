/* gakshintala created on 2/19/20 */
package arrow.io

import arrow.fx.IO
import kotlinx.coroutines.runBlocking

suspend fun frameworkMain() = IO { Thread.currentThread().name }.suspended()

fun main() = runBlocking {
    print(frameworkMain())
}
