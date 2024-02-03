package ga.overfullstack.adapter

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dev.zacsweers.moshix.sealed.annotations.FallbackJsonAdapter
import dev.zacsweers.moshix.sealed.annotations.TypeLabel
import org.junit.jupiter.api.Test

@ExperimentalStdlibApi
class MessageTest {

  @Test
  fun assertFallbackAdapterBehavior() {
    val adapter = Moshi.Builder().build().adapter<MessageWithFallbackAdapter>()
    val success = MessageWithFallbackAdapter.Success("Okay!")
    val error = MessageWithFallbackAdapter.Error(mapOf("order" to 66.0))
    assertThat(adapter.fromJson("{\"type\":\"success\",\"value\":\"Okay!\"}")).isEqualTo(success)
    // Test alternates
    assertThat(adapter.fromJson("{\"type\":\"successful\",\"value\":\"Okay!\"}")).isEqualTo(success)
    assertThat(adapter.fromJson("{\"type\":\"error\",\"error_logs\":{\"order\":66}}"))
      .isEqualTo(error)

    // Fallback
    assertThat(adapter.fromJson("{\"type\":\"unknown\",\"value\":\"Okay!\"}")).isEqualTo(success)
  }

  @FallbackJsonAdapter(MessageWithFallbackAdapter.SuccessAdapter::class)
  @JsonClass(generateAdapter = true, generator = "sealed:type")
  sealed class MessageWithFallbackAdapter {

    @TypeLabel("success", ["successful"])
    @JsonClass(generateAdapter = true)
    data class Success(val value: String) : MessageWithFallbackAdapter()

    @TypeLabel("error")
    @JsonClass(generateAdapter = true)
    data class Error(val error_logs: Map<String, Any>) : MessageWithFallbackAdapter()

    class SuccessAdapter(moshi: Moshi) : JsonAdapter<MessageWithFallbackAdapter>() {
      private val delegate = moshi.adapter<Success>()

      override fun fromJson(reader: JsonReader): MessageWithFallbackAdapter? {
        return delegate.fromJson(reader)
      }

      override fun toJson(writer: JsonWriter, value: MessageWithFallbackAdapter?) {
        throw NotImplementedError()
      }
    }
  }
}
