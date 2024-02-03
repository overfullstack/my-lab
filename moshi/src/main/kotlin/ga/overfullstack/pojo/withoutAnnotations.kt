package ga.overfullstack.pojo

data class Bean1(val name: String, val items: List<String>)

data class NestedBean1(val name: String, val bean: Bean)

data class Message1(val message: String)

data class NestedMessages(val message2s: List<Message2>)
