package ga.overfullstack;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.function.Function;

public class Member<T> {
  SimpleImmutableEntry<String, T> entry;
  Function<T, String> fn1;
}
