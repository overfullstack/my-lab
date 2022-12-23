package ga.overfullstack.common

data class User(val id: Int = 0, var userId: UserId = UserId(""))

data class UserId(val id: String)
