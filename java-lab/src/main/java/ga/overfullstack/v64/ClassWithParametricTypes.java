package ga.overfullstack.v64;

public abstract class ClassWithParametricTypes<T, U> {
  protected final java.lang.reflect.Type type1 =
      new com.google.common.reflect.TypeToken<T>(getClass()) {}.getType(); // or getRawType() to return Class<? super T>
  protected final java.lang.reflect.Type type2 = new com.google.common.reflect.TypeToken<U>(getClass()) {}.getType();
}
