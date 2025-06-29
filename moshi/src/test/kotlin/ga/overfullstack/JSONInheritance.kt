package ga.overfullstack

import com.salesforce.revoman.input.readFileInResourcesToString
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import java.lang.reflect.Type
import org.junit.jupiter.api.Test

class JSONInheritance {

  @JsonClass(generateAdapter = true)
  internal open class Parent {
    open var pField1: String? = "pi1"
    open lateinit var pField2: String
  }

  @JsonClass(generateAdapter = true)
  internal class Child(val cField1: String, val cField2: String) : Parent()

  private class IgnoreSuperClass : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<*>? {
      if (type == Parent::class.java) {
        return null
      }
      return moshi.nextAdapter<Any?>(this, type, annotations)
    }
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun inheritance() {
    val json = readFileInResourcesToString("inheritance.json")
    val child = Moshi.Builder().build().adapter<Child>().fromJson(json)
    println(child?.pField1)
  }
}
