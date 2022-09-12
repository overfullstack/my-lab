package ga.overfullstack.immutables.generics;

import java.lang.reflect.Type;
import java.util.List;
import com.google.common.reflect.TypeToken;
import org.immutables.value.Value;

public abstract class TreeElement<T> extends Super<T> {
  @Value.Derived
  public Type getType() {
    return new TypeToken<T>(getClass()) {}.getType();
  }
}

abstract class Super<T> {
  protected final Type type1 = new TypeToken<T>(getClass()) {}.getType(); // or getRawType() to return Class<? super T>  
}

@Value.Immutable
abstract class Node<T> extends TreeElement<T> {
  public abstract List<TreeElement<T>> elements();
}

@Value.Immutable
abstract class Leaf<T> extends TreeElement<T> {
  @Value.Parameter public abstract T value();
}
