package ga.overfullstack

import java.io.Serializable
import java.lang.reflect.Modifier
import java.util.*
import org.junit.jupiter.api.Test
import org.springframework.beans.BeanUtils

class ReflectionLab {
  @Test
  fun `derive type`() {
    val bean: Bean = BeanUtils.instantiateClass(Bean::class.java)
    val fieldToValue: Map<String, Serializable> =
      java.util.Map.of("str", "str", "bool", true, "anInt", 1, "integer", 9, "anEnum", "B")
    fieldToValue.forEach { (fieldName: String?, value: Serializable) ->
      val propType: Class<*> =
        BeanUtils.findPropertyType(
          fieldName,
          Bean::class.java,
        )
      when {
        propType.isPrimitive -> {
          when (propType.getName()) {
            "int",
            "boolean" ->
              BeanUtils.getPropertyDescriptor(
                  Bean::class.java,
                  fieldName,
                )
                ?.getWriteMethod()
                ?.invoke(bean, value)
          }
        }
        propType.isEnum -> {
          for (enumConstant in propType.getEnumConstants()) {
            if (enumConstant.toString() == value) {
              BeanUtils.getPropertyDescriptor(
                  Bean::class.java,
                  fieldName,
                )
                ?.getWriteMethod()
                ?.invoke(bean, enumConstant)
            }
          }
        }
        BeanUtils.isSimpleProperty(propType) -> {
          when (propType) {
            String::class.java,
            Integer::class.java ->
              BeanUtils.getPropertyDescriptor(Bean::class.java, fieldName)
                ?.getWriteMethod()
                ?.invoke(bean, value)
          }
        }
      }
    }
    println(bean)
  }

  @Test
  fun `write props`() {
    val bean = Bean("str", true, 2, 4, Enum.C)
    writeProps(bean)
  }

  private inline fun <reified T> writeProps(bean: T) {
    val type = T::class.java
    for (propDescriptor in BeanUtils.getPropertyDescriptors(type)) {
      val propType = propDescriptor.propertyType
      val value = propDescriptor.readMethod(bean)
      when {
        propType.isEnum -> println("Enum: $value")
        BeanUtils.isSimpleProperty(propType) ->
          when (value) {
            is String -> println("String: $value")
            is Int -> println("Int: $value")
            is Boolean -> println("Boolean: $value")
            is Long -> println("Long: $value")
            is Double -> println("Double: $value")
          }
      }
    }
  }

  private class Bean() {
    var str: String? = null
    var bool = false
    var anInt = 0
    var integer: Int? = null
    var anEnum: Enum? = null

    constructor(str: String?, bool: Boolean, anInt: Int, integer: Int?, anEnum: Enum?) : this() {
      this.str = str
      this.bool = bool
      this.anInt = anInt
      this.integer = integer
      this.anEnum = anEnum
    }

    override fun toString() = reflectionToString(this)

    companion object {
      fun reflectionToString(obj: Any): String {
        val s = LinkedList<String>()
        var clazz: Class<in Any>? = obj.javaClass
        while (clazz != null) {
          for (prop in clazz.declaredFields.filterNot { Modifier.isStatic(it.modifiers) }) {
            prop.isAccessible = true
            s += "${prop.name}=" + prop.get(obj)?.toString()?.trim()
          }
          clazz = clazz.superclass
        }
        return "${obj.javaClass.simpleName}=[${s.joinToString(", ")}]"
      }
    }
  }

  private enum class Enum {
    A,
    B,
    C
  }
}
