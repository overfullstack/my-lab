import org.http4k.core.PolyHandler
import org.http4k.hotreload.HotReloadServer
import org.http4k.hotreload.HotReloadable
import org.http4k.lens.localDate
import org.http4k.lens.string
import org.http4k.mcp.ToolHandler
import org.http4k.mcp.ToolResponse
import org.http4k.mcp.model.Content
import org.http4k.mcp.model.McpEntity
import org.http4k.mcp.model.Tool
import org.http4k.mcp.protocol.ServerMetaData
import org.http4k.mcp.protocol.ServerProtocolCapability.ToolsChanged
import org.http4k.mcp.protocol.Version
import org.http4k.routing.bind
import org.http4k.routing.mcpHttpStreaming
import org.http4k.server.Helidon


val nameArg = Tool.Arg.string().required("name", "Name of the person whose diary appointments are requested")
// argument input typesafe lenses for the tool
val dateArg = Tool.Arg.localDate().required("date", "date in format yyyy-mm-dd")

// the description of the tool exposed to clients
fun toolDefinitionFor(): Tool = Tool(
  "diary",
  "Responds with a list of appointments for the given date",
  nameArg,
  dateArg,
)

// handles the actual call to that tool
val diaryToolHandler: ToolHandler = { toolRequest ->
  val date = dateArg(toolRequest)
  val name = nameArg(toolRequest)
  val calendarData = mapOf(
    date to listOf(
      "08:00 - Breakfast meeting",
      "11:00 - $name PTM",
      "16:00 - Tour planner"
    )
  )
  val appointmentContent = calendarData[date]?.map { Content.Text("$date: $it") } ?: emptyList()
  ToolResponse.Ok(appointmentContent)
}

class ReloadableMCP : HotReloadable<PolyHandler> {
  override fun create() = mcpHttpStreaming(
    ServerMetaData(McpEntity.of("http4k MCP Server"), Version.of("1.0.0"), ToolsChanged),
    toolDefinitionFor() bind diaryToolHandler,
  )
}

fun main() {
  HotReloadServer.poly<ReloadableMCP>(Helidon(3001)).start()
}


