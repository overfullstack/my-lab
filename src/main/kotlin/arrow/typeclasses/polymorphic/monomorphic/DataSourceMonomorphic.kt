package arrow.typeclasses.polymorphic.monomorphic

import arrow.typeclasses.User
import arrow.typeclasses.polymorphic.Task
import io.reactivex.Observable

interface DataSourceMonomorphic {
    fun allTasksByUser(user: User): Observable<List<Task>>
}

