package ga.overfullstack

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test
import pojo.Bean1
import pojo.Message
import pojo.NestedBean1
import pojo.NestedMessages

internal class MoshiKtLab {

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun readJsonToPojo() {
    val mapAdapter = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build().adapter<Bean1>()
    val bean = mapAdapter.fromJson(readFileFromTestResource("bean.json"))!!
    println(bean)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read Json To nested Pojo`() {
    val nestedBeanStr = readFileFromTestResource("nested-bean.json")
    val mapAdapter = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build().adapter<NestedBean1>()
    val nestedBean = mapAdapter.fromJson(nestedBeanStr)!!
    println(nestedBean)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `read Json To nested Pojo with Custom Adapter`() {
    val nestedMessages = Moshi.Builder()
      .add(NestedMessagesAdapter(3))
      .addLast(KotlinJsonAdapterFactory())
      .build()
      .adapter<NestedMessages>()
      .fromJson(readFileFromTestResource("msg.json"))!!
    println(nestedMessages)
  }

  class NestedMessagesAdapter(private val msgCount: Int) {
    @FromJson
    fun fromJson(message: Message): NestedMessages {
      val msgs = List(msgCount) { message.copy(message = it.toString()) }
      return NestedMessages(msgs)
    }
  }
}
