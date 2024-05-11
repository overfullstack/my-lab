package ga.overfullstack

import kotlinx.datetime.Clock
import org.junit.jupiter.api.Test

class DateTimeLab {

  @Test
  fun dateTime() {
    println("Clock now: " + Clock.System.now())
    println("Epoch seconds: " + Clock.System.now().epochSeconds)
  }
}
