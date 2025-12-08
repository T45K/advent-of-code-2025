fun main() {
    val sampleInput = readFile("4_sample.txt")
    val input = readFile("4.txt")

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

private val neighborOffsets = listOf(
    -1 to -1, -1 to 0, -1 to 1,
    0 to -1, 0 to 1,
    1 to -1, 1 to 0, 1 to 1,
)

private fun List<String>.inBounds(x: Int, y: Int): Boolean =
    x in indices && y in this[0].indices

private fun List<String>.adjacentPaperCount(x: Int, y: Int): Int =
    neighborOffsets.count { (dx, dy) ->
        val nx = x + dx
        val ny = y + dy
        inBounds(nx, ny) && this[nx][ny] == '@'
    }

private fun solve1(input: List<String>) {
    val row = input.size
    val col = input[0].length

    val answer = (0..<row).sumOf { x ->
        (0..<col).count { y ->
            input[x][y] == '@' && input.adjacentPaperCount(x, y) < 4
        }
    }

    println(answer)
}

private fun List<String>.countRoles() = sumOf { line -> line.count { it == '@' } }

private fun solve2(input: List<String>) {
    val row = input.size
    val col = input[0].length

    fun nextState(current: List<String>): List<String> =
        (0..<row).map { x ->
            (0..<col).map { y ->
                val isRemovable = current[x][y] == '@' && current.adjacentPaperCount(x, y) < 4
                if (isRemovable) '.' else current[x][y]
            }.joinToString("")
        }

    val finalState = generateSequence(input) { current ->
        val next = nextState(current)
        if (next == current) null else next
    }.last()

    val answer = input.countRoles() - finalState.countRoles()

    println(answer)
}
