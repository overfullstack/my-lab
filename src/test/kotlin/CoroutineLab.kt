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
    
}
