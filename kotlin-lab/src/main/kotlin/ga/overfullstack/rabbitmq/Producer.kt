package ga.overfullstack.rabbitmq

import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets

const val QUEUE_NAME = "queue.my"

fun main() {
  val factory = ConnectionFactory()
  factory.newConnection("amqp://guest:guest@localhost:5672/").use { connection ->
    connection.createChannel().use { channel ->
      channel.queueDeclare(QUEUE_NAME, false, false, false, null)
      val message = "Hello Rabbit!"
      channel.basicPublish(
        "", // default exchange
        QUEUE_NAME, // by default routing key = queue_name
        null,
        message.toByteArray(StandardCharsets.UTF_8),
      )
      println("[📮] Sent '$message'")
    }
  }
}
