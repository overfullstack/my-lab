@file:JvmName("JsonPojoUtils")

package ga.overfullstack.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.lang.reflect.Type

fun readFileFromTestResource(fileRelativePath: String): String =
  File("src/test/resources/$fileRelativePath").readText()

fun readFileFromResource(fileAbsolutePath: String): String = File(fileAbsolutePath).readText()

@JvmOverloads
fun <PojoT : Any> jsonToPojo(
  pojoType: Type,
  jsonFilePath: String,
  customAdapters: List<Any> = emptyList(),
  typesToIgnore: Set<Class<out Any>>? = emptySet()
): PojoT? {
  val jsonAdapter = initMoshiJsonAdapter<PojoT>(customAdapters, typesToIgnore, pojoType)
  return runCatching { jsonAdapter.fromJson(readFileFromTestResource(jsonFilePath)) }.getOrNull()
}

@JvmOverloads
fun <PojoT : Any> pojoToJson(
  pojoType: Type,
  pojo: PojoT,
  customAdapters: List<Any> = emptyList(),
  typesToIgnore: Set<Class<out Any>>? = emptySet()
): String? {
  val jsonAdapter = initMoshiJsonAdapter<PojoT>(customAdapters, typesToIgnore, pojoType)
  return runCatching { jsonAdapter.indent("  ").toJson(pojo) }.getOrNull()
}

inline fun <reified PojoT : Any> pojoToJson(
  pojo: PojoT,
  customAdapters: List<Any> = emptyList(),
  typesToIgnore: Set<Class<out Any>>? = emptySet()
): String? {
  val jsonAdapter = initMoshiJsonAdapter<PojoT>(customAdapters, typesToIgnore)
  return runCatching { jsonAdapter.indent("  ").toJson(pojo) }.getOrNull()
}

private fun <PojoT : Any> initMoshiJsonAdapter(
  customAdapters: List<Any>,
  typesToIgnore: Set<Class<out Any>>?,
  pojoType: Type
): JsonAdapter<PojoT> {
  val moshiBuilder = Moshi.Builder()
  for (adapter in customAdapters) {
    moshiBuilder.add(adapter)
  }
  if (!typesToIgnore.isNullOrEmpty()) {
    moshiBuilder.add(IgnoreUnknownFactory(typesToIgnore))
  }
  return moshiBuilder.build().adapter(pojoType)
}

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified PojoT : Any> initMoshiJsonAdapter(
  customAdapters: List<Any>,
  typesToIgnore: Set<Class<out Any>>?,
): JsonAdapter<PojoT> {
  val moshiBuilder = Moshi.Builder()
  for (adapter in customAdapters) {
    moshiBuilder.add(adapter)
  }
  if (!typesToIgnore.isNullOrEmpty()) {
    moshiBuilder.add(IgnoreUnknownFactory(typesToIgnore))
  }
  moshiBuilder.addLast(KotlinJsonAdapterFactory())
  return moshiBuilder.build().adapter<PojoT>()
}
