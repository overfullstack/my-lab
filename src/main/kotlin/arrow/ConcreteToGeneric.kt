import arrow.Kind
import arrow.fx.IO
import arrow.fx.extensions.io.async.async
import arrow.fx.fix
import arrow.fx.reactor.k
import arrow.fx.typeclasses.Async
import arrow.typeclasses.Monad
import reactor.core.publisher.Mono

class RepoAsync<F>(
        private val dbClient: ReactorDBClient,
        M: Async<F>
) : Async<F> by M {
    fun get(): Kind<F, String> = fx.async {
        !effect { dbClient.get().k().suspended()!! }
    }
}

class RepoSync<F>(
        private val dbClient: SyncDBClient,
        M: Monad<F>
) : Monad<F> by M {
    fun get(): Kind<F, String> = fx.monad {
        dbClient.get()
    }
}

fun main() {
    val repoAsync = RepoAsync(ReactorDBClient(), IO.async())
    println(repoAsync.get().fix().unsafeRunSync())

    val repoSync = RepoSync(SyncDBClient(), IO.async())
    println(repoAsync.get().fix().unsafeRunSync())
}

interface AsyncDBClient<F> {
    fun get(): Kind<F, String>
}

class ReactorDBClient {
    fun get(): Mono<String> = Mono.just("from mono async")
}

class SyncDBClient {
    fun get() = "from sync"
}


