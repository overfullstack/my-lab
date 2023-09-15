package ga.overfullstack.immutables.generics;

import java.util.List;
import org.immutables.value.Value;

public abstract class TreeElement<T> {}

@Value.Immutable
abstract class Node<T> extends TreeElement<T> {
  public abstract List<TreeElement<T>> elements();
}

@Value.Immutable
abstract class Leaf<T> extends TreeElement<T> {
  @Value.Parameter
  public abstract T value();
}
