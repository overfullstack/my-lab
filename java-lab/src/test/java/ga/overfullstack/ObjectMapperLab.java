package ga.overfullstack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ObjectMapperLab {
  
  @Test
  @DisplayName("localDate to json")
  void localDateToJson() throws JsonProcessingException {
    final var dateInterval = new DateInterval(LocalDate.now(), LocalDate.now(), new BigDecimal("12345678901234567890.123456789"));
    final var mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    final var json = mapper.writeValueAsString(dateInterval);
    System.out.println(json);
    System.out.println(new BigDecimal("12345678901234567890.123456789").toPlainString());
  }

  static class DateInterval {
    private LocalDate min;
    private LocalDate max;
    private final BigDecimal bd;

    public DateInterval(LocalDate min, LocalDate max, BigDecimal bd) {
      this.min = min;
      this.max = max;
      this.bd = bd;
    }

    public LocalDate getMin() {
      return min;
    }

    public void setMin(LocalDate min) {
      this.min = min;
    }

    public LocalDate getMax() {
      return max;
    }

    public void setMax(LocalDate max) {
      this.max = max;
    }

    public BigDecimal getBd() {
      return bd;
    }
  }
}

