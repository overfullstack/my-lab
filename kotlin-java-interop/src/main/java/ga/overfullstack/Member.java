package ga.overfullstack;

import java.util.AbstractMap;
import java.util.function.Function;

public class Member<T> {
  AbstractMap.SimpleImmutableEntry<String, T> entry;
  Function<T, String> fn1;
}
