/* gakshintala created on 1/21/20 */
package coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main start")
    withContext(Dispatchers.Default) {
        val longer = async { delayFun(1, 2000) }
        val shorter = async { delayFun(2) }
        longer.await() // while waiting on this, thread is freed and hops to shorter 
        shorter.await()
    }

    println("Main stop")
}

suspend fun delayFun(id: Int, delay: Long = 1000) {
    println("$id - Delay start in Thread: ${Thread.currentThread().name}") // It doesn't block the main thread.
    delay(delay) // Thread freed here.
    //Thread.sleep(1000)
    println("$id - Delay end   in Thread: ${Thread.currentThread().name}") // It can be started and ended by different thread.
}
