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
    data class Node(val id: String, val left: Node?, val right: Node?, val value: Int)
  }
}

class NodeAdapter(val treeGraph: MutableMap<String, Node> = mutableMapOf()) {
  @FromJson
  fun fromJson(jNode: JNode): Node {
    val leftNode = jNode.left?.let { treeGraph[it] }
    val rightNode = jNode.right?.let { treeGraph[it] }
    val node = Node(jNode.id, leftNode, rightNode, jNode.value)
    treeGraph[jNode.id] = node
    return node
  }
}
