import kotlin.math.max
import kotlin.math.min

fun <T> Iterable<T>.chunkedBy(predicate: (T) -> Boolean): List<List<T>> {
    val acc = mutableListOf<List<T>>()
    val tmp = mutableListOf<T>()
    forEach {
        if (predicate(it)) {
            if (tmp.isNotEmpty()) acc += tmp.toList()
            tmp.clear()
        } else {
            tmp += it
        }
    }
    if (tmp.isNotEmpty()) acc.add(tmp)
    return acc
}

class UnionFindTree(numOfNodes: Int) {
    private val nodes: IntArray = IntArray(numOfNodes) { it }
    private val indices = ArrayDeque<Int>()

    /**
     * Returns the root of the tree to which the argument node belongs.
     * 
     * @param nodeNumber The node number
     * @return The root, i.e., the smallest value in the set to which it belongs
     */
    fun getRoot(nodeNumber: Int): Int {
        val rootNode = nodes[nodeNumber]
        if (rootNode != nodeNumber) {
            indices.add(nodeNumber)
            return getRoot(rootNode)
        }

        indices.forEach { nodes[it] = rootNode }
        indices.clear()
        return nodeNumber
    }

    /**
     * Determines whether two nodes belong to the same set.
     * 
     * @param nodeA Node
     * @param nodeB Node
     * @return Whether the two nodes belong to the same set
     */
    fun hasSameRoot(nodeA: Int, nodeB: Int): Boolean {
        return getRoot(nodeA) == getRoot(nodeB)
    }

    /**
     * Merges the sets to which the argument nodes belong.
     * 
     * @param nodeA Node
     * @param nodeB Node
     */
    fun unit(nodeA: Int, nodeB: Int) {
        val rootA = getRoot(nodeA)
        val rootB = getRoot(nodeB)
        nodes[max(rootA, rootB)] = min(rootA, rootB)
    }

    fun allRoots(): List<Int> = nodes.indices.map { getRoot(it) }
}

fun <T> Iterable<T>.isUnique(): Boolean = distinct().size == 1
