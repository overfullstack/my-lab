package common

data class User(val id: Int = 0, var userId: UserId = UserId("")) {
  constructor(id: UserId) : this() {
    userId = id
  }
}

data class UserId(val id: String)
