package ga.overfullstack.adapter

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.pojo.NestedBean
import ga.overfullstack.utils.readFileFromTestResource
import io.kotest.matchers.shouldBe
import java.io.IOException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RegexAdapterTest {

  @Test
  fun `regex test`() {
    val variableKey = "\$variableKey"
    postManVariableRegex.find("{{$variableKey}}")?.groups?.get("variableKey")?.value shouldBe
      variableKey
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  @Throws(IOException::class)
  fun `regex Factory`() {
    val beanStr = readFileFromTestResource("factory/bean-with-regex.json")
    val nestedBean =
      Moshi.Builder().add(RegexAdapter).build().adapter<NestedBean>().fromJson(beanStr)
    Assertions.assertNotNull(nestedBean)
    Assertions.assertNotNull(nestedBean?.bean)
    Assertions.assertEquals("{{container}}".uppercase(), nestedBean?.name)
    Assertions.assertEquals("member", nestedBean?.bean?.name)
    Assertions.assertEquals(2, nestedBean?.bean?.items?.size)
    Assertions.assertEquals("{{item1}}".uppercase(), nestedBean?.bean?.items?.get(0))
    Assertions.assertEquals("item2", nestedBean?.bean?.items?.get(1))
  }
}
