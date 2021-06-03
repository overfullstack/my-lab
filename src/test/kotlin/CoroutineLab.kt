import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

/* gakshintala created on 4/19/20 */
class CoroutineLab {
    
    @Test
    fun `main thread is suspended when no launch is used`() = runBlocking {
        println("Main Start")
        delayFor(1, 2000L)
        println("After-1")
        delayFor(2, 1000L)
        println("After-2")
        println("Main End")
        Either
    }

    private suspend fun delayFor(id: Int, delayTime: Long) {
        println("Delay-$id Start")
        delay(delayTime) // This suspends main thread
        println("Delay-$id End")
    }
    
    @Test
    fun `launch starts concurrent coroutine on main thread`() = runBlocking<Unit> {
        launch { 
            delay(1000)
            println("World ${Thread.currentThread().name}") // @coroutine#2
        }
        print("Hello,")
    }

    @Test
    fun `async starts concurrent coroutine on main thread`() = runBlocking<Unit> {
        val async = async {
            delay(1000)
            println("World ${Thread.currentThread().name}") // @coroutine#2
        }
        print("Hello,")
        async.await()
    }

    @Test
    fun asyncLab() = runBlocking {
        val async1 = async {
            delay(100)
            print("Hello ")
        }
        val async2 = async {
            delay(100)
            print("World!")
        }
        async1.await()
        async2.await()
    }

    fun log(msg: Any) = println("[${Thread.currentThread().name}] $msg")
    
    suspend fun foo(i: Int): String {
        delay(100)
        log(i)
        return i.toString()
    }

    @Test
    fun asyncInLoop() = runBlocking {
        /*(1..5).asFlow().map { i -> foo(i) }.flowOn(Dispatchers.Default)
                .collect { i -> log(i) }*/

        val map = (1..5).map { i -> async { foo(i) } }
    }

    @Test
    fun `runBlocking with blocking thread`() {
        val time = measureTimeMillis {
            runBlocking {
                for (i in 1..2) {
                    launch {
                        work(i)
                    }
                }
            }
        }
        println("Done in $time ms") // 2s
    }
    
    @Test
    fun `runBlocking with blocking thread inside withContext`() {
        val time = measureTimeMillis {
            runBlocking {
                for (i in 1..2) {
                    launch {
                        workWithContext(i)
                    }
                }
            }
        }
        println("Done in $time ms") // 1s
    }

    @Test
    fun `runBlocking with Dispatcher and blocking thread`() {
        val time = measureTimeMillis {
            runBlocking(Dispatchers.Default) {
                for (i in 1..2) {
                    launch {
                        work(i)
                    }
                }
            }
        }
        println("Done in $time ms") // 1s
    }

    private fun work(i: Int) {
        Thread.sleep(1000)
        println("Work $i done")
    }
    
    private suspend fun workWithContext(i: Int) = withContext(Dispatchers.Default) {
        work(i)
    }
    
    @Test
    fun `withDispatchers vs launch`() = runBlocking {
        println("Main start")
        withContext(Dispatchers.Default) { // This runs on ThreadPool, same as starting a fork-join pool
            val longer = async { delayFun(1, 2000) }
            val shorter = async { delayFun(2) }
            longer.await() // while waiting on this, thread is freed and hops to shorter 
            shorter.await()
        }

        println("Dispatcher end")

        launch { // This runs on main (Test Worker), but doesn't block main thread
            val longer = async { delayFun(1, 2000) }
            val shorter = async { delayFun(2) }
            longer.await()
            shorter.await()
        }

        println("Main end")
    }

    private suspend fun delayFun(id: Int, delay: Long = 1000) {
        println("$id - Delay start in Thread: ${Thread.currentThread().name}") // It doesn't block the main thread.
        delay(delay) // Thread freed here.
        //Thread.sleep(1000)
        println("$id - Delay end   in Thread: ${Thread.currentThread().name}") // ! It can be started and ended by different thread.
    }
    
}
