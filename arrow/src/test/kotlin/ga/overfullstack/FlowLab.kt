package ga.overfullstack

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

/* gakshintala created on 4/19/20 */
class FlowLab {
  fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

  fun foo(): Flow<Int> = flow { // flow builder
    for (i in 1..3) {
      delay(100) // pretend we are doing something useful here
      log("Emitting $i")
      emit(i) // emit next value
    }
  }

  @Test
  fun flowCollect() =
    runBlocking<Unit> {
      // Launch a concurrent coroutine to check if the ga.overfullstack.main thread is blocked
      launch {
        for (k in 1..3) {
          log("I'm not blocked $k")
          delay(100)
        }
      }
      // Collect the flow
      foo().collect { value -> log("Collected $value") }
    }

  fun foo2(): Flow<Int> =
    flow {
        for (i in 1..3) {
          Thread.sleep(100) // pretend we are computing it in CPU-consuming way
          log("Emitting $i")
          emit(i) // emit next value
        }
      }
      .flowOn(Dispatchers.Default)

  @Test
  fun flowCollect2() = runBlocking<Unit> { foo2().collect { value -> log("Collected $value") } }
}
