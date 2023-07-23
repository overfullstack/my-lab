package ga.overfullstack.pojo.tree

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import ga.overfullstack.pojo.tree.Tree.Tree.JNode
import ga.overfullstack.pojo.tree.Tree.Tree.Node

@JsonClass(generateAdapter = true)
data class Tree(val tree: Tree) {
  @JsonClass(generateAdapter = true)
  data class Tree(val nodes: List<Node>, val root: String) {
    @JsonClass(generateAdapter = true)
    data class JNode(val id: String, val left: String?, val right: String?, val value: Int)

    @JsonClass(generateAdapter = true)
    data class Node(
        val id: String,
        var left: Node? = null,
        var right: Node? = null,
        var value: Int? = null
    )
  }
}

class NodeAdapter(val treeGraph: MutableMap<String, Node> = mutableMapOf()) {
  @FromJson
  fun fromJson(jNode: JNode): Node {
    val node = treeGraph[jNode.id] ?: Node(id = jNode.id)
    node.value = jNode.value
    node.left = getNode(jNode.left)
    node.right = getNode(jNode.right)
    return node.also { treeGraph[jNode.id] = node }
  }

  private fun getNode(id: String?): Node? = id?.let { treeGraph.computeIfAbsent(it, ::Node) }
}
