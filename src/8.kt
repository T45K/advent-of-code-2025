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

private fun solve1(input: List<String>) {
    val positions = input.map {
        val (x, y, z) = it.split(",")
        Triple(x.toLong(), y.toLong(), z.toLong())
    }

    val numOfNodes = input.size
    val pairs = positions.asSequence().flatMapIndexed { index1, position1 ->
        val pair1 = index1 to position1
        positions.subList(index1 + 1, positions.size).mapIndexed { index2, position2 ->
            val pair2 = index2 + index1 + 1 to position2
            pair1 to pair2
        }
    }.map { (pair1, pair2) ->
        val (index1, position1) = pair1
        val (index2, position2) = pair2
        Triple(index1, index2, sqDistance(position1, position2))
    }.sortedBy { (_, _, distance) -> distance }
        .take(numOfNodes)
        .map { (index1, index2, _) -> index1 to index2 }.toList()

    val unionFindTree = UnionFindTree(numOfNodes)
    pairs.forEach { (index1, index2) -> unionFindTree.unit(index1, index2) }

    val answer = (0..<numOfNodes).map { unionFindTree.getRoot(it) }
        .groupingBy { it }
        .eachCount()
        .map { it.value }
        .asSequence()
        .sortedDescending()
        .take(3)
        .reduce { a, b -> a * b }
    println(answer)
}

private fun sqDistance(p1: Triple<Long, Long, Long>, p2: Triple<Long, Long, Long>): Long =
    sqDistance(p1.first - p2.first, p1.second - p2.second, p1.third - p2.third)

private fun sqDistance(x: Long, y: Long, z: Long): Long =
    x * x + y * y + z * z

private fun solve2(input: List<String>) {
    val positions = input.map {
        val (x, y, z) = it.split(",")
        Triple(x.toLong(), y.toLong(), z.toLong())
    }

    val pairs = positions.asSequence().flatMapIndexed { index1, position1 ->
        val pair1 = index1 to position1
        positions.subList(index1 + 1, positions.size).mapIndexed { index2, position2 ->
            val pair2 = index2 + index1 + 1 to position2
            pair1 to pair2
        }
    }.map { (pair1, pair2) ->
        val (index1, position1) = pair1
        val (index2, position2) = pair2
        Triple(index1, index2, sqDistance(position1, position2))
    }.sortedBy { (_, _, distance) -> distance }
        .map { (index1, index2, _) -> index1 to index2 }.toList()

    val numOfNodes = input.size
    val unionFindTree = UnionFindTree(numOfNodes)

    val (index1, index2) = pairs.asSequence()
        .onEach { (index1, index2) -> unionFindTree.unit(index1, index2) }
        .first { _ -> (0..<numOfNodes).map { unionFindTree.getRoot(it) }.isUnique() }

    val answer = positions[index1].first * positions[index2].first
    println(answer)
}
