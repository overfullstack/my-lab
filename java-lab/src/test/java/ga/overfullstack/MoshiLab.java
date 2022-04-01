package ga.overfullstack;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static ga.overfullstack.utils.Utils.readFileFromTestResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MoshiLab {

  @Test
  void customListAdapter() throws IOException {
    final var beanStr = readFileFromTestResource("bean.json");
    final var moshi = new Moshi.Builder()
        .add(new BeanAdapter())
        .build();
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
  
  static class BeanAdapter {
    @FromJson
    List<String> fromJson(List<String> items) {
      return Collections.emptyList();
    }
  }
}
