package ga.overfullstack.polyglot

import buildContext
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.github.underscore.U
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.graalvm.polyglot.Source
import org.junit.jupiter.api.Test
import postman.PostmanAPI

class PolyglotXMLLab {
  @Test
  fun xmlParse() {
    val responseBody = """
      <?xml version="1.0" encoding="UTF-8"?>
      <node>
        <a>1</a>
        <b>2</b>
        <c>
          <c1>3</c1>
          <c2>4</c2>
        </c>
      </node>
    """.trimIndent()
    val callingScript = """
      var jsonData=xml2Json(responseBody);
      console.log(jsonData);
      pm.environment.set('x', jsonData['node']['a']) ; 
      pm.environment.set('y', jsonData.node.b);
      pm.environment.set('z', jsonData['node']['c']['c2']);
    """.trimIndent()
    val context = buildContext(useCommonjsRequire = false)
    val source = Source.newBuilder("js", callingScript, "myScript.js").build()
    val pm = PostmanAPI()
    val jsBindings = context.getBindings("js")
    jsBindings.putMember("postman", pm)
    jsBindings.putMember("responseBody", responseBody)
    jsBindings.putMember("xml2Json", xml2Json2)
    context.eval(source)
    println(pm.environment)
  }

  @Test
  fun xmlSoapParse() {
    val responseBody = """
      <?xml version="1.0" encoding="UTF-8"?>
      <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:partner.soap.sforce.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          <soapenv:Body>
              <loginResponse>
                  <result>
                      <metadataServerUrl>https://trialorgsforu-ec.test1.my.pc-rnd.salesforce.com/services/Soap/m/55.0/00DRN0000009wGZ</metadataServerUrl>
                      <passwordExpired>false</passwordExpired>
                      <sandbox>false</sandbox>
                      <serverUrl>https://trialorgsforu-ec.test1.my.pc-rnd.salesforce.com/services/Soap/u/55.0/00DRN0000009wGZ</serverUrl>
                      <sessionId>00DRN0000009wGZ!ARMAQGPcIyUAsjgF36rdz4CPfXHy4gEGwB4DxQvxqqJEQEHNVXRrPUuwLwznzBISJtqAW49V9ASkvnh0dtP4i1L3w34ljA0.</sessionId>
                      <userId>005RN000000bTH9YAM</userId>
                  </result>
              </loginResponse>
          </soapenv:Body>
      </soapenv:Envelope>
    """.trimIndent()
    val callingScript = """
      var jsonData=xml2Json(responseBody);
      console.log(jsonData);
      var sessionId = jsonData['soapenv:Envelope']['soapenv:Body'].loginResponse.result.sessionId
      pm.environment.set("accessToken", sessionId);
    """.trimIndent()
    val context = buildContext(useCommonjsRequire = false)
    val source = Source.newBuilder("js", callingScript, "myScript.js").build()
    val pm = PostmanAPI()
    val jsBindings = context.getBindings("js")
    jsBindings.putMember("postman", pm)
    jsBindings.putMember("responseBody", responseBody)
    jsBindings.putMember("xml2Json", xml2Json)
    context.eval(source)
    println(pm.environment)
  }

  @OptIn(ExperimentalStdlibApi::class)
  val xml2Json = Xml2Json { xml ->
    Moshi.Builder().build().adapter<Map<*, *>>().fromJson(U.xmlToJson(xml))
  }

  private val xml2Json2 = Xml2Json { xml ->
    val trimmedXml = if (xml.startsWith("<?xml")) xml.substring(xml.indexOf("?>") + 2) else xml
    val randomTag = getRandomString(5)
    XmlMapper().readValue("<$randomTag>$trimmedXml</$randomTag>", Map::class.java)
  }

  private fun getRandomString(length: Int): String {
    val allowedChars = ('a'..'z')
    return (1..length)
      .map { allowedChars.random() }
      .joinToString("")
  }

  @FunctionalInterface
  fun interface Xml2Json {
    @Suppress("unused")
    fun xml2Json(xml: String): Map<*, *>?
  }
}