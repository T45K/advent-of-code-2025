fun main() {
    val sampleInput = readFile("7_sample.txt")
    val input = readFile("7.txt")

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
    val table = input.map { it.toCharArray() }.toTypedArray()
    val row = table.size
    val col = table[0].size
    (1..<row).forEach { i ->
        (0..<col).forEach { j ->
            if (table[i][j] == '.' && table[i - 1][j] == 'S') table[i][j] = '|'
            if (table[i][j] == '.' && table[i - 1][j] == '|') table[i][j] = '|'
            if (table[i][j] == '^' && table[i - 1][j] == '|') {
                if (j > 0) table[i][j - 1] = '|'
                if (j < row) table[i][j + 1] = '|'
            }
        }
    }

    val answer = (1..<row).sumOf { i ->
        (0..<col).count { j -> table[i][j] == '^' && table[i - 1][j] == '|' }
    }

    println(answer)
}

private fun solve2(input: List<String>) {
    val row = input.size
    val col = input[0].length
    val counts = Array(row) { Array(col) { 0L } }
    (1..<row).forEach { i ->
        (0..<col).forEach { j ->
            if (input[i][j] == '.' && input[i - 1][j] == 'S') counts[i][j]++
            if (input[i][j] == '.' && counts[i - 1][j] > 0) counts[i][j] += counts[i - 1][j]
            if (input[i][j] == '^' && counts[i - 1][j] > 0) {
                if (j > 0) counts[i][j - 1] += counts[i - 1][j]
                if (j < row) counts[i][j + 1] += counts[i - 1][j]
            }
        }
    }

    val answer = counts.last().sum()

    println(answer)
}
