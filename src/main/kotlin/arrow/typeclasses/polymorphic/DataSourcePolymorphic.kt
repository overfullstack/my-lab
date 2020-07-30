package arrow.typeclasses.polymorphic

import arrow.Kind
import common.User

interface DataSourcePolymorphic<F> {
    fun allTasksByUser(user: User): Kind<F, List<Task>>
}
