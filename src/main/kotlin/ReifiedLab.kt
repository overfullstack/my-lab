/* gakshintala created on 2/28/20 */

sealed class TreeNode {
    val parent: TreeNode? = null

    class Root : TreeNode()
    class Node : TreeNode()
    class Leaf : TreeNode()
}

fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
    var p = parent
    while (p != null && !clazz.isInstance(p)) {
        p = p.parent
    }
    @Suppress("UNCHECKED_CAST")
    return p as T?
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
