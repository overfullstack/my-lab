package ga.overfullstack.pojo.composite

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import ga.overfullstack.pojo.tree.NodeAdapter
import ga.overfullstack.pojo.tree.Tree
import ga.overfullstack.utils.readFileFromTestResource
import org.junit.jupiter.api.Test

class TreeParserTest {

  @OptIn(ExperimentalStdlibApi::class)
  @Test
  fun `tree parse`() {
    val treeJson = readFileFromTestResource("tree/tree.json")
    val nodeAdapter = NodeAdapter()
    val treeAdapter = Moshi.Builder().add(nodeAdapter).build().adapter<Tree>()
    val tree = treeAdapter.fromJson(treeJson)
    println(tree)
    println(nodeAdapter.treeGraph[tree?.tree?.root])
  }
}
