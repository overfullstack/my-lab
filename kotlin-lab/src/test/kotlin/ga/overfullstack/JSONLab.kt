package ga.overfullstack

import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Test

class JSONLab {
  @Test
  fun `java json array`() {
    val ja: JSONArray = JSONArray()
    ja.put(java.lang.Boolean.TRUE)
    ja.put("lorem ipsum")

    val jo: JSONObject = JSONObject()
    jo.put("name", "jon doe")
    jo.put("age", "22")
    jo.put("city", "chicago")
    ja.put(jo)
  }
}
