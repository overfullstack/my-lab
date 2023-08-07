package ga.overfullstack;

import io.vavr.control.Try;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

class ReflectionLab {
  @Test
  @DisplayName("Find Type and use setter")
  void findTypeAndUseSetter() {
    final var bean = BeanUtils.instantiateClass(Bean.class);
    final var fieldToValue =
        Map.of("str", "str", "bool", true, "anInt", 1, "integer", 9, "anEnum", "B");
    fieldToValue.forEach(
        (fieldName, value) -> {
          final var propType = BeanUtils.findPropertyType(fieldName, Bean.class);
          if (propType.isPrimitive()) {
            switch (propType.getName()) {
              case "int", "boolean" -> Try.run(
                  () ->
                      BeanUtils.getPropertyDescriptor(Bean.class, fieldName)
                          .getWriteMethod()
                          .invoke(bean, value));
            }
          } else if (propType.isEnum()) {
            for (Object enumConstant : propType.getEnumConstants()) {
              if (enumConstant.toString().equals(value)) {
                Try.run(
                    () ->
                        BeanUtils.getPropertyDescriptor(Bean.class, fieldName)
                            .getWriteMethod()
                            .invoke(bean, enumConstant));
              }
            }
          } else if (BeanUtils.isSimpleProperty(propType)) {

          }
        });
    System.out.println(bean);
  }

  private static class Bean {
    private String str;
    private boolean bool;

    private int anInt;

    private Integer integer;

    private Enum anEnum;

    public void setStr(String str) {
      this.str = str;
    }

    public void setBool(boolean bool) {
      this.bool = bool;
    }

    public void setAnInt(int anInt) {
      this.anInt = anInt;
    }

    public void setInteger(Integer integer) {
      this.integer = integer;
    }

    public void setAnEnum(Enum anEnum) {
      this.anEnum = anEnum;
    }

    @Override
    public String toString() {
      return "Bean{" + "str='" + str + '\'' + ", bool=" + bool + ", anEnum=" + anEnum + '}';
    }
  }

  private enum Enum {
    A,
    B,
    C
  }
}
