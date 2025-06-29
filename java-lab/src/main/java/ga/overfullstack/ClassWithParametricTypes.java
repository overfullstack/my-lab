package ga.overfullstack;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;

public abstract class ClassWithParametricTypes<T, U> {
	protected final Type type1 =
			new TypeToken<T>(getClass()) {}.getType(); // or getRawType() to return Class<? super T>
	protected final Type type2 = new TypeToken<U>(getClass()) {}.getType();
}
