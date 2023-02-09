package ga.overfullstack

import org.junit.jupiter.api.Test
import java.time.LocalDate

class DateTimeLab {

  @Test
  fun dateTime() {
    val current = LocalDate.now()
    println(current)
  }
}
