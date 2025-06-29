package ga.overfullstack.mcp

import java.time.LocalDate
import org.http4k.client.JavaHttpClient
import org.http4k.connect.model.ToolName
import org.http4k.core.BodyMode
import org.http4k.core.Uri
import org.http4k.lens.localDate
import org.http4k.lens.string
import org.http4k.lens.with
import org.http4k.mcp.ToolRequest
import org.http4k.mcp.client.http.HttpStreamingMcpClient
import org.http4k.mcp.model.McpEntity
import org.http4k.mcp.model.Tool
import org.http4k.mcp.protocol.Version

fun main() {
  val client =
    HttpStreamingMcpClient(
      McpEntity.of("http4k MCP Client"),
      Version.of("1.0.0"),
      Uri.of("http://localhost:3001/mcp"),
      JavaHttpClient(responseBodyMode = BodyMode.Stream),
    )

  println(">>> Server handshake\n" + client.start())

  println(">>> Tool list\n" + client.tools().list())

  println(
    ">>> Tool calling\n" +
      client
        .tools()
        .call(
          ToolName.of("diary_for_David"),
          ToolRequest()
            .with(
              Tool.Arg.string().required("name") of "Gopal",
              Tool.Arg.localDate().required("date") of LocalDate.parse("2025-03-21"),
            ),
        )
  )

  client.close()
}
