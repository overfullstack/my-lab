package arrow.typeclasses

import arrow.Kind
import arrow.core.getOrElse
import arrow.core.toOption
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.IOPartialOf
import arrow.fx.extensions.io.async.async
import arrow.fx.fix
import arrow.fx.reactor.ForMonoK
import arrow.fx.reactor.MonoK
import arrow.fx.reactor.extensions.monok.async.async
import arrow.fx.reactor.fix
import arrow.fx.reactor.k
import arrow.fx.rx2.k
import arrow.fx.typeclasses.Async
import io.reactivex.Single
import reactor.core.publisher.Mono

/**
 * Client
 */
fun main() {
    // Client provides these concrete implementations.
    val reactorDao = object : NonBlockingReactorRepo<ForMonoK>, Async<ForMonoK> by MonoK.async() {
        override val libDBClient = ReactorLibDBClient()
    }

    val nonBlockingGet = reactorDao.run { "id".validate().fix().mono }
    println(nonBlockingGet.block())

    val blockingDao = object : BlockingRepo<IOPartialOf<Nothing>>, Async<IOPartialOf<Nothing>> by IO.async<Nothing>() {
        override val libDBClient = BlockingLibDBClient()
    }
    val blockingGet = blockingDao.run { "id".validate().fix().unsafeRunSyncEither() }
    println(blockingGet)
}

/** Generic Lib **/

interface RepoTC<F> : Async<F> {
    fun get(): Kind<F, String>

    fun String.validate(): Kind<F, String> = get()
}

interface NonBlockingReactorRepo<F> : RepoTC<F> {
    val libDBClient: ReactorLibDBClient

    override fun get(): Kind<F, String> = effect { libDBClient.get().k().suspended()!! }
}

interface NonBlockingRxRepo<F> : RepoTC<F> {
    val libDBClient: RxLibDBClient

    override fun get(): Kind<F, String> = effect { libDBClient.get().k().suspended() }
}

interface BlockingRepo<F> : RepoTC<F> {
    val libDBClient: BlockingLibDBClient

    override fun get(): Kind<F, String> = fx.async { libDBClient.get() }
}

interface BlockingOptionalRepo<F> : RepoTC<F> {
    val libDBClient: BlockingLibOptionalDBClient

    override fun get(): Kind<F, String> = fx.async {
        libDBClient.get().getOrElse { "" }
    }
}

/* Concrete 3rd party Lib Impls */

class ReactorLibDBClient {
    fun get(): Mono<String> = Mono.just("from mono")
}

class RxLibDBClient {
    fun get(): Single<String> = Single.just("from Rx Single")
}

class BlockingLibDBClient {
    fun get() = "from blocking"
}

class BlockingLibOptionalDBClient {
    fun get() = "from blocking Option".toOption()
}

// TODO 2/26/20 gakshintala: Option, Single
