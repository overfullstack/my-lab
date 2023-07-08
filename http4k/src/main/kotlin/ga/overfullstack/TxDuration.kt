package ga.overfullstack

import org.http4k.client.ApacheClient
import org.http4k.core.HttpHandler
import org.http4k.core.HttpTransaction
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.then
import org.http4k.filter.ResponseFilters

fun main() {
  // this is a general use filter for reporting on http transactions
  val standardFilter =
    ResponseFilters.ReportHttpTransaction { tx: HttpTransaction ->
      println("Tx Duration: ${tx.duration.toMillis()}ms")
    }

  val monitoredApp: HttpHandler = standardFilter.then(ApacheClient())

  monitoredApp(Request(GET, "https://pokeapi.co/api/v2/pokemon?limit=10"))
}
