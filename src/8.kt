fun main() {
    val sampleInput = readFile("8_sample.txt")
    val input = readFile("8.txt")

    println("Solve 1: Sample")
    solve1(sampleInput)
    println()

    println("Solve 1: Actual")
    solve1(input)
    println()

    println("Solve 2: Sample")
    solve2(sampleInput)
    println()

    println("Solve 2: Actual")
    solve2(input)
    println()
}

private data class Point3(val x: Long, val y: Long, val z: Long) {
    fun sqDistanceTo(other: Point3): Long {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return dx * dx + dy * dy + dz * dz
    }
}

private data class Edge(val from: Int, val to: Int, val sqDistance: Long)

private fun parsePositions(input: List<String>): List<Point3> =
    input.map { line ->
        val (x, y, z) = line.split(",")
        Point3(x.toLong(), y.toLong(), z.toLong())
    }

private fun edgesSortedByDistance(positions: List<Point3>): List<Edge> =
    sequence {
        for (i in positions.indices) {
            val p1 = positions[i]
            for (j in i + 1..positions.lastIndex) {
                val edge = Edge(i, j, p1.sqDistanceTo(positions[j]))
                yield(edge)
            }
        }
    }.sortedBy { it.sqDistance }
        .toList()

private fun componentSizes(unionFindTree: UnionFindTree, numOfNodes: Int): List<Int> =
    unionFindTree.allRoots()
        .groupingBy { it }
        .eachCount()
        .values
        .toList()

private fun solve1(input: List<String>) {
    val positions = parsePositions(input)
    val numOfNodes = positions.size
    val edges = edgesSortedByDistance(positions).take(numOfNodes)

    val unionFindTree = UnionFindTree(numOfNodes)
    edges.forEach { (from, to, _) -> unionFindTree.unit(from, to) }

    val answer = componentSizes(unionFindTree, numOfNodes)
        .asSequence()
        .sortedDescending()
        .take(3)
        .reduce { a, b -> a * b }
    println(answer)
}

private fun solve2(input: List<String>) {
    val positions = parsePositions(input)
    val numOfNodes = positions.size
    val edges = edgesSortedByDistance(positions)
    val unionFindTree = UnionFindTree(numOfNodes)

    val (index1, index2) = edges.asSequence()
        .onEach { (from, to, _) -> unionFindTree.unit(from, to) }
        .first { _ -> unionFindTree.allRoots().isUnique() }

    val answer = positions[index1].x * positions[index2].x
    println(answer)
}
