package ga.overfullstack


fun main() {
  val context = ContextBuilder().buildContext()
  context.eval("js", "const xml2Json = require('../src/main/resources/xml2Json.js'); console.info(xml2Json('<a>1</a>')['a']);")
}

