/* gakshintala created on 2/28/20 */

sealed class TreeNode {
    val parent: TreeNode? = null

    class Root : TreeNode()
    class Node : TreeNode()
    class Leaf : TreeNode()
}

inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}

fun main() {
    TreeNode.Node().findParentOfType<TreeNode.Root>()
}
