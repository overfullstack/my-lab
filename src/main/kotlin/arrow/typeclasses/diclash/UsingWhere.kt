package arrow.typeclasses.diclash

import arrow.Kind
import arrow.typeclasses.Applicative
import arrow.typeclasses.Show
import arrow.typeclasses.User
import arrow.typeclasses.UserId

fun <F> Applicative<F>.findUserName(S: Show<User>, id: Kind<F, UserId>): Kind<F, String> = S.run {
    id.map { fetchUser(it) }.map { it.show() }
}

fun <F, TC> TC.findUserName(id: Kind<F, UserId>): Kind<F, String> where TC : Applicative<F>, TC : Show<User> =
        id.map { fetchUser(it) }.map { it.show() }

fun fetchUser(it: UserId): User {
    TODO("not implemented")
}
