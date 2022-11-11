package ga.overfullstack.generics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TypesLab {
  @Test
  void parametricTest() {
    System.out.println(List.class.getGenericSuperclass());
    //assertTrue(List.class.getGenericSuperclass() instanceof ParameterizedType);
  }
  
}
