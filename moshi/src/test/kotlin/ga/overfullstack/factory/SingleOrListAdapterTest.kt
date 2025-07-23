package ga.overfullstack.factory

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import java.util.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SingleOrListAdapterTest {
  @Test
  fun singleOrList() {
    val moshi = Moshi.Builder().add(ObjOrListAdapterFactory).build()
    val adapter: JsonAdapter<List<Any?>> =
      moshi.adapter(
        Types.newParameterizedType(List::class.java, Map::class.java),
        SingleOrList::class.java,
      )
    val collectionStr = readFileToString("collection.json")
    val collection = adapter.fromJson(collectionStr)
    print(collection)
    val objStr = readFileToString("obj.json")
    val objList = adapter.fromJson(objStr)
    print(objList)
    val collectionInObjStr = readFileToString("collection-in-obj.json")
    val collectionInObj = adapter.fromJson(collectionInObjStr)
    print(collectionInObj)
    val collectionWithSingleObjStr = readFileToString("collection-with-single-obj.json")
    val collectionWithSingleObj = adapter.fromJson(collectionWithSingleObjStr)
    print(collectionWithSingleObj)
  }

  private fun print(singleOrList: Collection<Any?>?) {
    if (singleOrList?.javaClass == Collections.singletonList(null).javaClass) {
      println((singleOrList).first())
    } else {
      println(singleOrList)
    }
  }

  @Test
  fun objOrList() {
    val moshi = Moshi.Builder().add(ObjOrListAdapterFactory).build()
    val adapter: JsonAdapter<Any?> =
      moshi.adapter(
        Types.newParameterizedType(List::class.java, Map::class.java),
        ObjOrList::class.java,
      )
    val collectionStr = readFileToString("collection.json")
    val collection = adapter.fromJson(collectionStr)
    println(collection)
    val objStr = readFileToString("obj.json")
    val objList = adapter.fromJson(objStr)
    println(objList)
    val collectionInObjStr = readFileToString("collection-in-obj.json")
    val collectionInObj = adapter.fromJson(collectionInObjStr)
    println(collectionInObj)
    val collectionWithSingleObjStr = readFileToString("collection-with-single-obj.json")
    val collectionWithSingleObj = adapter.fromJson(collectionWithSingleObjStr)
    println(collectionWithSingleObj)
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun anyAdapter2() {
    val adapter = Moshi.Builder().build().adapter<Any>()
    val collectionStr = readFileToString("collection.json")
    val collection = adapter.fromJson(collectionStr)
    println(collection?.javaClass)
    println(collection)
    val objStr = readFileToString("obj.json")
    val objList = adapter.fromJson(objStr)
    println(objList?.javaClass)
    println(objList)
    val collectionInObjStr = readFileToString("collection-in-obj.json")
    val collectionInObj = adapter.fromJson(collectionInObjStr)
    println(collectionInObj?.javaClass)
    println(collectionInObj)
    val collectionWithSingleObjStr = readFileToString("collection-with-single-obj.json")
    val collectionWithSingleObj = adapter.fromJson(collectionWithSingleObjStr)
    println(collectionWithSingleObj?.javaClass)
    println(collectionWithSingleObj)
  }

  @DisplayName("Doesn't work")
  @Test
  fun anyAdapter() {
    val moshi = Moshi.Builder().add(AnyAdapterFactory).build()
    val adapter: JsonAdapter<List<Map<String, String>>> =
      moshi.adapter(Types.newParameterizedType(List::class.java, Map::class.java))
    val collectionStr = readFileToString("collection.json")
    val collection = adapter.fromJson(collectionStr)
    println(collection)
    val objStr = readFileToString("obj.json")
    val obj = adapter.fromJson(objStr)
    println(obj)
    val collectionInObjStr = readFileToString("collection-in-obj.json")
    val collectionInObj = adapter.fromJson(collectionInObjStr)
    println(collectionInObj)
  }
}
