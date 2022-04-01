package ga.overfullstack;

import static ga.overfullstack.utils.Utils.readFileFromTestResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class MoshiLab {

  @Test
  void customListAdapter() throws IOException {
    final var beanStr = readFileFromTestResource("bean.json");
    final var moshi = new Moshi.Builder().build();
    final var beanJsonAdapter = moshi.adapter(Bean.class);
    final var bean = beanJsonAdapter.fromJson(beanStr);
    assertNotNull(bean);
    assertEquals("value1", bean.field1);
    assertEquals(2, bean.items.size());
  }

  static class Bean {
    String field1;
    List<String> items;
  }
}
