import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.format.Moshi

fun main() {
  println(callout(mapOf("https://pokeapi.co/api/v2/pokemon?limit=10" to Results::class.java)))
}

private fun callout(urlToClass: Map<String, Class<out Any>>): List<Any?> =
  urlToClass.map { (url, clazz) ->
    val result: Response = JavaHttpClient()(Request(Method.GET, url))
    Moshi.asA(result.bodyString(), clazz.kotlin)
  }.toList()

data class Pokemon(val name: String)
data class Results(val results: List<Pokemon>)
