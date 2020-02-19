package arrow.typeclasses.polymorphic

import arrow.Kind
import arrow.typeclasses.User

interface DataSourcePolymorphic<F> {
    fun allTasksByUser(user: User): Kind<F, List<Task>>
}
