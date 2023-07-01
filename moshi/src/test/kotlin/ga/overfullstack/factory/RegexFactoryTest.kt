package ga.overfullstack.factory

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.pojo.NestedBean
import ga.overfullstack.utils.readFileFromTestResource
import io.kotest.matchers.shouldBe
import java.io.IOException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RegexFactoryTest {

  @Test
  fun `regex test`() {
    val variable = "variable"
    postManVariableRegex.matchEntire("{{$variable}}")?.groupValues?.get(1) shouldBe variable
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  @Throws(IOException::class)
  fun `regex Factory`() {
    val beanStr = readFileFromTestResource("bean-with-regex.json")
    val moshi = Moshi.Builder().add(RegexAdapterFactory()).build()
    val beanJsonAdapter = moshi.adapter<NestedBean>()
    val nestedBean = beanJsonAdapter.fromJson(beanStr)
    Assertions.assertNotNull(nestedBean)
    Assertions.assertNotNull(nestedBean?.bean)
    Assertions.assertEquals("{{container}}".uppercase(), nestedBean?.name)
    Assertions.assertEquals("member", nestedBean?.bean?.name)
    Assertions.assertEquals(2, nestedBean?.bean?.items?.size)
    Assertions.assertEquals("{{item1}}".uppercase(), nestedBean?.bean?.items?.get(0))
    Assertions.assertEquals("item2", nestedBean?.bean?.items?.get(1))
  }
}
