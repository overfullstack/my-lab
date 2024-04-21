package ga.overfullstack

import com.salesforce.revoman.input.readFileInResourcesToString
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import dev.zacsweers.moshix.adapters.AdaptedBy
import dev.zacsweers.moshix.adapters.JsonString
import okio.BufferedSource
import org.junit.jupiter.api.Test

class JsonString {
  @Test
  fun jsonToString() {
    val collectionJsonStr = readFileInResourcesToString("collection-in-obj.json")
    val collectionAdapter =
      Moshi.Builder()
        .add(JsonString.Factory())
        .add(AdaptedBy.Factory())
        .build()
        .adapter(Collection::class.java)
    println(collectionAdapter.fromJson(collectionJsonStr))
  }

  @JsonClass(generateAdapter = true)
  @AdaptedBy(ItemDataAdapter::class)
  internal data class ItemData(val data: String)

  internal class ItemDataAdapter : JsonAdapter<ItemData>() {
    override fun fromJson(reader: JsonReader): ItemData {
      return ItemData(reader.nextSource().use(BufferedSource::readUtf8))
    }

    override fun toJson(writer: JsonWriter, itemData: ItemData?) {
      writer.valueSink().use { sink -> sink.writeUtf8(checkNotNull(itemData?.data)) }
    }
  }

  @JsonClass(generateAdapter = true)
  internal data class Collection(val name: String, val itemData: List<ItemData>)
}
