import arrow.Kind
import arrow.fx.IO
import arrow.fx.extensions.io.async.async
import arrow.fx.fix
import arrow.fx.reactor.MonoK
import arrow.fx.typeclasses.Async

open class Repo<F>(
        private val dbClient: DBClient,
        M: Async<F>
) : Async<F> by M {
    fun get(): Kind<F, String> = fx.async {
        val s = !effect { dbClient.get().suspended() }
        s!!
    }
}

fun main() {
    val repo = Repo(DBClient(), IO.async())
    print(repo.get().fix().unsafeRunSync())
}

class DBClient {
    fun get() = MonoK { "abc" }
}
