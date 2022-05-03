package ga.overfullstack;

import static ga.overfullstack.utils.Utils.readFileFromTestResource;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JSONInheritanceJ {
  private static abstract class Parent {
    String pField1;
    String pField2;
    MyInterface myInterface;
  }
  
  private static class Child extends Parent {
    String cField1;
    String cField2;
  }
  private interface MyInterface {
    
  }
  
  @Test
  void inheritance() throws IOException {
    final var json = readFileFromTestResource("inheritance.json");
    final var child = new Moshi.Builder()
        .add(new IgnoreUnknownFactory())
        .build()
        .adapter(Child.class)
        .fromJson(json);
    System.out.println(child.pField1);
    System.out.println(child.myInterface);
  }

  static class IgnoreUnknownFactory implements JsonAdapter.Factory {
    @Override public JsonAdapter<?> create(
        Type type, Set<? extends Annotation> annotations, Moshi moshi) {
      var rawType = Types.getRawType(type);
      if (rawType == MyInterface.class) {
        return new IgnoreUnknown<Type>();
      }
      return  moshi.nextAdapter(this, type, annotations);
    }
  }
  
  static class IgnoreUnknown<T> extends JsonAdapter<T> {

    @Override
    @FromJson
    public T fromJson(JsonReader reader) {
      return null;
    }

    @Override
    @ToJson
    public void toJson(JsonWriter writer, T value) {
    }
  }
}
