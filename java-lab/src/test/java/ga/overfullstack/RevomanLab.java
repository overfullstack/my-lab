package ga.overfullstack;

import static org.assertj.core.api.Assertions.assertThat;

import com.salesforce.revoman.input.json.JsonPojoUtils;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RevomanLab {
  @Test
  @DisplayName("json with Epoch Date To Pojo")
  void jsonWithEpochDateToPojo() {
    final var beanWithDate =
        JsonPojoUtils.<BeanWithDate>jsonToPojo(BeanWithDate.class, "{\"date\": 1604216172813}");
    assertThat(beanWithDate).isNotNull();
    assertThat(beanWithDate.date).isNotNull();
  }

  private static class BeanWithDate {
    private final Date date;

    private BeanWithDate(Date date) {
      this.date = date;
    }

    public Date getDate() {
      return date;
    }

    @Override
    public String toString() {
      return "BeanWithDate{" + "date=" + date + '}';
    }
  }
}
