package ga.overfullstack.rabbitmq

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import java.nio.charset.StandardCharsets

fun main() {
  val factory = ConnectionFactory()
  val connection = factory.newConnection("amqp://guest:guest@localhost:5672/")
  val channel = connection.createChannel()
  val consumerTag = "SimpleConsumer"

  channel.queueDeclare(QUEUE_NAME, false, false, false, null)

  println("[$consumerTag] Waiting for messages...")
  val deliverCallback = DeliverCallback { tag: String?, delivery: Delivery ->
    val message = String(delivery.body, StandardCharsets.UTF_8)
    println("[📩] Received message: '$message'")
    channel.basicPublish("", QUEUE_NAME, null, message.toByteArray(StandardCharsets.UTF_8))
    println(" [x] Sent '$message'")
  }
  val cancelCallback = CancelCallback { tag: String? -> println("[$tag] was canceled") }

  channel.basicConsume(QUEUE_NAME, true, consumerTag, deliverCallback, cancelCallback)
}
