package ga.overfullstack.lombok

import org.junit.jupiter.api.Test

class LombokTest {
  
  @Test
  fun lombokJava() {
    val user = User.builder().age(1).name("user").build()
    println(user)
    val admin = Admin.builder().id(1).age(2).name("admin").build()
      println(admin)
  }
}
