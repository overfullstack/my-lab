package arrow.typeclasses.polymorphic

import arrow.Kind
import arrow.core.Option
import arrow.core.left
import arrow.core.right
import arrow.fx.rx2.SingleK
import arrow.fx.rx2.extensions.singlek.async.async
import arrow.fx.rx2.fix
import arrow.fx.typeclasses.Async
import arrow.typeclasses.ApplicativeError
import arrow.typeclasses.User
import arrow.typeclasses.UserId

sealed class UserLookupError : RuntimeException() //assuming you are using exceptions
data class UserNotInLocalStorage(val user: User) : UserLookupError()
data class UserNotInRemoteStorage(val user: User) : UserLookupError()
data class UnknownError(val underlying: Throwable) : UserLookupError()


/**
 * Both ways of using TypeClasses depicted below. 
 * Implementation by Delegation through Class and Inheriting through interface. 
 */

class LocalDataSource<F>(A: ApplicativeError<F, Throwable>)
    : DataSourcePolymorphic<F>, ApplicativeError<F, Throwable> by A {
    private val localCache: Map<User, List<Task>> =
            mapOf(User(UserId("user1")) to listOf(Task("LocalTask assigned to user1")))

    override fun allTasksByUser(user: User): Kind<F, List<Task>> =
            Option.fromNullable(localCache[user]).fold(
                    { raiseError(UserNotInLocalStorage(user)) },
                    { just(it) }
            )
}

interface RemoteDataSource<F> : DataSourcePolymorphic<F>, Async<F> {
    val internetStorage: Map<User, List<Task>>

    override fun allTasksByUser(user: User): Kind<F, List<Task>> =
            async { cb ->
                //allows you to take values from callbacks and place them back in the context of `F`
                Option.fromNullable(internetStorage[user]).fold(
                        { cb(UserNotInRemoteStorage(user).left()) },
                        { cb(it.right()) }
                )
            }
}

class TaskRepository<F>(
        private val localDS: DataSourcePolymorphic<F>,
        private val remoteDS: RemoteDataSource<F>,
        AE: ApplicativeError<F, Throwable>) : ApplicativeError<F, Throwable> by AE {

    fun allTasksByUser(user: User): Kind<F, List<Task>> =
            localDS.allTasksByUser(user).handleErrorWith {
                when (it) {
                    is UserNotInLocalStorage -> remoteDS.allTasksByUser(user)
                    else -> raiseError(UnknownError(it))
                }
            }
}

class Module<F>(A: Async<F>) {
    private val localDataSource: LocalDataSource<F> = LocalDataSource(A)
    private val remoteDataSource: RemoteDataSource<F> = object : RemoteDataSource<F>, Async<F> by A {
        override val internetStorage = mapOf(User(UserId("user2")) to listOf(Task("Remote Task assigned to user2")))
    }
    
    val repository: TaskRepository<F> = TaskRepository(localDataSource, remoteDataSource, A)
}

object Test {

    @JvmStatic
    fun main(args: Array<String>): Unit {
        val user1 = User(UserId("user1"))
        val user2 = User(UserId("user2"))
        val user3 = User(UserId("unknown user"))

        println("-------------**SingleK**-------------")
        val singleModule = Module(SingleK.async())
        singleModule.run {
            repository.allTasksByUser(user1).fix().single.subscribe({ println(it) }, { println(it) })
            repository.allTasksByUser(user2).fix().single.subscribe({ println(it) }, { println(it) })
            repository.allTasksByUser(user3).fix().single.subscribe({ println(it) }, { println(it) })
        }

        /*val maybeModule = Module(MaybeK.async())
        maybeModule.run {
            repository.allTasksByUser(user1).fix().maybe.subscribe({ println(it) }, { println(it) })
            repository.allTasksByUser(user2).fix().maybe.subscribe({ println(it) }, { println(it) })
            repository.allTasksByUser(user3).fix().maybe.subscribe({ println(it) }, { println(it) })
        }

        val observableModule = Module(ObservableK.async())
        observableModule.run {
            repository.allTasksByUser(user1).fix().observable.subscribe { println(it) }
            repository.allTasksByUser(user2).fix().observable.subscribe { println(it) }
            repository.allTasksByUser(user3).fix().observable.subscribe({ println(it) }, { println(it) })
        }

        val flowableModule = Module(FlowableK.async())
        flowableModule.run {
            repository.allTasksByUser(user1).fix().flowable.subscribe { println(it) }
            repository.allTasksByUser(user2).fix().flowable.subscribe { println(it) }
            repository.allTasksByUser(user3).fix().flowable.subscribe({ println(it) }, { println(it) })
        }

        *//*val deferredModule = Module(DeferredK.async())
        deferredModule.run {
            runBlocking {
                try {
                    println(repository.allTasksByUser(user1).fix().deferred.await())
                    println(repository.allTasksByUser(user2).fix().deferred.await())
                    println(repository.allTasksByUser(user3).fix().deferred.await())
                } catch (e: UserNotInRemoteStorage) {
                    println(e)
                }
            }
        }*//*

        val ioModule = Module(IO.async())
        ioModule.run {
            println(repository.allTasksByUser(user1).fix().attempt().unsafeRunSync())
            println(repository.allTasksByUser(user2).fix().attempt().unsafeRunSync())
            println(repository.allTasksByUser(user3).fix().attempt().unsafeRunSync())
        }*/
    }
}
