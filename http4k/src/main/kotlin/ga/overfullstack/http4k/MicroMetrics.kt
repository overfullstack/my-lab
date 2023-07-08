package ga.overfullstack.http4k

import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import org.http4k.client.ApacheClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.filter.MicrometerMetrics

fun main() {
  // this is a micrometer registry used mostly for testing - substitute the correct implementation.
  val registry = SimpleMeterRegistry()
  val client =
    ClientFilters.SetBaseUriFrom(Uri.of("https://pokeapi.co"))
      .then(ClientFilters.MicrometerMetrics.RequestTimer(registry))
      .then(ApacheClient())

  for (limit in 1..10) {
    client(Request(GET, "/api/v2/pokemon?limit=$limit"))
  }

  // see some results
  registry.forEachMeter { println("${it.id}\n${it.measure().joinToString()}") }
}
