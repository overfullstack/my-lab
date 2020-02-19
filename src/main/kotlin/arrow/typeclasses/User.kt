package arrow.typeclasses

import arrow.typeclasses.di.Index

data class User(val id: Index = 0, var userId: UserId = UserId("")) {
    constructor(id: UserId) : this() {
        userId = id
    }
}
data class UserId(val id: String)
