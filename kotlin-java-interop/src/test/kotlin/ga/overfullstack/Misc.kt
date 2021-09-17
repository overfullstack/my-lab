package ga.overfullstack

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun main() {
  val message = Duration.parseIsoStringOrNull("2009-05-19T14:39Z")
  println(message)
}
