package ga.overfullstack

import ga.overfullstack.common.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread

/* gakshintala created on 7/28/20 */

class Coroutines2Lab {
  @Test
  fun `Uncaught exception due to callback`() {
    try {
      getUserStandard("101") { user ->
        println(Thread.currentThread().name)
        println(user)
      }
    } catch (e: Exception) { // This cannot catch the exception as Main finishes before the thread it spawns.
      println("exception")
    }
    println("done in ga.overfullstack.main")
  }

  private fun getUserStandard(userId: String, callback: (User) -> Unit) = thread {
    println(Thread.currentThread().name)
    Thread.sleep(1000)
    throw RuntimeException("throwing exception from thread")
    // callback(User(userId, "Filip"))
  }

  @Test
  fun `Dealing exception with suspended function`() = runBlocking {
    try {
      getUserStandardSuspended("101") { user ->
        println(Thread.currentThread().name)
        println(user)
      }
    } catch (e: Exception) { // Exception is caught as code is sequential
      println("exception")
    }
    println("done in ga.overfullstack.main")
  }

  private suspend fun getUserStandardSuspended(userId: String, callback: (User) -> Unit) {
    println(Thread.currentThread().name)
    delay(1000)
    throw RuntimeException("throwing exception from thread")
    // callback(User(userId, "Filip"))
  }

  @Test
  fun `async await`() {
    val userId = 992
    GlobalScope.launch {
      println("about to call await " + Thread.currentThread().name)
      println(getUserByIdFromNetwork(userId))
      println("await Called " + Thread.currentThread().name)
    }
    println("Main done")
    Thread.sleep(4000)
  }

  private suspend fun getUserByIdFromNetwork(userId: Int): User =
    withContext(Dispatchers.Default) {
      println("inside async " + Thread.currentThread().name)
      delay(3000)
      User(userId)
    }
}
