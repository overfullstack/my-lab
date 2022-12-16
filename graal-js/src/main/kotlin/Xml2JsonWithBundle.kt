fun main() {
  val context = buildContext()
  context.eval("js", "const xml2Json = require('../src/main/resources/xml2Json.js'); console.info(xml2Json('<a>1</a>')['a']);")
}

