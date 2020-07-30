package arrow.typeclasses.polymorphic.monomorphic

import common.User
import common.UserId
import arrow.typeclasses.polymorphic.Task
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

sealed class Errors : Throwable() {
    class UserNotInLocalStorage(user: User) : Errors()
    class UserNotInRemoteStorage(user: User) : Errors()
}

class LocalDataSource : DataSourceMonomorphic {
    private val localCache: Map<User, List<Task>> =
            mapOf(User(UserId("user1")) to listOf(Task("LocalTask assigned to user1")))

    override fun allTasksByUser(user: User): Observable<List<Task>> =
            Observable.create { emitter ->
                val cachedUser = localCache[user]
                if (cachedUser != null) {
                    emitter.onNext(cachedUser)
                } else {
                    emitter.onError(Errors.UserNotInLocalStorage(user))
                }
            }
}

class RemoteDataSource : DataSourceMonomorphic {
    private val internetStorage: Map<User, List<Task>> =
            mapOf(User(UserId("user2")) to listOf(Task("Remote Task assigned to user2")))

    override fun allTasksByUser(user: User): Observable<List<Task>> =
            Observable.create { emitter ->
                val networkUser = internetStorage[user]
                if (networkUser != null) {
                    emitter.onNext(networkUser)
                } else {
                    emitter.onError(Errors.UserNotInRemoteStorage(user))
                }
            }
}

class TaskRepository(private val localDS: DataSourceMonomorphic,
                     private val remoteDS: RemoteDataSource) {

    fun allTasksByUser(user: User): Observable<List<Task>> =
            localDS.allTasksByUser(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .onErrorResumeNext { _: Throwable -> remoteDS.allTasksByUser(user) }
}

class Module {
    private val localDataSource: LocalDataSource = LocalDataSource()
    private val remoteDataSource: RemoteDataSource = RemoteDataSource()
    val repository: TaskRepository = TaskRepository(localDataSource, remoteDataSource)
}

object Test {

    @JvmStatic
    fun main(): Unit {
        val user1 = User(UserId("user1"))
        val user2 = User(UserId("user2"))
        val user3 = User(UserId("unknown user"))

        val dependenciesModule = Module()
        dependenciesModule.run {
            repository.allTasksByUser(user1).subscribe({ println(it) }, { println(it) })
            repository.allTasksByUser(user2).subscribe({ println(it) }, { println(it) })
            repository.allTasksByUser(user3).subscribe({ println(it) }, { println(it) })
        }
    }
}
