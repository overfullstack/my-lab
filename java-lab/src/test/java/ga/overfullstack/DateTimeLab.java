package ga.overfullstack;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeLab {
  @Test
  @DisplayName("Local Date Time format")
  void localDateTimeFormat() {
    final var ldt = LocalDate.now();
  }
}
