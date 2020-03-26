package arrow.io

import arrow.fx.IO
import arrow.fx.IOResult
import arrow.fx.extensions.io.applicativeError.handleError
import arrow.fx.handleError

class SpeakerService {
    fun loadAllSpeakers(): List<Speaker> {
        TODO("not implemented")
    }
}

fun handleFailure(error: Throwable): Unit = TODO()
fun handleSuccess(speakerNames: List<String>): Unit = TODO()

class ApiClient(private val service: SpeakerService) {
    fun getSpeakers(): IO<String, List<Speaker>> = IO {
        service.loadAllSpeakers()
    }
}

data class Speaker(val name: String)

fun main() {
    val service = SpeakerService()
    val apiClient = ApiClient(service)

    val program = apiClient.getSpeakers()
            .map { speakers -> speakers.map { it.name } }
            .map { names -> handleSuccess(names) }

    // Run the program asynchronously
    program.unsafeRunAsyncEither { callback: IOResult<String, Unit> ->
        callback.fold(
                {},
                { println(it) },
                { println("success") }
        )
    }

    // Run the program synchronously
    program.unsafeRunSyncEither()

}
