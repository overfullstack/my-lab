package ga.overfullstack.factory

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.pojo.Bean
import ga.overfullstack.pojo.NestedBean
import ga.overfullstack.utils.readFileInResourcesToString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UpperCaseAdapterFactoryTest {

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun upperCaseFactoryFromJson() {
    val beanStr = readFileInResourcesToString("nested-bean.json")
    val beanJsonAdapter =
      Moshi.Builder().add(UppercaseAdapterFactory()).build().adapter<NestedBean>()
    val nestedBean = beanJsonAdapter.fromJson(beanStr)
    Assertions.assertNotNull(nestedBean)
    Assertions.assertNotNull(nestedBean?.bean)
    assertEquals("container".uppercase(), nestedBean?.name)
    assertEquals("member".uppercase(), nestedBean?.bean?.name)
    assertEquals(2, nestedBean?.bean?.items?.size)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun upperCaseFactoryToJson() {
    val beanJsonAdapter =
      Moshi.Builder().add(UppercaseAdapterFactory()).build().adapter<NestedBean>()
    val nestedBeanStr =
      beanJsonAdapter.toJson(NestedBean("container", Bean("member", listOf("item1", "item2"))))
    println(nestedBeanStr)
  }
}
