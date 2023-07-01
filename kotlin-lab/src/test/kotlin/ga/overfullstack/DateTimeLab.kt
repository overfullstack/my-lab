package ga.overfullstack

import java.time.LocalDate
import org.junit.jupiter.api.Test

class DateTimeLab {

  @Test
  fun dateTime() {
    val current = LocalDate.now()
    println(current)
  }

  @Test
  fun epoch() {
    val epoch = System.currentTimeMillis()
    println(epoch)
  }
}
